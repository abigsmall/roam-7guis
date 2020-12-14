(ns sevenguis.core
  (:require [reagent.core :as r]
            [sevenguis.components.counter :refer [counter]]
            [sevenguis.components.temperature-converter :refer [temperature-converter]]))

(defn app
  []
  [:div
   [counter]
   [temperature-converter]])

(defn ^:export main
  []
  (r/render
    [app]
    (.getElementById js/document "app")))
