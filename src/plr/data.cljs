(ns plr.data)

(def sites [:so :octo :rm :languish :jb :pypl :ieee :tiobe :githut])

(def names {:so "StackOverflow" :octo "Octoverse" :rm "RedMonk" :languish "Languish" :jb "JetBrains"
            :pypl "PYPL" :ieee "IEEE Spectrum" :tiobe "TIOBE" :githut "GitHut 2.0"})

(def links {:octo     "https://github.blog/news-insights/octoverse/octoverse-2024/#the-most-popular-programming-languages"
            :ieee     "https://spectrum.ieee.org/top-programming-languages-2024"
            :rm       "https://redmonk.com/sogrady/2024/09/12/language-rankings-6-24/"
            :languish "https://tjpalmer.github.io/languish/"
            :jb       "https://www.jetbrains.com/lp/devecosystem-2024/"
            :so       "https://survey.stackoverflow.co/2024/technology/"
            :pypl     "https://pypl.github.io/PYPL.html"
            :tiobe    "https://www.tiobe.com/tiobe-index/"
            :githut   "https://madnight.github.io/githut/"})

; Lisp is included here because SO groups every Lisp into one which collides with Languish having all the Lisp dialects
(def odd ["Markdown" "CSS" "HTML" "SAS" "Jupyter Notebook" "Vue" "JSON" "Dockerfile" "SQL" "PLpgSQL" "ASP.NET" "Sass/SCSS"
          "XML" "TeX" "Batchfile" "Nginx" "Makefile" "CSV" "CMake" "Adblock Filter List" "Jinja" "YAML" "ApacheConf"
          "Svelte" "Lisp" "GraphQL"])

(def functional ["Haskell" "Scala" "F#" "Lisp" "Scheme" "Racket" "Erlang" "Elixir" "ML" "OCaml" "Elm" "Clojure" "Standard ML"
                 "Reason" "Emacs Lisp" "Common Lisp" "NewLisp" "PicoLisp" "PureScript" "Futhark" "LFE" "jq" "Gleam" "Roc"
                 "ReScript"])

(def arrays ["APL" "J" "q" "BQN" "Julia" "Fortran" "MATLAB" "R" "Futhark" "Mathematica" "Chapel"])

(def system ["C" "C++" "Rust" "Swift" "Nim" "Zig" "Go" "D" "Ada", "Pascal"])