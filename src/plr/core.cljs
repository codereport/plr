(ns plr.core
  (:require
   [reagent.dom :as rdom]
   [reagent.core :as r]
   [clojure.string :as str]
   [kixi.stats.core :as kixi]
   [plr.imgs :as imgs]
   [plr.data :as data]
   [plr.styles :as styles]
   [plr.info :as info])
  (:require-macros
   [plr.helper :as read]))

(def is-mobile? (some #(str/includes? js/navigator.userAgent %) ["Android" "iPhone"]))

(defonce state (r/atom {:num-langs       (if is-mobile? 10 20)
                        :actual-langs    (if is-mobile? 10 20)
                        :delta           3
                        :toggle-info     false
                        :omit-edge-langs true
                        :which-langs     "All"}))

(defonce state-check-boxes (r/atom {:so true :octo true :rm true :languish true :pypl false :ieee false :tiobe false}))

(def media "/public/media")
;; (def media "/media")

(def site-langs        [(read/so) (read/octo) (read/rm) (read/languish) (read/pypl) (read/ieee) (read/tiobe)])
(def prev3-site-langs  [(read/prev3-so)  (read/prev3-octo)  (read/prev3-rm)  (read/prev3-languish)  (read/prev3-pypl)  (read/prev3-ieee)  (read/prev3-tiobe)])
(def prev6-site-langs  [(read/prev6-so)  (read/prev6-octo)  (read/prev6-rm)  (read/prev6-languish)  (read/prev6-pypl)  (read/prev6-ieee)  (read/prev6-tiobe)])
(def prev12-site-langs [(read/prev12-so) (read/prev12-octo) (read/prev12-rm) (read/prev12-languish) (read/prev12-pypl) (read/prev12-ieee) (read/prev12-tiobe)])

(defn avg [coll] (transduce identity kixi/mean coll))
(defn stdev [coll] (transduce identity kixi/standard-deviation coll))
(defn format [num] (/ (int (* num 100)) 100))
(defn in? [e coll] (some #(= e %) coll))

(defn filter-langs [mask rankings]
  (->> rankings
       (map vector mask)
       (filter first)
       (map last)))

(defn generate-row-data [rankings mask extra]
  (->> rankings
       (filter-langs mask)
       (str/join "\n")
       (str/split-lines)
       (map #(str/split % #","))
       (map (fn [[i lang]] [(js/parseInt i) lang]))
       (remove #(and (in? (last %) data/odd) (@state :omit-edge-langs)))
       (remove #(= "" %))
       (remove #(and (not (in? (last %) data/functional)) (= (@state :which-langs) "Functional")))
       (remove #(and (not (in? (last %) data/arrays)) (= (@state :which-langs) "Array")))
       (remove #(and (not (in? (last %) data/system)) (= (@state :which-langs) "System")))
       (group-by last)
       (map (fn [[k v]] (let [vals (map first v)] [(avg vals) (stdev vals) (count vals) k])))
       (sort)
       (map-indexed vector)
       (take (+ (@state :num-langs) (if extra 10 0)))))

(defn simplify-row-data [row-data]
  (->> row-data
       (map (fn [[rank [avg stdev n lang]]] [lang rank]))
       (flatten)
       (apply hash-map)))

(defn format-delta [delta]
  (let [val (abs delta)]
    (cond
      (= (- (@state :actual-langs) 1) delta) (str/join ["⭐"])
      (> 0 delta) (str/join ["🟢 (" (str val) ")"])
      (< 0 delta) (str/join ["🔴 (" (str val) ")"])
      :else "-")))

(defn generate-row [rank [avg stdev n lang] prev-rankings]
  [:tr
   [:td styles/cell (str (+ rank 1))]
   [:td [:img {:src (str/join [media "/logos/" (get imgs/logo-map lang)]) :width "40px" :height "40px"}]]
   [:td styles/cell lang]
   [:td styles/cell (format avg)]
   [:td styles/cell (format stdev)]
   [:td styles/cell n]
   [:td styles/cell (format-delta (- rank (prev-rankings lang)))]])

(defn make-table [rows]
  (let [mask            (map #(@state-check-boxes %) data/sites)
        prev-site-langs (cond (= (@state :delta) 3) prev3-site-langs
                              (= (@state :delta) 6) prev6-site-langs
                              :else                 prev12-site-langs)
        prev-rankings   (simplify-row-data (generate-row-data prev-site-langs mask true))]
    [:table (styles/table is-mobile?)
     [:tr {:style {:font-weight "bold"}} [:td] [:td] [:td "Language"] [:td "Avg"] [:td "StDev"] [:td "n¹"] [:td (str/join [(str (@state :delta)) "mΔ"])]]
     (map (partial apply generate-row) (map #(conj % prev-rankings) rows))]))

; Pretty sure the fix for https://github.com/codereport/plr/issues/11 is making sure no "empty rows" get displayed
; Can replicate bug consistently by generating lang list not divisible by 10 and then going to something else
(defn generate-table [rankings mask]
  (let [row-data (generate-row-data rankings mask false)]
    (swap! state assoc :actual-langs (count row-data))
    (cond
      (empty? row-data) [:div [:label "No languages."]]
      (= (@state :which-langs) "Array")  (make-table row-data)
      (not= (@state :num-langs) 20)      (make-table row-data)
      (<= (@state :actual-langs) 10)     (make-table row-data)
      is-mobile?                         (make-table row-data)
      :else [:div (make-table (take 10 row-data)) (make-table (drop 10 row-data))])))

(defn language-check-box [lang disable]
  [:div {:style {:display "inline"}}
   [:input {:type "checkbox"
            :checked (@state-check-boxes lang)
            :disabled (if disable true false)
            :on-change #(swap! state-check-boxes assoc lang (not (@state-check-boxes lang)))}]
   [:div {:style {:display "inline"}} [:label styles/cb-font (str/join [" " (get data/names lang)])]
    [:a {:href (get data/links lang)} [:img {:src (str/join [media "/icons/link.png"]) :width "16px" :height "16px"}]]
    [:label styles/cb-font " "]]])

(defn title-prefix [which-langs]
  (if (= which-langs "All") "" which-langs))

(defn app-view []
  [:div {:style {:text-align "center"
                 :padding "50px"
                 :font-family "Ubuntu Mono,Consolas,IBM Plex Mono,Roboto Mono,Courier"}}
   [:label (styles/font 50) (str/join [(title-prefix (@state :which-langs)) " Programming Language Rankings (2025 Mar)"])] [:br] [:br]
   [:label (styles/font 25) "by code_report"] [:br]
   [:a {:href "https://www.twitter.com/code_report"}  [:img {:src (str/join [media "/icons/twitter.png"]) :width "40px" :height "40px"}]]
   [:a {:href "https://www.youtube.com/c/codereport"} [:img {:src (str/join [media "/icons/youtube.png"]) :width "40px" :height "40px"}]]
   [:a {:href "https://www.github.com/codereport"}    [:img {:src (str/join [media "/icons/github.png"])  :width "40px" :height "40px"}]]
   [:br] [:br]

   (if (@state :toggle-info)
     [:div
      [:label {:style {:text-decoration "underline"}
               :on-click #(swap! state assoc :toggle-info (not (@state :toggle-info)))}  "(back)"] [:br] [:br]
      (info/table is-mobile?)]
     [:div [:div
            ;; TODO: figure out how to make this work
            ;; (map-indexed (fn [i lang] (language-check-box lang (if (= i 3) {:style {:display "inline"}} {}))))
            (language-check-box :so       false)
            (language-check-box :octo     false)
            (language-check-box :rm       false)
            (language-check-box :languish false) [:br]
            (language-check-box :pypl     true)
            (language-check-box :ieee     false)
            (language-check-box :tiobe    true)
            [:label {:style {:text-decoration "underline"}
                     :on-click #(swap! state assoc :toggle-info (not (@state :toggle-info)))}  "(rankings overview)"]
            [:br] [:label "-"] [:br]
            [:div
             [:input {:type "checkbox"
                      :checked (@state :omit-edge-langs)
                      :on-change #(swap! state assoc :omit-edge-langs (not (@state :omit-edge-langs)))}]
             [:label styles/cb-font " Exclude \"Edge Languages\" | "]
             [:form {:style {:display "inline"}}
              [:label styles/cb-font "Number of Languages: "]
              [:select {:value (@state :num-langs)
                        :on-change #(swap! state assoc :num-langs (-> % .-target .-value js/Number))}
               [:option 10] [:option 20]]] [:br]
             [:form {:style {:display "inline"}}
              [:label styles/cb-font "Months for Delta (Δ): "]
              [:select {:value (@state :delta)
                        :on-change #(swap! state assoc :delta (-> % .-target .-value js/Number))}
               [:option 3] [:option 6] [:option 12]]]
             [:label styles/cb-font " | "]
             [:form {:style {:display "inline"}}
              [:select {:value (@state :which-langs)
                        :on-change #(swap! state assoc :which-langs  (-> % .-target .-value))}
               [:option "All"] [:option "Functional"] [:option "Array"] [:option "System"]]]
             [:label styles/cb-font " Languages"]]] [:br]

      (generate-table site-langs (map #(@state-check-boxes %) data/sites))
      [:div (styles/footnote is-mobile?)
       [:br]
       [:label "1 - The number of (selected) ranking websites this language shows up in."] [:br] [:br]
       [:label "If you have suggestions or find a bug, you can open an "]
       [:a {:href "https://github.com/codereport/plr/issues/new"} [:label "issue"]]
       [:label " here."]]])])

(defn render! []
  (rdom/render
   [app-view]
   (js/document.getElementById "app")))
