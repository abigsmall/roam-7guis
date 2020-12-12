(ns sevenguis.core
  (:require [reagent.core :as r]))

(defn app
  []
  [:div
   [:p "Whaddup!"]])

(defn ^:export main
  []
  (r/render
    [app]
    (.getElementById js/document "app")))
