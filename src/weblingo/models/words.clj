(ns weblingo.models.words
  (:use [clojure.xml]))

(defn get-words [nletters]
  (for [x (xml-seq (clojure.xml/parse "resources/words.xml"))
        :when (and (= (:tag x) :word) 
                   (= (get-in x [:attrs :letters]) (str nletters)))]
    (first (:content x))))
  

(defn get-random-word [nletters]
  (rand-nth (get-words nletters)))