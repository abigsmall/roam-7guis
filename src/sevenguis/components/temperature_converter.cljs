(ns sevenguis.components.temperature-converter
  (:require [reagent.core :as r]))

(defn c->f
  [c]
  (+ (* c (/ 9 5)) 32))

(defn f->c
  [f]
  (* (- f 32) (/ 5 9)))

(defn validate
  [input]
  (let [number (js/Number input)
        parsed-float (js/parseFloat input)
        valid (and (not (js/isNaN number)) (not (js/isNaN parsed-float)))]
    (condp = input
      nil true
      "" true
      valid)))

(defn empty-input?
  [input]
  (cond
    (nil? input) true
    (= input "") true
    :else false))

(defn temperature-converter
  []
  (let [c (r/atom nil)
        f (r/atom nil)]
    (fn                 ;; does this have to be a function? can’t it be just a hiccup element?
      []
      [:div
       [:h2 "Temperature Converter2"]
       [:label
        [:input
         {:class (cond
                   (not (validate @f)) "invalid"
                   (empty-input? @f) nil
                   (not (validate @c)) "decoupled")
                   ; (or (not (validate @c)) (empty-input? @c)) "decoupled" ;; or c has empty input

          :value @f
          :on-change (fn [e]
                       (let [input (.. e -target -value)]
                         (reset! f input)
                         (when (validate @f)
                           (reset! c (f->c input)))))}]

        "Fahrenheit"]
       [:label
        [:input
         {:class (cond
                   (not (validate @c)) "invalid"
                   (empty-input? @c) nil
                   (not (validate @f)) "decoupled")
          :value @c
          :on-change (fn [e]
                       (let [input (.. e -target -value)]
                         (reset! c input)
                         (when (validate @c)
                           (reset! f (c->f input)))))}]
        "Celsius"]])))
