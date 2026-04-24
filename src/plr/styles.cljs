(ns plr.styles)

(def theme-colors
  {:light {:background "white"
           :text "black"
           :border "#ccc"
           :button-bg "#f0f0f0"
           :button-bg-hover "#e0e0e0"
           :shadow "rgba(0,0,0,0.1)"
           :shadow-hover "rgba(0,0,0,0.15)"}
   :dark  {:background "#121212"
           :text "white"
           :border "#555"
           :button-bg "#2a2a2a"
           :button-bg-hover "#3a3a3a"
           :shadow "rgba(255,255,255,0.05)"
           :shadow-hover "rgba(255,255,255,0.1)"}})

(defn theme-toggle-style [theme]
  {:position "absolute"
   :left "30px"
   :top "30px"
   :background "transparent"
   :border (str "2px solid " (get-in theme-colors [theme :border]))
   :border-radius "25px"
   :padding "8px 16px"
   :color (get-in theme-colors [theme :text])
   :cursor "pointer"
   :font-family "'JetBrains Mono', monospace"
   :font-weight "bold"
   :transition "all 0.2s ease-in-out"})

(defn table [is-mobile?]
  {:style (merge {:font-family "JetBrains Mono, monospace"
                  :padding "10px 10px"
                  :font-size "20"
                  :margin-left "auto"
                  :margin-right "auto"
                  :text-align "center"
                  :color "inherit"}
                 (if is-mobile? {} {:display "inline"}))})

(defn font [font-size]
  {:style {:font-family "JetBrains Mono, monospace"
           :font-size (str font-size)
           :font-weight "bold"}})

(def cb-font (font 18))
(def cell  {:style {:padding "12px 30px" :font-size "24"}}) ; main cells
(def cell2 {:style {:padding "12px 30px" :font-size "16"}}) ; column headers
(defn footnote [is-mobile?] {:style {:font-size (if is-mobile? 12 10)}})

;; New style functions
(def emoji-style {:width "32px" :height "32px" :vertical-align "middle"})

(defn social-icon-style []
  {:height 40 
   :width 40
   :transition "all 0.2s ease-in-out"
   :transform "scale(1)"})

(defn modal-overlay []
  {:style {:position "fixed"
           :top 0
           :left 0
           :width "100%"
           :height "100%"
           :display "flex"
           :justify-content "center"
           :align-items "center"
           :background-color "rgba(0, 0, 0, 0.5)"
           :z-index 1000}})

(defn modal-content [theme]
  (let [colors (get theme-colors theme)]
    {:style {:background-color (:background colors)
             :color (:text colors)
             :padding "20px"
             :border-radius "8px"
             :max-width "80%"
             :max-height "80%"
             :overflow "auto"
             :position "relative"}}))

(defn modal-close-button [theme]
  (let [colors (get theme-colors theme)]
    {:style {:position "absolute"
             :top "10px"
             :right "10px"
             :border "none"
             :background "none"
             :font-size "20px"
             :color (:text colors)
             :cursor "pointer"}}))

(defn button [theme]
  (let [colors (get theme-colors theme)]
    {:style {:text-decoration "none"
             :background-color (:button-bg colors)
             :border (str "1px solid " (:border colors))
             :border-radius "4px"
             :padding "5px 10px"
             :margin-left "10px"
             :font-family "inherit"
             :font-size "0.9em"
             :color (:text colors)
             :cursor "pointer"
             :transition "all 0.2s ease-in-out"
             :box-shadow (str "0 1px 2px " (:shadow colors))}}))

(defn app-container [theme]
  (let [colors (get theme-colors theme)]
    {:style {:text-align "center"
             :padding "30px"
             :font-family "JetBrains Mono, monospace"
             :position "relative"
             :background-color (:background colors)
             :color (:text colors)
             :min-height "100vh"
             :box-sizing "border-box"}}))

(defn youtube-link []
  {:position "absolute"
   :right "30px"
   :top "30px"
   :cursor "pointer"})

(defn youtube-image []
  {:height "60px"
   :width "60px"
   :transition "all 0.2s ease-in-out"
   :transform "scale(1)"})

(defn language-item []
  {:style {:margin "auto 5px"}})

(defn inline-display []
  {:style {:display "inline"}})

(defn social-links-container []
  {:style {:display "flex" 
           :gap "10px" 
           :justify-content "center" 
           :margin-top "10px"}})
