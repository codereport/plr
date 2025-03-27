(ns plr.styles)

(defn table [is-mobile?]
  {:style (merge {:font-family "JetBrains Mono, monospace"
                  :padding "10px 10px"
                  :font-size "20"
                  :margin-left "auto"
                  :margin-right "auto"
                  :text-align "center"}
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

(defn modal-content []
  {:style {:background-color "white"
           :padding "20px"
           :border-radius "8px"
           :max-width "80%"
           :max-height "80%"
           :overflow "auto"
           :position "relative"}})

(defn modal-close-button []
  {:style {:position "absolute"
           :top "10px"
           :right "10px"
           :border "none"
           :background "none"
           :font-size "20px"
           :cursor "pointer"}})

(defn button []
  {:style {:text-decoration "none"
           :background-color "#f0f0f0"
           :border "1px solid #ccc"
           :border-radius "4px"
           :padding "5px 10px"
           :margin-left "10px"
           :font-family "inherit"
           :font-size "0.9em"
           :cursor "pointer"
           :transition "all 0.2s ease-in-out"
           :box-shadow "0 1px 2px rgba(0,0,0,0.1)"}})

(defn app-container []
  {:style {:text-align "center"
           :padding "30px"
           :font-family "JetBrains Mono, monospace"
           :position "relative"}})

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
