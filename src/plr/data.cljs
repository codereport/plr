(ns plr.data)

(def sites [:so :octo :rm :languish :pypl :ieee :tiobe])

(def names {:so "StackOverflow" :octo "Octoverse" :rm "RedMonk" :languish "Languish"
            :pypl "PYPL" :ieee "IEEE Spectrum" :tiobe "TIOBE"})

(def links {:octo     "https://github.blog/wp-content/uploads/2023/11/github-top-10-programming-languages.png?w=1024&resize=1024%2C576"
            :ieee     "https://spectrum.ieee.org/the-top-programming-languages-2023"
            :rm       "https://redmonk.com/sogrady/2023/05/16/language-rankings-1-23/"
            :languish "https://tjpalmer.github.io/languish/"
            :so       "https://survey.stackoverflow.co/2024/technology/"
            :pypl     "https://pypl.github.io/PYPL.html"
            :tiobe    "https://www.tiobe.com/tiobe-index/"})

; Lisp is included here because SO groups every Lisp into one which collides with Languish having all the Lisp dialects
(def odd ["Markdown" "CSS" "HTML" "SAS" "Jupyter Notebook" "Vue" "JSON" "Dockerfile" "SQL" "PLpgSQL" "ASP.NET" "Sass/SCSS"
          "XML" "TeX" "Batchfile" "Nginx" "Makefile" "CSV" "CMake" "Adblock Filter List" "Jinja" "YAML" "ApacheConf"
          "Svelte" "Lisp"])

(def functional ["Haskell" "Scala" "F#" "Lisp" "Scheme" "Racket" "Erlang" "Elixir" "ML" "OCaml" "Elm" "Clojure" "Standard ML"
                 "Reason" "Emacs Lisp" "Common Lisp" "NewLisp" "PicoLisp" "PureScript" "Futhark" "LFE" "jq" "Gleam"])

(def arrays ["APL" "J" "q" "BQN" "Julia" "Fortran" "MATLAB" "R" "Futhark" "Mathematica" "Chapel"])

(def system ["C" "C++" "Rust" "Swift" "Nim" "Zig" "Go" "D" "Ada"])