(ns plr.helper
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

;; Current month references for each site
(def current-dates
  {:rm       "2025-01"
   :so       "2025-07"
   :ieee     "2025-08"
   :octo     "2024-11"
   :languish "2025-07"
   :jb       "2024-12"})

(defn date-months-ago [date-str n]
  (let [[year month] (map #(Integer/parseInt %) (str/split date-str #"-"))
        total-months (+ (* year 12) (dec month))
        result-months (- total-months n)
        new-year (quot result-months 12)
        new-month (inc (rem result-months 12))
        result (format "%04d-%02d" new-year new-month)]
    result))

(defn find-closest-date [site date-str]
  (let [site-name (name site)
        path-template #(format "./public/data/%s/%s.txt" site-name %)]
    (loop [check-date date-str attempts 24]
      (let [path (path-template check-date)]
        (cond
          (.exists (io/file path)) check-date
          (zero? attempts) nil
          :else (recur (date-months-ago check-date 1) (dec attempts)))))))

(defn get-data-content [site-kw months-ago]
  (let [current (get current-dates site-kw)
        target-date (date-months-ago current months-ago)
        closest-date (find-closest-date site-kw target-date)]
    (if closest-date
      (slurp (format "./public/data/%s/%s.txt" (name site-kw) closest-date))
      (throw (ex-info (str "No data file found for " site-kw " around " target-date)
                    {:site site-kw :target-date target-date})))))

(defmacro get-data [site-kw months-ago] (get-data-content site-kw months-ago))

(defmacro get-all-sites [months-ago]
  (let [sites [:so :octo :rm :languish :jb :ieee]]
    (vec (map #(get-data-content % months-ago) sites))))
