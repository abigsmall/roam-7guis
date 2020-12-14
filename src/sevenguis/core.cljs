(ns sevenguis.core
  (:require [reagent.core :as r]
            [sevenguis.components.counter :refer [counter]]))

(defn app
  []
  [:div
   [counter]])

(defn ^:export main
  []
  (r/render
    [app]
    (.getElementById js/document "app")))
