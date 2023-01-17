(ns plr.data)

(def langs [:so :octo :rm :languish :pypl :ieee :tiobe])

(def names {:so "StackOverflow" :octo "Octoverse" :rm "RedMonk" :languish "Languish"
            :pypl "PYPL" :ieee "IEEE Spectrum" :tiobe "TIOBE"})

(def links {:octo     "https://octoverse.github.com/2022/top-programming-languages"
            :ieee     "https://spectrum.ieee.org/top-programming-languages-2022"
            :rm       "https://redmonk.com/sogrady/2022/10/20/language-rankings-6-22/"
            :languish "https://tjpalmer.github.io/languish/"
            :so       "https://survey.stackoverflow.co/2022/#most-popular-technologies-language"
            :pypl     "https://pypl.github.io/PYPL.html"
            :tiobe    "https://www.tiobe.com/tiobe-index/"})

(def octoverse ["JavaScript" "Python" "Java" "TypeScript" "C#" "C++" "PHP" "Shell" "C" "Ruby"])
(def ieee ["Python" "C" "C++" "C#" "Java" "SQL" "JavaScript" "R" "HTML" "TypeScript" "Go" "PHP" "Shell" "Ruby" "Scala" "MATLAB" "SAS" "Assembly" "Kotlin" "Rust" "Perl" "Objective-C" "Dart" "Swift" "Verilog" "Arduino" "D" "Julia" "CUDA" "VHDL" "Visual Basic" "LabView" "Groovy" "Lua" "Ada" "Scheme" "ABAP" "Haskell" "COBOL" "Elixir" "F#" "Lisp" "Delphi" "Fortran" "TCL" "Clojure" "Prolog" "OCaml" "Ladder Logic" "Erlang" "J" "Forth" "Elm" "Raku" "WebAssembly" "CoffeScript" "Eiffel"])
(def redmonk ["JavaScript" "Python" "Java" "PHP" "C#" "CSS" "C++" "TypeScript" "Ruby" "C" "Swift" "R" "Objective-C" "Shell" "Scala" "Go" "PowerShell" "Kotlin" "Rust" "Dart"])
(def languish ["Python" "JavaScript" "TypeScript" "Java" "C++" "C#" "Go" "HTML" "Markdown" "C" "PHP" "Rust" "CSS" "Shell" "Kotlin" "Jupyter Notebook" "R" "Dart" "Swift" "Vue" "SQL" "Ruby" "JSON" "Lua" "Dockerfile" "PowerShell"])
(def stack-overflow ["JavaScript" "HTML" "SQL" "Python" "TypeScript" "Java" "Shell" "C#" "C++" "PHP" "C" "PowerShell" "Go" "Rust" "Kotlin" "Dart" "Ruby" "Assembly" "Swift" "R" "VBA" "MATLAB" "Lua" "Groovy" "Delphi" "Scala" "Objective-C" "Perl" "Haskell" "Elixir" "Julia" "Clojure" "Solidity" "Lisp" "F#" "Fortran" "Erlang" "APL" "COBOL" "SAS" "OCaml" "Crystal"])
(def pypl ["Python" "Java" "JavaScript" "C#" "C++" "PHP" "R" "TypeScript" "Swift" "Objective-C" "Go" "Rust" "Kotlin" "MATLAB" "Ruby" "VBA" "Ada" "Dart" "Scala" "Visual Basic" "Lua" "ABAP" "Haskell" "Julia" "Groovy" "COBOL" "Perl" "Delphi"])
(def tiobe ["Python" "C" "C++" "Java" "C#" "Visual Basic" "JavaScript" "SQL" "Assembly" "PHP" "Swift" "Go" "R" "Classic Visual Basic" "MATLAB" "Ruby" "Delphi" "Rust" "Perl" "Scratch" "FoxPro" "SAS" "Objective-C" "Lua" "Kotlin" "Ada" "Fortran" "Lisp" "Julia" "Transact-SQL" "COBOL" "Scala" "F#" "Logo" "TypeScript" "Groovy" "Shell" "Dart" "RPG" "PL/SQL" "PowerShell" "Awk" "Prolog" "CFML" "Haskell" "D" "LabView" "Scheme" "ABAP" "OCaml"])

(def extras [[[2 "CSS"]] ; StackOverflow
             [] ; Octoverse
             [] ; Redmonk
             ; Languish
             [[29 "VBA"]
              [30 "Scala"]
              [32 "Objective-C"]
              [36 "Solidity"]
              [39 "Assembly"]
              [51 "MATLAB"]
              [54 "Groovy"]
              [66 "CUDA"]
              [67 "Delphi"]
              [72 "Arduino"]
              [75 "Verilog"]
              [112 "Crystal"]
              [115 "VHDL"]
              [138 "D"]
              [145 "ABAP"]
              [166 "Ada"]
              [185 "LabView"]
              [212 "COBOL"]
              [249 "APL"]
              [261 "Visual Basic"]
              [301 "J"]]
             [[5 "C"]] ; PYPL
             [] ; IEEE Spectrum
             [] ; TIOBE
             ])

(def odd ["Markdown" "CSS" "HTML" "SAS" "Jupyter Notebook" "Vue" "JSON" "Dockerfile" "SQL"])
