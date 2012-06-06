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
    (if (= nil (session/get :lingoword))
      (session/put! :lingoword (get-random-word 5)))
    (if (= nil (session/get :lingoboard))
      (session/put! :lingoboard (create-board (session/get :lingoword) 5)))
    (session/get :lingoboard)))

(defn reset-game! []
  (do
    (session/remove! :lingoboard)
    (session/remove! :lingoword)))

(defn guessed? [row]
  (= (count (filter empty? row)) 0))

(defn board-create-evaluate-row [length word]
  (vec (take length 
             (for [i (range (count word))]
         {:letter (get word i) 
          :correct (= (get (session/get :lingoword) i) (get word i)) 
          :contains (contains? (session/get :lingoword) (get word i))}))))

(defn board-guess [board word]
  (let [rows (count board)
        length (count (first board))
        guessedrows (filter guessed? board)
        newrow (board-create-evaluate-row length word)
        fillrows (repeat (- rows 1 (count guessedrows)) (board-create-row length))]
    (reverse (apply conj guessedrows newrow fillrows))))

(defn guess! [word]
  (session/put! :lingoboard (board-guess (get-board) word)))
    

