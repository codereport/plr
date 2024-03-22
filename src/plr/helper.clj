(ns plr.helper)

; figure out how to parameterize on data/sites and info/last-update

; current
(defmacro rm       [] (slurp "./public/data/rm/2023-01.txt")) ; released on 2023-05
(defmacro so       [] (slurp "./public/data/so/2023-06.txt"))
(defmacro pypl     [] (slurp "./public/data/pypl/2024-03.txt"))
(defmacro tiobe    [] (slurp "./public/data/tiobe/2023-12.txt"))
(defmacro ieee     [] (slurp "./public/data/ieee/2023-08.txt"))
(defmacro octo     [] (slurp "./public/data/octo/2023-11.txt"))
(defmacro languish [] (slurp "./public/data/languish/2024-01.txt"))

; previous 3 month
(defmacro prev3-rm       [] (slurp "./public/data/rm/2022-06.txt")) ; released on 2022-10
(defmacro prev3-so       [] (slurp "./public/data/so/2022-06.txt"))
(defmacro prev3-pypl     [] (slurp "./public/data/pypl/2023-12.txt"))
(defmacro prev3-tiobe    [] (slurp "./public/data/tiobe/2023-09.txt"))
(defmacro prev3-ieee     [] (slurp "./public/data/ieee/2022-08.txt"))
(defmacro prev3-octo     [] (slurp "./public/data/octo/2022-11.txt"))
(defmacro prev3-languish [] (slurp "./public/data/languish/2023-10.txt"))

; previous 6 month
(defmacro prev6-rm       [] (slurp "./public/data/rm/2022-06.txt")) ; released on 2022-10
(defmacro prev6-so       [] (slurp "./public/data/so/2022-06.txt"))
(defmacro prev6-pypl     [] (slurp "./public/data/pypl/2023-09.txt"))
(defmacro prev6-tiobe    [] (slurp "./public/data/tiobe/2023-07.txt"))
(defmacro prev6-ieee     [] (slurp "./public/data/ieee/2022-08.txt"))
(defmacro prev6-octo     [] (slurp "./public/data/octo/2022-11.txt"))
(defmacro prev6-languish [] (slurp "./public/data/languish/2023-07.txt"))

; previous 12 month
(defmacro prev12-rm       [] (slurp "./public/data/rm/2022-06.txt")) ; released on 2022-10
(defmacro prev12-so       [] (slurp "./public/data/so/2022-06.txt"))
(defmacro prev12-pypl     [] (slurp "./public/data/pypl/2023-03.txt"))
(defmacro prev12-tiobe    [] (slurp "./public/data/tiobe/2023-01.txt"))
(defmacro prev12-ieee     [] (slurp "./public/data/ieee/2022-08.txt"))
(defmacro prev12-octo     [] (slurp "./public/data/octo/2022-11.txt"))
(defmacro prev12-languish [] (slurp "./public/data/languish/2023-01.txt"))