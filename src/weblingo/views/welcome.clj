(ns weblingo.views.welcome
  (:require [weblingo.views.common :as common])
  (:use [noir.core :only [defpage]]
        [weblingo.models.model]))

(defpage "/random-word" []
  (common/layout
    [:p (get-random-word 5)]))
