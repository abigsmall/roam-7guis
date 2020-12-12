(ns sevenguis.core
  (:require [reagent.core :as r]))

(defn app
  []
  [:div
   "Whaddup"])

(defn ^:export main
  []
  (r/render
    [app]
    (.getElementById js/document "app")))
