(ns weblingo.models.lingo)

(defn board-create-row [length & letters]
  (let [letters (take length (map #(hash-map :letter %) letters))]
    (let [fill (repeat (- length (count letters)) {})]
      (vec (concat letters fill)))))

(defn create-board [word rows]
  (let [length (count word)]
    (let [first (board-create-row length (first word))
          rest (repeat (- rows 1) (board-create-row length))]
      (apply conj [first] rest))))

(defn row-guessed? [row]
  (= (count (filter empty? row)) 0))

(defn board-create-evaluate-row [length guess word]
  (vec (take length 
             (for [i (range (count word))]
         {:letter (get guess i) 
          :correct (= (get guess i) (get word i)) 
          :contains (not (= -1 (.indexOf word (str (get guess i)))))}))))

(defn board-guess [board guess word]
  (let [rows (count board)
        length (count (first board))
        guessedrows (vec (filter row-guessed? board))
        newrow (board-create-evaluate-row length guess word)
        fillrows (repeat (- rows 1 (count guessedrows)) (board-create-row length))]
    (apply conj guessedrows newrow fillrows)))

(defn row-correct? [row]
  (= (count (filter #(:correct %1) row)) (count row)))

(defn word-guessed? [board]
  (> (count (filter row-correct? board)) 0))

(defn board-completed? [board]
  (= (count (filter #(not (row-guessed? %1)) board)) 0))

(defn game-ended? [board]
  (or (word-guessed? board) (board-completed? board)))
    

