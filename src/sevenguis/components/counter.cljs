(ns sevenguis.components.counter
  (:require [reagent.core :as r]))

(defn counter
  []
  (let [count (r/atom 0)]
    (fn
      []
      [:div @count
       [:div]
       [:button
        {:on-click #(swap! count inc)}
        "Count 🧛‍♂️"]])))
