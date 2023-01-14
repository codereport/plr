(ns plr.core
  (:require
   [reagent.dom :as rdom]
   [reagent.core :as r]
   [clojure.string :as str]
   [plr.imgs :as imgs]
   [plr.data :as data]
   ))

(defonce state (r/atom {:results-table [:tr]}))

(defn avg [coll] (/ (reduce + coll) (count coll)))
(defn format [num] (/ (int (* num 100)) 100))
(defn in? [e coll] (some #(= e %) coll))

(defn generate-row [rank [score n lang]]
  [:tr
   [:td {:style {:padding "12px 30px"}} (str (+ rank 1))]
   [:td [:img {:src (str/join ["/media/" (get imgs/logo-map lang)]) :width "40px" :height "40px"}]]
   [:td {:style {:padding "12px 30px"}} (str lang)]
   [:td {:style {:padding "12px 30px"}} (format score)]
   [:td {:style {:padding "12px 30px"}} (format n)]])

(defn generate-table [rankings]
  [:table {:style {:font-family "Courier"
                   :padding "12px 12px"
                   :font-size "30"
                   :margin-left "auto"
                   :margin-right "auto"
                   :text-align "center"}}
   (->> rankings
        (map (partial remove #(in? % data/odd)))
        (map (partial map-indexed vector))
        (apply concat)
        (concat data/extras)
        (group-by last)
        (map (fn [[k v]] [(+ (avg (map first v)) 1)
                          (count (map first v))
                          k]))
        sort
        (map-indexed vector)
        (take 20)
        (map (partial apply generate-row)))
  ])

(defn style [font-size]
  {:style {:font-family "Courier"
           :font-size (str font-size)
           :font-weight "bold"}})

(def cb-style (style 23))

(defn app-view []
  [:div {:style {:search-text ""
                 :text-align "center"
                 :padding "100px"}}
   [:label (style 50) "Programming Language Rankings"] [:br] [:br]
   [:label (style 25) "brought to you by code_report"] [:br] [:br]
   [:div
    [:input {:type "checkbox"}] [:label cb-style " StackOverflow "]
    [:input {:type "checkbox"}] [:label cb-style " Octoverse "]
    [:input {:type "checkbox"}] [:label cb-style " Redmonk "]
    [:input {:type "checkbox"}] [:label cb-style " Languish "]
    [:br]
    [:input {:type "checkbox"}] [:label cb-style " PYPL "]
    [:input {:type "checkbox"}] [:label cb-style " IEE Spectrum "]
    [:input {:type "checkbox"}] [:label cb-style " TIOBE "]] [:br]
   (@state :resutls-table
           (generate-table [data/octoverse
                            data/ieee
                            data/redmonk
                            data/pypl
                            data/languish
                            data/stack-overflow]))])

(defn render! []
  (rdom/render
   [app-view]
   (js/document.getElementById "app")))
