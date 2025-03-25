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

(def media "/media")

(def sites [:so :octo :rm :languish :ieee])

(def avg (partial transduce identity kixi/mean))
(def stdev (partial transduce identity kixi/standard-deviation))
(defn format [num] (/ (int (* num 100)) 100))
(defn in? [e coll] (some #{e} coll))

(defn filter-langs [mask rankings]
  (->> rankings
       (map vector mask)
       (filter first)
       (map last)))

(defn should-include-lang? [lang]
  (not (or (= "" lang)
           (and (in? lang data/odd) (@state :omit-edge-langs))
           (and (= (@state :which-langs) "Functional") (not (in? lang data/functional)))
           (and (= (@state :which-langs) "Array") (not (in? lang data/arrays)))
           (and (= (@state :which-langs) "System") (not (in? lang data/system))))))

(defn generate-row-data [rankings mask extra]
  (->> rankings
       (filter-langs mask)
       (str/join "\n")
       (str/split-lines)
       (map #(let [[i lang] (str/split % #",")] [(js/parseInt i) lang]))
       (remove #(not (should-include-lang? (last %))))
       (group-by last)
       (map (fn [[k v]] [(avg (map first v)) (stdev (map first v)) (count v) k]))
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
      (= (- (@state :actual-langs) 1) delta) "⭐"
      (zero? delta) "-"
      (neg? delta) (str "🟢 (" val ")")
      :else (str "🔴 (" val ")"))))

(defn get-prev-site-langs [delta]
  (case delta
    3 (read/get-all-sites 3)
    6 (read/get-all-sites 6)
    12 (read/get-all-sites 12)))

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
        prev-site-langs (get-prev-site-langs (@state :delta))
        prev-rankings   (simplify-row-data (generate-row-data prev-site-langs mask true))]
    [:table (styles/table is-mobile?)
     [:tr {:style {:font-weight "bold"}} [:td] [:td] [:td "Language"] [:td "Avg"] [:td "StDev"] [:td "n¹"] [:td (str (@state :delta) "mΔ")]]
     (map (partial apply generate-row) (map #(conj % prev-rankings) rows))]))

(defn should-use-single-column? []
  (or (= (@state :which-langs) "Array")
      (not= (@state :num-langs) 20)
      (<= (@state :actual-langs) 10)
      is-mobile?))

(defn generate-table [rankings mask]
  (let [row-data (generate-row-data rankings mask false)]
    (swap! state assoc :actual-langs (count row-data))
    (cond
      (empty? row-data) [:div [:label "No languages."]]
      (every? false? mask) [:div [:label "Please select at least one language ranking source."]] 
      :else (let [display-rows (if (should-use-single-column?)
                               [row-data]
                               [(take 10 row-data) (drop 10 row-data)])]
              [:div (map make-table display-rows)]))))

(defn language-check-box [lang disable]
  [:div {:style {:display "inline"}}
   [:input {:type "checkbox"
            :checked (@state-check-boxes lang)
            :disabled disable
            :on-change #(swap! state-check-boxes update lang not)}]
   [:div {:style {:display "inline"}} 
    [:label styles/cb-font (str " " (get data/names lang))]
    [:a {:href (get data/links lang)} 
     [:img {:src (str media "/icons/link.png") :width "16px" :height "16px"}]]
    [:label styles/cb-font " "]]])

(defn title-prefix [which-langs]
  (when (not= which-langs "All") which-langs))

(defn social-link [url icon]
  [:a {:href url} 
   [:img {:src (str media "/icons/" icon) :width "40px" :height "40px"}]])

(defn social-links []
  [:div
   [social-link "https://www.twitter.com/code_report"  "twitter.png"]
   [social-link "https://mastodon.social/@code_report" "twitter.png"]
   [social-link "https://www.youtube.com/c/codereport" "youtube.png"]
   [social-link "https://www.github.com/codereport"    "github.png"]])

(defn language-filters []
  (let [langs (keys @state-check-boxes)
        disabled-langs #{:pypl :tiobe}]
    [:div (map-indexed (fn [idx lang] [:span
         (language-check-box lang (contains? disabled-langs lang))
         (when (and (= idx 3) (< idx (dec (count langs)))) [:br])])
      langs)
     [:label {:style {:text-decoration "underline"}
              :on-click #(swap! state update :toggle-info not)}
      "(rankings overview)"]]))

(defn filter-controls []
  [:div
   [:input {:type "checkbox"
            :checked (@state :omit-edge-langs)
            :on-change #(swap! state update :omit-edge-langs not)}]
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
              :on-change #(swap! state assoc :which-langs (-> % .-target .-value))}
     [:option "All"] [:option "Functional"] [:option "Array"] [:option "System"]]]
   [:label styles/cb-font " Languages"]])

(defn footnotes []
  [:div (styles/footnote is-mobile?)
   [:br]
   [:label "1 - The number of (selected) ranking websites this language shows up in."] [:br] [:br]
   [:label "If you have suggestions or find a bug, you can open an "]
   [:a {:href "https://github.com/codereport/plr/issues/new"} [:label "issue"]]
   [:label " here."]])

(defn app-view []
  [:div {:style {:text-align "center"
                 :padding "50px"
                 :font-family "Ubuntu Mono,Consolas,IBM Plex Mono,Roboto Mono,Courier"}}
   [:label (styles/font 50) 
    (str (title-prefix (@state :which-langs)) 
         " Programming Language Rankings (2025 Mar)")] [:br] [:br]
   [:label (styles/font 25) "by code_report"] [:br]
   [social-links]
   [:br] [:br]

   (if (@state :toggle-info)
     [:div
      [:label {:style {:text-decoration "underline"}
               :on-click #(swap! state update :toggle-info not)} "(back)"] [:br] [:br]
      (info/table is-mobile?)]
     [:div [language-filters] [:br] [:label "-"] [:br] [filter-controls] [:br]
      (generate-table (read/get-all-sites 0) (map #(@state-check-boxes %) data/sites))
      [footnotes]])])

(defn render! []
  (rdom/render
   [app-view]
   (js/document.getElementById "app")))
