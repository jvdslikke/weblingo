(ns weblingo.views.welcome
  (:require [weblingo.views.common :as common]
            [weblingo.models.model :as model])
  (:use [noir.core :only [defpage]]))

(defpage "/random-word" []
  (common/layout
    [:p (model/get-random-word 5)]))

(defpage [:get "/"] []
  (common/game-layout (model/get-board)));

(defpage [:post "/"] {:keys [reset guess]}
  (do 
    (if (= reset "1")
      (model/reset-game!))
    (if (not (= nil guess))
      (common/game-layout (model/guess! guess)))
    (common/game-layout (model/get-board))))