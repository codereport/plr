(ns plr.helper)

; figure out how to parameterize on data/sites and info/last-update

(defmacro read-rm       [] (slurp "./public/data/rm/2022-06.txt"))
(defmacro read-so       [] (slurp "./public/data/so/2022-06.txt"))
(defmacro read-pypl     [] (slurp "./public/data/pypl/2023-04.txt"))
(defmacro read-tiobe    [] (slurp "./public/data/tiobe/2023-03.txt"))
(defmacro read-ieee     [] (slurp "./public/data/ieee/2022-08.txt"))
(defmacro read-octo     [] (slurp "./public/data/octo/2022-11.txt"))
(defmacro read-languish [] (slurp "./public/data/languish/2023-01.txt"))
