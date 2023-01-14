(ns plr.data)

; https://octoverse.github.com/2022/top-programming-languages
(def octoverse ["JavaScript" "Python" "Java" "TypeScript" "C#" "C++" "PHP" "Shell" "C" "Ruby"])

; https://spectrum.ieee.org/top-programming-languages-2022 2022
(def ieee ["Python" "C" "C++" "C#" "Java" "SQL" "JavaScript" "R" "HTML" "TypeScript" "Go" "PHP" "Shell" "Ruby" "Scala" "MATLAB" "SAS" "Assembly" "Kotlin" "Rust"])

; https://redmonk.com/sogrady/2022/10/20/language-rankings-6-22/ June 2022
(def redmonk ["JavaScript" "Python" "Java" "PHP" "C#" "CSS" "C++" "TypeScript" "Ruby" "C" "Swift" "R" "Objective-C" "Shell" "Scala" "Go" "PowerShell" "Kotlin" "Rust" "Dart"])

; https://tjpalmer.github.io/languish/ 2022 Q4 (Dec)
(def languish ["Python" "JavaScript" "TypeScript" "Java" "C++" "C#" "Go" "HTML" "Markdown" "C" "PHP" "Rust" "CSS" "Shell" "Kotlin" "Jupyter Notebook" "R" "Dart" "Swift" "Vue" "SQL" "Ruby" "JSON" "Lua" "Dockerfile" "PowerShell"])

; https://survey.stackoverflow.co/2022/#most-popular-technologies-language 2022
(def stack-overflow ["JavaScript" "HTML" "SQL" "Python" "TypeScript" "Java" "Shell" "C#" "C++" "PHP" "C" "PowerShell" "Go" "Rust" "Kotlin" "Dart" "Ruby" "Assembly" "Swift" "R" "VBA" "MATLAB" "Lua" "Groovy" "Delphi" "Scala" "Objective-C" "Perl"])

; https://pypl.github.io/PYPL.html Jan 2023
(def pypl ["Python" "Java" "JavaScript" "C#" "C++" "PHP" "R" "TypeScript" "Swift" "Objective-C" "Go" "Rust" "Kotlin" "MATLAB" "Ruby" "VBA" "Ada" "Dart" "Scala" "Visual Basic"])

; https://www.tiobe.com/tiobe-index/ Jan 2023
(def tiobe ["Python" "C" "C++" "Java" "C#" "Visual Basic" "JavaScript" "SQL" "Assembly" "PHP" "Swift" "Go" "R" "Classic Visual Basic" "MATLAB" "Ruby" "Delphi" "Rust" "Perl" "Scratch"])

(def extras [; Languish
             [29 "VBA"]
             [32 "Objective-C"]
             [39 "Assembly"]
             [166 "Ada"]
             [261 "Visual Basic"]])

(def odd ["Markdown" "CSS" "HTML" "SAS" "Jupyter Notebook" "Vue" "JSON" "Dockerfile" "SQL"])
