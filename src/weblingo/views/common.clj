(ns weblingo.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-css html5]]))

(defpartial layout [& content]
            (html5
              [:head
               [:title "weblingo"]]
              [:body
               [:div#wrapper
                [:h1 "weblingo"]
                content]]))
