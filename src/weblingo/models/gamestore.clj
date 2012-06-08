(ns weblingo.models.gamestore
  (:require [weblingo.models.lingo :as lingo]
            [weblingo.models.words :as words]
            [noir.session :as session]))

(defn get-word []
  (session/get :lingoword))

(defn set-word [word]
  (session/put! :lingoword word))

(defn set-board [board]
  (session/put! :lingoboard board))

(defn get-board []
  (do
    (if (= nil (get-word))
      (set-word (words/get-random-word 5)))
    (if (= nil (session/get :lingoboard))
      (set-board (lingo/create-board (get-word) 5)))
    (session/get :lingoboard)))

(defn reset-game! []
  (do
    (session/remove! :lingoboard)
    (session/remove! :lingoword)))