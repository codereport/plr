(ns demo.core
  (:require
   [reagent.dom :as rdom]
   [reagent.core :as r]
   [clojure.string :as str]
   ))

(defonce state (r/atom {:top-padding "100px"
                        :results-table [:tr]}))

(def logo-map
  {"APL"        "apl_logo.png"
   "Assembly"   "assembly_logo.png"
   "Shell"      "bash_logo.png"
   "BQN"        "bqn_logo.svg"
   "C"          "c_logo.png"
   "C++"        "cpp_logo.png"
   "C#"         "csharp_logo.png"
   "Clojure"    "clojure_logo.png"
   "Crystal"    "crystal_logo.svg"
   "CSS"        "css_logo.png"
   "CUDA"       "thrust_logo.jfif"
   "D"          "d_logo.png"
   "Dart"       "dart_logo.png"
   "Elixir"     "elixir_logo.png"
   "Elm"        "elm_logo.png"
   "Erlang"     "erlang_logo.png"
   "F#"         "fsharp_logo.png"
   "Fortran"    "fortran_logo.png"
   "Go"         "go_logo.png"
   "Groovy"     "groovy_logo.jpeg"
   "Haskell"    "haskell_logo.svg"
   "HTML"       "html_logo.svg"
   "J"          "j_logo.png"
   "Java"       "java_logo.png"
   "JavaScript" "javascript_logo.png"
   "Julia"      "julia_logo.svg"
   "Kotlin"     "kotlin_logo.png"
   "LISP"       "LISP_logo.png"
   "Lua"        "lua_logo.png"
   "MATLAB"     "matlab_logo.png"
   "Nim"        "nim_logo.png"
   "Objective-C""objc_logo.png"
   "OCaml"      "ocaml_logo.jpg"
   "Pharo"      "pharo_logo.png"
   "PHP"        "php_logo.png"
   "PowerShell" "powershell_logo.png"
   "Python"     "python_logo.png"
   "q"          "kx-logo.png"
   "R"          "r_logo.png"
   "Racket"     "racket_logo.png"
   "Ruby"       "ruby_logo.png"
   "Rust"       "rust_logo.png"
   "SAS"        "sas_logo.png"
   "Scala"      "scala_logo2.png"
   "SQL"        "sql_logo.png"
   "Swift"      "swift_logo.png"
   "TypeScript" "ts.png"
   "Zig"        "zig_logo.svg"})

(def octoverse ["JavaScript" "Python" "Java" "TypeScript" "C#" "C++" "PHP" "Shell" "C" "Ruby"])
(def ieee ["Python" "C" "C++" "C#" "Java" "SQL" "JavaScript" "R" "HTML" "TypeScript" "Go" "PHP" "Shell" "Ruby" "Scala" "MATLAB" "SAS" "Assembly" "Kotlin" "Rust"])
(def redmonk ["JavaScript" "Python" "Java" "PHP" "C#" "CSS" "C++" "TypeScript" "Ruby" "C" "Swift" "R" "Objective-C" "Shell" "Scala" "Go" "PowerShell" "Kotlin" "Rust" "Dart"])
(def languish ["Python" "JavaScript" "TypeScript" "Java" "C++" "C#" "Go" "HTML" "Markdown" "C" "PHP" "Rust" "CSS" "Shell" "Kotlin" "Jupyter Notebook" "R" "Dart" "Swift" "Vue" "SQL" "Ruby" "JSON" "Lua" "Dockerfile" "PowerShell"])
(def stack-overflow ["JavaScript" "HTML" "SQL" "Python" "TypeScript" "Java" "Shell" "C#" "C++" "PHP" "C" "PowerShell" "Go" "Rust" "Kotlin" "Dart" "Ruby" "Assembly" "Swift" "R" "VBA" "MATLAB" "Lua" "Groovy" "Delphi" "Scala" "Objective-C" "Perl"])
(def pypl ["Python" "Java" "JavaScript" "C#" "C++" "PHP" "R" "TypeScript" "Swift" "Objective-C" "Go" "Rust" "Kotlin" "MATLAB" "Ruby" "VBA" "Ada" "Dart" "Scala" "Visual Basic"])

(def extras [; Languish
             [29 "VBA"]
             [32 "Objective-C"]
             [39 "Assembly"]
             [166 "Ada"]
             [261 "Visual Basic"]])


(def odd ["Markdown" "CSS" "HTML" "SAS" "Jupyter Notebook" "Vue" "JSON" "Dockerfile" "SQL"])

(defn avg [coll] (/ (reduce + coll) (count coll)))
(defn format [num] (/ (int (* num 100)) 100))
(defn in? [e coll] (some #(= e %) coll))

(defn generate-row [rank [score n lang]]
  [:tr
   [:td {:style {:padding "12px 30px"}} (str (+ rank 1))]
   [:td [:img {:src (str/join ["/media/" (get logo-map lang)]) :width "40px" :height "40px"}]]
   [:td {:style {:padding "12px 30px"}} (str lang)]
   [:td {:style {:padding "12px 30px"}} (format score)]
   [:td {:style {:padding "12px 30px"}} (format n)]])

(defn generate-table [rankings]
  [:table {:style {:font-family "Consolas"
                   :padding "12px 12px"
                   :font-size "30"
                   :margin-left "auto"
                   :margin-right "auto"
                   :text-align "center"}}
   (->> rankings
        (map (partial remove #(in? % odd)))
        (map (partial map-indexed vector))
        (apply concat)
        (concat extras)
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
  {:style {:font-family "Consolas"
           :font-size (str font-size)
           :font-weight "bold"}})

(def cb-style (style 23))

(defn app-view []
  [:div {:style {:search-text ""
                 :text-align "center"
                 :padding (@state :top-padding)}}
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
           (generate-table [octoverse
                            ;; ieee
                            redmonk
                            ;; pypl
                            languish
                            stack-overflow]))])

(defn render! []
  (rdom/render
   [app-view]
   (js/document.getElementById "app")))
