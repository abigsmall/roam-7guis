(ns sevenguis.components.flight-booker
  (:require [reagent.core :as r]))

(defn flight-booker
  []
  [:div
   [:h2 "Flight Booker"]
   [:select
    {:name "Flight type"}
    [:option
     {:value "one-way"}
     "one-way flight"]
    [:option
     {:value "return"}
     "return flight"]]
   [:input
    {:value "new Date() JS interop"}]
   [:input
    {:value "new Date() JS interop"}]])
