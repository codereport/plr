(ns plr.styles)

(def table
  {:style {:font-family "Courier"
           :padding "12px 12px"
           :font-size "30"
           :margin-left "auto"
           :margin-right "auto"
           :text-align "center"}})

(defn font [font-size]
  {:style {:font-family "Courier"
           :font-size (str font-size)
           :font-weight "bold"}})

(def cb-font (font 23))

(def cell {:style {:padding "12px 30px"}})
