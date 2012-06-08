(ns weblingo.views.game
  (:require [weblingo.views.common :as common]
            [weblingo.models.gamestore :as gamestore]
            [weblingo.models.lingo :as lingo])
  (:use [noir.core :only [defpage]]))

(defn game-status []
  (if (not (lingo/game-ended? (gamestore/get-board)))
    "Submit a word to proceed"
    (if (lingo/word-guessed? (gamestore/get-board))
      "You won!"
      (str "You lost. The word was: " (gamestore/get-word)))))
    

(defpage [:get "/"] []
  (common/game-layout (gamestore/get-board) (game-status)))

(defpage [:post "/"] {:keys [reset guess]}
  (do
    (if (= reset "1")
      (gamestore/reset-game!))
    (if (and (not (= nil guess))
             (not (= (clojure.string/trim guess) ""))
             (not (lingo/game-ended? (gamestore/get-board))))
      (gamestore/set-board 
        (lingo/board-guess (gamestore/get-board) 
                           guess 
                           (gamestore/get-word))))
    (common/game-layout (gamestore/get-board) (game-status))))