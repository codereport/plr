(ns plr.info
  (:require
   [clojure.string :as str]
   [plr.data :as data]
   [plr.styles :as styles]
   [plr.imgs :as imgs]))

(def num-langs   {:so "52"      :octo "10"      :rm "20"       :languish "497"       :pypl "29"      :ieee "57"      :tiobe "50"})
(def freq        {:so "Annual"  :octo "Annual"  :rm "Biannual" :languish "Quarterly" :pypl "Monthly" :ieee "Annual"  :tiobe "Monthly"})
(def last-update {:so "2023-06" :octo "2022-11" :rm "2023-01"  :languish "2023-08"   :pypl "2023-09" :ieee "2022-08" :tiobe "2023-09"})

(def problems {:octo     "Limitation of only showing \"top 10\" languages since 2014 in graphic."
               :ieee     "JavaScript seems low."
               :rm       "N/A"
               :languish "Every file format on GitHub i.e. JupyterNotebooks, Markdown."
               :so       "N/A"
               :pypl     "Only 28 selected languages, includes Ada & ABAP?"
               :tiobe    "The worst of them all. Two different Visual Basics in top 15, TypeScript ranked #35, RPG ranked #39?. Definitely ignore."})

(defn site-logo [site padding size]
  [:div {:style {:padding (str/join [(str padding) "px " (str padding) "px"]) :display "inline"}}
   [:img {:src (str/join ["public/media/site-logos/" (imgs/site-logos site)])
          :width  (str/join [(str size) "px"])
          :height (str/join [(str size) "px"])}]])

(defn icon [site] (site-logo site 0 22))

(def source {:octo     [:div (icon :octo) " Requests"]
             :ieee     [:div (icon :google) " Trends, " (icon :twitter) ", " (icon :so) ", " (icon :ieee) " Xplore Digital Library, " (icon :ieee) " Jobs Site, CareerBuilder, " (icon :octo) " Stars, " (icon :octo) " Pull Requests"]
             :rm       [:div (icon :octo) " Pull Requests, " (icon :so) " Questions"]
             :languish [:div (icon :octo) " Issues, " (icon :octo) " Stars, " (icon :so) " Questions"]
             :so       [:div (icon :so) " Survey of 70,000+ developers"]
             :pypl     [:div (icon :google) " Trends"]
             :tiobe    "\"The ratings are based on the number of skilled engineers world-wide, courses and third party vendors. Popular search engines such as Google, Bing, Yahoo!, Wikipedia, Amazon, YouTube and Baidu are used to calculate the ratings.\""})

(defn row [site]
  [:tr
   [:td (site-logo site 16 75)]
   [:td styles/cell2 [:a {:href (get data/links site)} [:label (data/names site)]]]
   [:td styles/cell2 [:label (num-langs site)]]
   [:td styles/cell2 [:label (freq site)]]
   [:td styles/cell2 [:label (last-update site)]]
   [:td styles/cell2 [:label (source site)]]
   [:td styles/cell2 [:label (problems site)]]])

(defn table [is-mobile?]
  [:table (styles/table is-mobile?)
   [:tr {:style {:font-weight "bold"}} [:td] [:td "Website"] [:td "#"] [:td "Frequency"] [:td "Last"] [:td "Source"] [:td "Problem"]]
   (map row data/sites)])