(ns weblingo.models.model
  (:require [noir.session :as session])
  (:use [clojure.xml]))

(defn board-create-row [length & letters]
  (let [letters (take length (map #(hash-map :letter %) letters))]
    (let [fill (repeat (- length (count letters)) {})]
      (vec (concat letters fill)))))

(defn create-board [word rows]
  (let [length (count word)]
    (let [first (board-create-row length (first word))
          rest (repeat (- rows 1) (board-create-row length))]
      (apply conj [first] rest))))

(defn get-words [nletters]
  (for [x (xml-seq (clojure.xml/parse "resources/words.xml"))
        :when (and (= (:tag x) :word) 
                   (= (get-in x [:attrs :letters]) (str nletters)))]
    (first (:content x))))
  

(defn get-random-word [nletters]
  (rand-nth (get-words nletters)))

(defn get-board []
  (do
    (if (= nil (session/get :lingoboard))
      (session/put! :lingoboard (create-board (get-random-word 5) 5)))
    (session/get :lingoboard)))

(defn reset-board! []
  (session/remove! :lingoboard))

(defn guessed? [row]
  (= (count (filter empty? row)) 0))

(defn board-guess [board word]
  (let [rows (count board)
        length (count (first board))
        guessedrows (filter guessed? board)
        newrow (apply board-create-row length (seq word))
        fillrows (repeat (- rows 1 (count guessedrows)) (board-create-row length))]
    (reverse (apply conj guessedrows newrow fillrows))))
  

(defn guess! [word]
  (session/put! :lingoboard (board-guess (get-board) word)))
    

