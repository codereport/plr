(ns demo.core
  (:require
    [reagent.dom :as rdom]
    [reagent.core :as r]))

(defonce state (r/atom {:counter 1}))

(defn app-view []
  [:div
   [:h1 "Hello World!"]
   (:counter @state)
   [:button {:on-click (fn []
                         (swap! state update :counter inc))}
    "+1"]])

(defn render! []
  (rdom/render
    [app-view]
    (js/document.getElementById "app")))



