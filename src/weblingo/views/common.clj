(ns weblingo.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-css html5]]
        [hiccup.core :only [html]]))

(defpartial layout [& content]
            (html5
              [:head
               [:title "weblingo"]]
              [:body
               [:div#wrapper
                [:h1 "weblingo"]
                content]]))

(defn board-table-row-cell [cell]
  (let [htmlcell [:td]]
    (if (contains? cell :letter)
      (conj htmlcell (:letter cell))
      (conj htmlcell "-"))))

(defn board-table-row-cells [row]
  (vec (for [cell row] (board-table-row-cell cell))))

(defn board-table-row [row]
  (let [htmlrow [:tr]]
    (apply conj htmlrow (board-table-row-cells row))))

(defn board-table-rows [board]
  (vec (for [row board] (board-table-row row))))

(defn board-table [board]
  (let [table [:table.lingoBoard]]
    (apply conj table (board-table-rows board))))

(defpartial game-layout [board]
  (layout 
    [:form {:method "post"}
     [:label {:for "wordguess"} "guess a word"]
     [:input#wordguess {:type "text" :name "guess"}]
     [:input {:type "submit" :value "guess"}]]
    [:form {:method "post"}
     [:input {:type "hidden" :name "reset" :value "1"}]
     [:input {:type "submit" :value "reset"}]]
    [:div#game (board-table board)]))
