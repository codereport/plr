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
