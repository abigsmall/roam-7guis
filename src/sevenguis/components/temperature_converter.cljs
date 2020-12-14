(ns sevenguis.components.temperature-converter
  (:require [reagent.core :as r]))

(defn convert-to-f
  [{source-unit :unit source-value :value}]
  (case source-unit
    "C" (+ (* source-value (/ 9 5)) 32)
    "F" source-value))

(defn convert-to-c
  [{source-unit :unit source-value :value}]
  (case source-unit
    "F" (* (- source-value 32) (/ 5 9))
    "C" source-value))

(defn temperature-converter
  []
  (let [temperature (r/atom {:unit "C" :value nil})]
    (fn
      []
      [:div
       [:label
        [:input
         {:value (when (:value @temperature) (convert-to-f @temperature))
          :on-change #(swap! temperature assoc :value (convert-to-c {:unit "F" :value (.. % -target -value)}))}]
        "Fahrenheit"]
       [:label
        [:input
         {:value (when (:value @temperature) (:value @temperature))
          :on-change #(swap! temperature assoc :value (.. % -target -value))}]
        "Celsius"]])))

;; well, how do we want to do this?
;; we gotta do some validation
;; let’s just dive in
;; we can make K be the canonical value
;; ok let’s just make C be the canonical
;; or F
