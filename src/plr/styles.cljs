(ns plr.styles)

(defn table [is-mobile?]
  {:style (merge {:font-family "Ubuntu Mono,Consolas,Courier"
                  :padding "12px 12px"
                  :font-size "30"
                  :margin-left "auto"
                  :margin-right "auto"
                  :text-align "center"}
                 (if is-mobile? {} {:display "inline"}))})

(defn font [font-size]
  {:style {:font-family "Ubuntu Mono,Consolas,Courier"
           :font-size (str font-size)
           :font-weight "bold"}})

(def cb-font (font 23))

(def cell {:style {:padding "12px 30px"
                   :font-size "30"}})

(def cell2 {:style {:padding "12px 30px"
                    :font-size "24"}})

(defn footnote [is-mobile?]
  {:style {:font-size (if is-mobile? 12 16)}})