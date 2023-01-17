(ns plr.info
  (:require
   [clojure.string :as str]
   [plr.data :as data]
   [plr.styles :as styles]
   [plr.imgs :as imgs]))

(def num-langs {:so "43" :octo "10" :rm "20" :languish "300+" :pypl "28" :ieee "57" :tiobe "50"})
(def freq {:so "Annual" :octo "Annual" :rm "Biannual" :languish "Monthly" :pypl "Monthly" :ieee "Annual" :tiobe "Monthly"})

(def problems {:octo     "Limitation of only showing \"top 10\" languages since 2014 in graphic."
               :ieee     "JavaScript seems low."
               :rm       "N/A"
               :languish "Every file format on GitHub i.e. JupyterNotebooks, Markdown."
               :so       "N/A"
               :pypl     "Only 28 selected languages, includes Ada & ABAP?"
               :tiobe    "The worst of them all. Two different Visual Basics in top 15, TypeScript ranked #35, RPG ranked #39?. Definitely ignore."})

(def source {:octo     "Github Pull Requests"
             :ieee     "Google Trends, Twitter, StackOverflow, IEEE Xplore Digital Library, IEEE Jobs Site, CareerBuilder, GitHub Stars, GitHub Pull Requests"
             :rm       "\"we extract language rankings from GitHub [by pull request] and Stack Overflow and combine them."
             :languish "GitHub Issues, GitHub Stars, StackOverflow Questions"
             :so       "Survey of 70,000+ developers"
             :pypl     "\"how often language tutorials are searched on Google ... raw data comes from Google Trends.\""
             :tiobe    "\"The ratings are based on the number of skilled engineers world-wide, courses and third party vendors. Popular search engines such as Google, Bing, Yahoo!, Wikipedia, Amazon, YouTube and Baidu are used to calculate the ratings.\""})

(defn row [lang]
  [:tr
   [:td {:style {:padding "16px 16px"}} [:img {:src (str/join ["public/media/site-logos/" (imgs/site-logos lang)]) :width "75x" :height "75px"}]]
   [:td styles/cell2 [:a {:href (get data/links lang)} [:label (data/names lang)]]]
   [:td styles/cell2 [:label (num-langs lang)]]
   [:td styles/cell2 [:label (freq lang)]]
   [:td styles/cell2 [:label (source lang)]]
   [:td styles/cell2 [:label (problems lang)]]])

(defn table [is-mobile?]
  [:table (styles/table is-mobile?)
   [:tr {:style {:font-weight "bold"}} [:td] [:td "Website"] [:td "#"] [:td "Frequency"] [:td "Source"] [:td "Problem"]]
   (map row data/langs)])