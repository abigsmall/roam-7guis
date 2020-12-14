(ns sevenguis.core
  (:require [reagent.core :as r]
            [sevenguis.components.counter :refer [counter]]
            [sevenguis.components.temperature-converter :refer [temperature-converter]]
            [sevenguis.components.flight-booker :refer [flight-booker]]))

(defn app
  []
  [:div
   [counter]
   [temperature-converter]
   [flight-booker]])

(defn ^:export main
  []
  (r/render
    [app]
    (.getElementById js/document "app")))
