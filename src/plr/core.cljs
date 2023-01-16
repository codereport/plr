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
(defonce num-langs         (r/atom 10))

(def media "/public/media")
;; (def media "/media")

(def sites [data/stack-overflow data/octoverse data/redmonk data/languish data/pypl data/ieee data/tiobe])

(defn avg [coll] (/ (reduce + coll) (count coll)))
(defn format [num] (/ (int (* num 100)) 100))
(defn in? [e coll] (some #(= e %) coll))

(defn generate-row [rank [avg n lang]]
  [:tr
   [:td styles/cell (str (+ rank 1))]
   [:td [:img {:src (str/join [media "/logos/" (get imgs/logo-map lang)]) :width "40px" :height "40px"}]]
   [:td styles/cell lang]
   [:td styles/cell (format avg)]
   [:td styles/cell n]])

(defn generate-table [rankings mask]
  [:table styles/table
   [:tr {:style {:font-weight "bold"}} [:td] [:td] [:td "Language"] [:td "Avg"] [:td "n¹"]]
   (->> rankings
        (map vector mask)
        (filter first)
        (map last)
        (map (partial remove #(and (in? % data/odd) @cb-edge-langs)))
        (map (partial map-indexed vector))
        (apply concat)
        (concat data/extras)
        (group-by last)
        (map (fn [[k v]] [(+ (avg (map first v)) 1) ; avg
                          (count (map first v))     ; n
                          k]))                      ; lang
        (sort)
        (map-indexed vector)
        (take @num-langs)
        (map (partial apply generate-row)))])

(defn app-view []
  [:div {:style {:search-text ""
                 :text-align "center"
                 :padding "50px"
                 :font-family "Courier"}}
   [:label (styles/font 50) "Programming Language Rankings"] [:br] [:br]
   [:label (styles/font 25) "by code_report"] [:br]
   [:a {:href "https://www.twitter.com/code_report"}  [:img {:src "public/media/icons/twitter.png" :width "40px" :height "40px"}]]
   [:a {:href "https://www.youtube.com/c/codereport"} [:img {:src "./public/media/icons/youtube.png" :width "40px" :height "40px"}]]
   [:a {:href "https://www.github.com/codereport"}    [:img {:src "/public/media/icons/github.png"  :width "40px" :height "40px"}]]
   [:br] [:br]

   ; TODO clean up repetitive checkbox code.
   [:div
    [:input {:type "checkbox"
             :checked @cb-stack-overflow
             :on-change #(swap! cb-stack-overflow not)}]
    [:label styles/cb-font " StackOverflow"]
    [:a {:href "https://survey.stackoverflow.co/2022/#most-popular-technologies-language"}
     [:img {:src "/media/icons/link.png" :width "16px" :height "16px"}]]
    [:label styles/cb-font " "]

    [:input {:type "checkbox"
             :checked @cb-octoverse
             :on-change #(swap! cb-octoverse not)}] [:label styles/cb-font " Octoverse"]
    [:a {:href "https://octoverse.github.com/2022/top-programming-languages"}
     [:img {:src "/media/icons/link.png" :width "16px" :height "16px"}]]
    [:label styles/cb-font " "]

    [:input {:type "checkbox"
             :checked @cb-redmonk
             :on-change #(swap! cb-redmonk not)}] [:label styles/cb-font " Redmonk"]
    [:a {:href "https://redmonk.com/sogrady/2022/10/20/language-rankings-6-22/"}
     [:img {:src "/media/icons/link.png" :width "16px" :height "16px"}]]
    [:label styles/cb-font " "]

    [:input {:type "checkbox"
             :checked @cb-languish
             :on-change #(swap! cb-languish not)}] [:label styles/cb-font " Languish"]
    [:a {:href "https://tjpalmer.github.io/languish/"}
     [:img {:src "/media/icons/link.png" :width "16px" :height "16px"}]]
    [:label styles/cb-font " "]
    [:br]

    [:input {:type "checkbox"
             :checked @cb-pypl
             :on-change #(swap! cb-pypl not)}] [:label styles/cb-font " PYPL"]
    [:a {:href "https://pypl.github.io/PYPL.html"}
     [:img {:src "/media/icons/link.png" :width "16px" :height "16px"}]]
    [:label styles/cb-font " "]

    [:input {:type "checkbox"
             :checked @cb-iee-spectrum
             :on-change #(swap! cb-iee-spectrum not)}] [:label styles/cb-font " IEE Spectrum"]
    [:a {:href "https://spectrum.ieee.org/top-programming-languages-2022"}
     [:img {:src "/media/icons/link.png" :width "16px" :height "16px"}]]
    [:label styles/cb-font " "]
    [:input {:type "checkbox"
             :checked @cb-tiobe
             :on-change #(swap! cb-tiobe not)}] [:label styles/cb-font " TIOBE"]
    [:a {:href "https://www.tiobe.com/tiobe-index/"}
     [:img {:src "/media/icons/link.png" :width "16px" :height "16px"}]]
    [:label styles/cb-font " "]
    [:br] [:label "-"] [:br]
    [:input {:type "checkbox"
             :checked @cb-edge-langs
             :on-change #(swap! cb-edge-langs not)}]
    [:label styles/cb-font " Exclude \"Edge Languages\""]
    [:br] [:label "-"] [:br]
    [:form
     [:label styles/cb-font "Number of Languages: "]
     [:select {:value @num-langs
               :on-change #(reset! num-langs (-> % .-target .-value js/Number))}
      [:option 10]
      [:option 20]]]]

   (generate-table sites [@cb-stack-overflow  @cb-octoverse  @cb-redmonk  @cb-languish  @cb-pypl  @cb-iee-spectrum @cb-tiobe])
   (@state :results-table)
   [:br]
   [:label "1 - The number of (selected) ranking websites this language shows up in."]])

(defn render! []
  (rdom/render
   [app-view]
   (js/document.getElementById "app")))
