(ns plr.core
  (:require
   [reagent.dom :as rdom]
   [reagent.core :as r]
   [clojure.string :as str]
   [plr.imgs :as imgs]
   [plr.data :as data]
   [plr.styles :as styles]))

(defonce state             (r/atom {:results-table [:tr]}))
(defonce cb-edge-langs     (r/atom true))
(defonce cb-stack-overflow (r/atom true))
(defonce cb-octoverse      (r/atom true))
(defonce cb-redmonk        (r/atom true))
(defonce cb-languish       (r/atom true))
(defonce cb-pypl           (r/atom false))
(defonce cb-iee-spectrum   (r/atom false))
(defonce cb-tiobe          (r/atom false))

;; (def include-mask [@cb-stack-overflow  @cb-octoverse  @cb-redmonk  @cb-languish  @cb-pypl  @cb-iee-spectrum @cb-tiobe])
(def sites        [data/stack-overflow data/octoverse data/redmonk data/languish data/pypl data/ieee        data/tiobe])

(defn avg [coll] (/ (reduce + coll) (count coll)))
(defn format [num] (/ (int (* num 100)) 100))
(defn in? [e coll] (some #(= e %) coll))

(defn generate-row [rank [score n lang]]
  [:tr
   [:td styles/cell (str (+ rank 1))]
   [:td [:img {:src (str/join ["/media/" (get imgs/logo-map lang)]) :width "40px" :height "40px"}]]
   [:td styles/cell lang]
   [:td styles/cell (format score)]
   [:td styles/cell n]])

(defn generate-table [rankings mask]
  [:table styles/table
   (->> rankings
        (map vector mask)
        (filter first)
        (map last)
        (map (partial remove #(and (in? % data/odd) @cb-edge-langs)))
        (map (partial map-indexed vector))
        (apply concat)
        (concat data/extras)
        (group-by last)
        (map (fn [[k v]] [(+ (avg (map first v)) 1) ; score
                          (count (map first v))     ; n
                          k]))                      ; lang
        (sort)
        (map-indexed vector)
        (take 10)
        (map (partial apply generate-row)))])

(defn app-view []
  [:div {:style {:search-text ""
                 :text-align "center"
                 :padding "50px"}}
   [:label (styles/font 50) "Programming Language Rankings"] [:br] [:br]
   [:label (styles/font 25) "by code_report"] [:br] [:br]
   [:div
    [:input {:type "checkbox"
             :checked @cb-stack-overflow
             :on-change #(swap! cb-stack-overflow not)}] [:label styles/cb-font " StackOverflow "]
    [:input {:type "checkbox"
             :checked @cb-octoverse
             :on-change #(swap! cb-octoverse not)}] [:label styles/cb-font " Octoverse "]
    [:input {:type "checkbox"
             :checked @cb-redmonk
             :on-change #(swap! cb-redmonk not)}] [:label styles/cb-font " Redmonk "]
    [:input {:type "checkbox"
             :checked @cb-languish
             :on-change #(swap! cb-languish not)}] [:label styles/cb-font " Languish "] [:br]
    [:input {:type "checkbox"
             :checked @cb-pypl
             :on-change #(swap! cb-pypl not)}] [:label styles/cb-font " PYPL "]
    [:input {:type "checkbox"
             :checked @cb-iee-spectrum
             :on-change #(swap! cb-iee-spectrum not)}] [:label styles/cb-font " IEE Spectrum "]
    [:input {:type "checkbox"
             :checked @cb-tiobe
             :on-change #(swap! cb-tiobe not)}] [:label styles/cb-font " TIOBE "] [:br] [:br] [:br]
    [:input {:type "checkbox"
             :checked @cb-edge-langs
             :on-change #(swap! cb-edge-langs not)}]
    [:label styles/cb-font " Exclude \"Edge Languages\""] [:br] [:br]]
   (generate-table sites [@cb-stack-overflow  @cb-octoverse  @cb-redmonk  @cb-languish  @cb-pypl  @cb-iee-spectrum @cb-tiobe])
   (@state :results-table)])

(defn render! []
  (rdom/render
   [app-view]
   (js/document.getElementById "app")))
