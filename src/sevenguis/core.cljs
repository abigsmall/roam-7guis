(ns sevenguis.core
  (:require [reagent.core :as r]
            [sevenguis.components.counter :refer [counter]]
            [sevenguis.components.temperature-converter :refer [temperature-converter]]
            [sevenguis.components.flight-booker :refer [flight-booker]]))
            ; [sevenguis.components.simplified-example :refer [parent]]))

(defn app
  []
  [:div
   [:h2 "Counter"]
   [counter]
   [:h2 "Temperature Converter"]
   [temperature-converter]
   [:h2 "Flight Booker"]
   [flight-booker]])
   ; [parent]])

(defn ^:export main
  []
  (r/render
    [app]
    (.getElementById js/document "app")))
