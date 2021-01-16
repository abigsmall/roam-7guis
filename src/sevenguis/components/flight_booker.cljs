(ns sevenguis.components.flight-booker
  (:require [reagent.core :as r]
            [reagent.ratom :as ratom]))

(def date-regex #"\d{4}-\d{2}-(\d{2})")

(defn validate-date
  [date]
  (let [[match-result captured-day] (re-matches date-regex date)]
    (if match-result
      (when (= (js/parseInt captured-day) (.getUTCDate (js/Date. match-result)))
        match-result)
      match-result)))

(defn flight-booker
  []
  (let [c (r/atom :one-way)
        t1 (r/atom {:value "2021-01-08"})
        t2 (r/atom {:value "2021-01-08"
                    :enabled (ratom/reaction (= :round @c))})]
    (fn
      []
      [:div
       [:select
        {:name "Flight type"
         :on-change (fn [e]
                      (let [selection (.. e -target -value)]
                        (reset! c (keyword selection))))}
        [:option
         {:value "one-way"}
         "one-way flight"]
        [:option
         {:value "return"}
         "return flight"]]
       [:input
        {:class (when-not (validate-date (:value @t1)) "invalid")
         :value (:value @t1)
         :on-change (fn [e]
                      (let [input (.. e -target -value)]
                        (swap! t1 assoc :value input)))}]
       [:input
        {:class (when-not (validate-date (:value @t2)) "invalid")
         :value (:value @t2)
         :on-change (fn [e]
                      (let [input (.. e -target -value)]
                        (swap! t2 assoc :value input)))}]
       [:button "Book"]])))
