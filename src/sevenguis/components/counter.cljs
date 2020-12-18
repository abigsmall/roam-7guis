(ns sevenguis.components.counter
  (:require [reagent.core :as r]))

(defn counter
  []
  (let [count (r/atom 0)]
    (fn
      []
      [:div
       [:h2 "Counter"]
       [:span @count]
       [:button
        {:on-click #(swap! count inc)}
        "Count 🧛‍♂️"]])))
