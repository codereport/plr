(ns plr.data)

(def sites [:so :octo :rm :languish :pypl :ieee :tiobe])

(def names {:so "StackOverflow" :octo "Octoverse" :rm "RedMonk" :languish "Languish"
            :pypl "PYPL" :ieee "IEEE Spectrum" :tiobe "TIOBE"})

(def links {:octo     "https://octoverse.github.com/2022/top-programming-languages"
            :ieee     "https://spectrum.ieee.org/top-programming-languages-2022"
            :rm       "https://redmonk.com/sogrady/2023/05/16/language-rankings-1-23/"
            :languish "https://tjpalmer.github.io/languish/"
            :so       "https://survey.stackoverflow.co/2023/#most-popular-technologies-language"
            :pypl     "https://pypl.github.io/PYPL.html"
            :tiobe    "https://www.tiobe.com/tiobe-index/"})

(def odd ["Markdown" "CSS" "HTML" "SAS" "Jupyter Notebook" "Vue" "JSON" "Dockerfile" "SQL" "PLpgSQL" "ASP.NET" "Sass/SCSS"
          "XML" "TeX" "Batchfile" "Nginx" "Makefile" "CSV" "CMake" "Adblock Filter List" "Jinja" "YAML" "ApacheConf"
          "Svelte"])

(def functional ["Haskell" "Scala" "F#" "Lisp" "Scheme" "Racket" "Erlang" "Elixir" "ML" "OCaml" "Elm" "Clojure" "Standard ML" "Reason"])
(def arrays ["APL" "J" "q" "BQN" "Julia" "Fortran" "MATLAB" "R" "Futhark"])