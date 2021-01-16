(ns sevenguis.components.counter
  (:require [reagent.core :as r]))

(defn counter
  []
  (let [count (r/atom 0)]
    (fn
      []
      [:div
       [:span @count]
       [:button
        {:on-click #(swap! count inc)}
        "Count 🧛‍♂️"]])))
