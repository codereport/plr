(ns plr.core
  (:require
   [reagent.dom :as rdom]
   [reagent.core :as r]
   [clojure.string :as str]
   [kixi.stats.core :as kixi]
   [plr.imgs :as imgs]
   [plr.data :as data]
   [plr.styles :as styles]
   [plr.info :as info]
   ["react-social-icons" :refer [SocialIcon]])
  (:require-macros
   [plr.helper :as read]))

(def is-mobile? (some #(str/includes? js/navigator.userAgent %) ["Android" "iPhone"]))

(defonce state (r/atom {:num-langs       (if is-mobile? 10 20)
                        :actual-langs    (if is-mobile? 10 20)
                        :delta           3
                        :toggle-info     false
                        :omit-edge-langs true
                        :which-langs     "All"
                        :show-info-modal false}))

(defonce state-check-boxes (r/atom {:so true :octo true :rm true :languish true :jb false :pypl false :ieee false :tiobe false :githut false}))

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
      (= delta :new) [:img {:src "/media/emojis/star.png" :alt "Star" :style styles/emoji-style}]
      (zero? delta) "-"
      (neg? delta) [:span [:img {:src "/media/emojis/green_circle.png" :alt "Green Up" :style styles/emoji-style}] (str " (" val ")")]
      :else [:span [:img {:src "/media/emojis/red_circle.png" :alt "Red Down" :style styles/emoji-style}] (str " (" val ")")])))

(defn get-prev-site-langs [delta]
  (case delta
    3 (read/get-all-sites 3)
    6 (read/get-all-sites 6)
    12 (read/get-all-sites 12)
    24 (read/get-all-sites 24)))

(defn generate-row [rank [avg stdev n lang] prev-rankings]
  (let [delta (if (contains? prev-rankings lang)
                (- rank (prev-rankings lang))
                :new)]
    [:tr
     [:td styles/cell (str (+ rank 1))]
     [:td [:img {:src (str/join ["/media/logos/" (get imgs/logo-map lang)]) :width "40px" :height "40px"}]]
     [:td styles/cell lang]
     [:td styles/cell (format avg)]
     [:td styles/cell (format stdev)]
     [:td styles/cell n]
     [:td styles/cell (format-delta delta)]]))

(defn make-table [rows]
  (let [mask            (map #(@state-check-boxes %) [:so :octo :rm :languish :jb :ieee])
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
  (let [id-str (name lang)]
    [:div (styles/inline-display)
     [:input {:type "checkbox"
              :id id-str
              :checked (@state-check-boxes lang)
              :disabled disable
              :on-change #(swap! state-check-boxes update lang not)}]
     [:div (styles/inline-display) 
      [:label (merge {:for id-str} styles/cb-font) 
       (str " " (get data/names lang))]
      [:a {:href (get data/links lang)} 
       [:img {:src (str "/media/link.png") :width "16px" :height "16px"}]]]]))

(defn title-prefix [which-langs]
  (when (not= which-langs "All") which-langs))

(defn social-icon [props]
  [:> SocialIcon (merge {:style (styles/social-icon-style)}
                       {:onMouseOver (fn [e] 
                                      (-> e .-currentTarget .-style .-transform (set! "scale(1.25)")))
                        :onMouseOut (fn [e] 
                                     (-> e .-currentTarget .-style .-transform (set! "scale(1)")))}
                       props)])

(defn social-links []
  [:div (styles/social-links-container)
   [social-icon {:url "https://bsky.app/profile/codereport.bsky.social"}]
   [social-icon {:url "https://mastodon.social/@code_report" :network "mastodon"}]
   [social-icon {:url "https://www.twitter.com/code_report"}]
   [social-icon {:url "https://www.youtube.com/c/codereport"}]
   [social-icon {:url "https://www.github.com/codereport"}]])

(defn info-modal []
  (when (@state :show-info-modal)
    [:div (styles/modal-overlay)
     [:div (styles/modal-content)
      [:button (merge (styles/modal-close-button)
                     {:on-click #(swap! state assoc :show-info-modal false)})
       "×"]
      [:h2 "Rankings Overview"]
      (info/table is-mobile?)]]))

(defn language-filters []
  (let [sites-order [:so :octo :rm :languish :jb :ieee :pypl :tiobe :githut]
        disabled-langs #{:pypl :tiobe :githut}]
    [:div 
     (doall
       (map-indexed 
         (fn [idx lang] 
           ^{:key (str "lang-" lang)}
           [:span (styles/language-item)
             (language-check-box lang (contains? disabled-langs lang))
             (when (and (= idx 4) (< idx (dec (count sites-order)))) [:br])])
         sites-order))
     [:button (merge (styles/button)
                    {:on-mouse-over (fn [e] 
                                     (-> e .-target .-style .-backgroundColor (set! "#e0e0e0"))
                                     (-> e .-target .-style .-transform (set! "scale(1.05)"))
                                     (-> e .-target .-style .-boxShadow (set! "0 2px 5px rgba(0,0,0,0.15)")))
                     :on-mouse-out (fn [e] 
                                    (-> e .-target .-style .-backgroundColor (set! "#f0f0f0"))
                                    (-> e .-target .-style .-transform (set! "scale(1)"))
                                    (-> e .-target .-style .-boxShadow (set! "0 1px 2px rgba(0,0,0,0.1)")))
                     :on-click #(swap! state assoc :show-info-modal true)})
      "Rankings Overview"]]))

(defn filter-controls []
  [:div
   [:input {:type "checkbox"
            :id "exclude_edge_languages"
            :checked (@state :omit-edge-langs)
            :on-change #(swap! state update :omit-edge-langs not)}]
   [:label (merge styles/cb-font {:for "exclude_edge_languages"}) " Exclude \"Edge Languages\" | "]
   [:form {:style {:display "inline"}}
    [:label styles/cb-font "Number of Languages: "]
    [:select {:value (@state :num-langs)
              :on-change #(swap! state assoc :num-langs (-> % .-target .-value js/Number))}
     [:option 10] [:option 20]]] [:br]
   [:form {:style {:display "inline"}}
    [:label styles/cb-font "Months for Delta (Δ): "]
    [:select {:value (@state :delta)
              :on-change #(swap! state assoc :delta (-> % .-target .-value js/Number))}
     [:option 3] [:option 6] [:option 12] [:option 24]]]
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
  [:div (styles/app-container)
   [:a {:href "https://www.youtube.com/c/codereport"
        :style (styles/youtube-link)}
    [:img {:src "/media/code_report_circle.png"
           :style (styles/youtube-image)
           :on-mouse-over (fn [e] 
                           (-> e .-target .-style .-transform (set! "scale(1.25)")))
           :on-mouse-out (fn [e] 
                          (-> e .-target .-style .-transform (set! "scale(1)")))}]]
   [:label (styles/font 40) 
    (str (title-prefix (@state :which-langs)) 
         " Programming Language Rankings (2025 Sep)")] [:br] 
   [:label (styles/font 25) "by code_report"]
   [social-links]
   [:br]
   [info-modal]
   [:div [language-filters] [:br] [filter-controls] [:br]
    (generate-table (read/get-all-sites 0) (map #(@state-check-boxes %) [:so :octo :rm :languish :jb :ieee]))
    [footnotes]]])

(defn render! []
  (rdom/render
   [app-view]
   (js/document.getElementById "app")))
