(ns plr.core
  (:require
   [reagent.dom :as rdom]
   [reagent.core :as r]
   [clojure.string :as str]
   [kixi.stats.core :as kixi]
   [plr.imgs :as imgs]
   [plr.data :as data]
   [plr.styles :as styles]
   [plr.info :as info]))

(def is-mobile? (some #(str/includes? js/navigator.userAgent %) ["Android" "iPhone"]))

(defonce state (r/atom {:results-table   [:tr]
                        :num-langs       (if is-mobile? 10 20)
                        :toggle-info     false
                        :omit-edge-langs true}))

(defonce state-check-boxes (r/atom {:so true :octo true :rm true :languish true :pypl false :ieee false :tiobe false}))

(def media "/public/media")
;; (def media "/media")

(def site-langs [data/stack-overflow data/octoverse data/redmonk data/languish data/pypl data/ieee data/tiobe])

(defn map-indexed-from [n f coll] (map f (range n 1000) coll))
(defn avg [coll] (transduce identity kixi/mean coll))
(defn stdev [coll] (transduce identity kixi/standard-deviation coll))
(defn format [num] (/ (int (* num 100)) 100))
(defn in? [e coll] (some #(= e %) coll))

(defn generate-row [rank [avg stdev n lang]]
  [:tr
   [:td styles/cell (str (+ rank 1))]
   [:td [:img {:src (str/join [media "/logos/" (get imgs/logo-map lang)]) :width "40px" :height "40px"}]]
   [:td styles/cell lang]
   [:td styles/cell (format avg)]
   [:td styles/cell (format stdev)]
   [:td styles/cell n]])

(defn filter-langs [mask rankings]
  (->> rankings
       (map vector mask)
       (filter first)
       (map last)))

(defn make-table [rows]
  [:table (styles/table is-mobile?)
   [:tr {:style {:font-weight "bold"}} [:td] [:td] [:td "Language"] [:td "Avg"] [:td "StDev"] [:td "n¹"]]
   (map (partial apply generate-row) rows)])

(defn generate-table [rankings mask]
  (let [row-data (->> rankings
                      (filter-langs mask)
                      (map (partial remove #(and (in? % data/odd) (@state :omit-edge-langs))))
                      (map (partial map-indexed-from 1 vector))
                      (apply concat)
                      (concat (->> data/extras
                                   (filter-langs mask)
                                   (map (partial remove #(and (in? (last %) data/odd) (@state :omit-edge-langs))))
                                   (apply concat)))
                      (group-by last)
                      (map (fn [[k v]] (let [vals (map first v)]
                                         [(avg vals) (stdev vals) (count vals) k])))
                      (sort)
                      (map-indexed vector)
                      (take (@state :num-langs)))]
    (if (or (not= (@state :num-langs) 20) is-mobile?)
      (make-table row-data)
      [:div (make-table (take 10 row-data)) (make-table (drop 10 row-data))])))

(defn language-check-box [lang]
  [:div {:style {:display "inline"}}
   [:input {:type "checkbox"
            :checked (@state-check-boxes lang)
            :on-change #(swap! state-check-boxes assoc lang (not (@state-check-boxes lang)))}]
   [:div {:style {:display "inline"}} [:label styles/cb-font (str/join [" " (get data/names lang)])]
    [:a {:href (get data/links lang)} [:img {:src (str/join [media "/icons/link.png"]) :width "16px" :height "16px"}]]
    [:label styles/cb-font " "]]])

(defn app-view []
  [:div {:style {:text-align "center"
                 :padding "50px"
                 :font-family "Ubuntu Mono,Consolas,IBM Plex Mono,Roboto Mono,Courier"}}
   [:label (styles/font 50) "Programming Language Rankings (2023)"] [:br] [:br]
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
            (language-check-box :so)
            (language-check-box :octo)
            (language-check-box :rm)
            (language-check-box :languish) [:br]
            (language-check-box :pypl)
            (language-check-box :ieee)
            (language-check-box :tiobe)
            [:label {:style {:text-decoration "underline"}
                     :on-click #(swap! state assoc :toggle-info (not (@state :toggle-info)))}  "(rankings overview)"]
            [:br] [:label "-"] [:br]
            [:input {:type "checkbox"
                     :checked (@state :omit-edge-langs)
                     :on-change #(swap! state assoc :omit-edge-langs (not (@state :omit-edge-langs)))}]
            [:label styles/cb-font " Exclude \"Edge Languages\""]
            [:br] [:label "-"] [:br]
            [:form
             [:label styles/cb-font "Number of Languages: "]
             [:select {:value (@state :num-langs)
                       :on-change #(swap! state assoc :num-langs (-> % .-target .-value js/Number))}
              [:option 10] [:option 20]]]]

      (generate-table site-langs (map #(@state-check-boxes %) data/sites))
      (@state :results-table) [:br]
      [:div (styles/footnote is-mobile?)
       [:label "1 - The number of (selected) ranking websites this language shows up in."] [:br] [:br]
       [:label "If you have suggestions or find a bug, you can open an "]
       [:a {:href "https://github.com/codereport/plr/issues/new"} [:label "issue"]]
       [:label " here."]]])])

(defn render! []
  (rdom/render
   [app-view]
   (js/document.getElementById "app")))
