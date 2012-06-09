(ns weblingo.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-css html5]]
        [hiccup.core :only [html]]))

(defpartial layout [& content]
            (html5
              [:head
               [:title "Weblingo Created By Asim & Johan"]
               (include-css "/css/LingoStyle.css")]
              [:body
               [:div#wrapper
                content]]))

(defn board-table-row-cell-tag [cell]
  (keyword (str "td" 
       (if (:correct cell) ".correct")
       (if (:contains cell) ".contains"))))

(defn board-table-row-cell [cell]
  (let [htmlcell [(board-table-row-cell-tag cell)]]
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
  (let [table [:table {:class "lingoBoard"}]]
    (apply conj table (board-table-rows board))))

(defpartial game-layout [board status]
  (layout    
    [:div#container
     [:div#header ]
     [:div#content  
	    [:form {:method "post"}
	      [:label#lblTxt {:for "wordguess"} "Guess A Five-Letter Word "]
	      [:p]
	      [:input#wordguess {:type "text" :name "guess"}]
	      [:input#btnGuess {:type "submit" :value "Guess"}]]
	    [:form {:method "post"}
	      [:input {:type "hidden" :name "reset" :value "1"}]
	      [:input#btnReset {:type "submit" :value "Play Again"}]]
    [:div#game (board-table board)]
    [:p{:class "resultMessage"} status]
    ]
    [:div#footer]
    ]
   )
)
