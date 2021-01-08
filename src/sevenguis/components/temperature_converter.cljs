(ns sevenguis.components.temperature-converter
  (:require [reagent.core :as r]))

(defn c->f
  [c]
  (+ (* c (/ 9 5)) 32))

(defn f->c
  [f]
  (* (- f 32) (/ 5 9)))

(defn valid-number?
  [input]
  (let [number (js/Number input)
        parsed-float (js/parseFloat input)
        valid (and (not (js/isNaN number)) (not (js/isNaN parsed-float)))]
    valid))

(defn empty-input?
  [input]
  (or (nil? input) (= "" input)))

(defn temperature-converter
  []
  (let [c (r/atom nil)
        f (r/atom nil)]
    (fn
      []
      [:div
       [:h2 "Temperature Converter"]
       [:label
        [:input
         {:class (cond
                   (empty-input? @f) nil
                   (not (valid-number? @f)) "invalid"
                   (or (not (valid-number? @c)) (empty-input? @c)) "decoupled")
          :value @f
          :on-change (fn [e]
                       (let [input (.. e -target -value)]
                         (reset! f input)
                         (when (valid-number? input)
                           (reset! c (f->c input)))))}]
        "Fahrenheit"]
       [:label
        [:input
         {:class (cond
                   (empty-input? @c) nil
                   (not (valid-number? @c)) "invalid"
                   (or (not (valid-number? @f)) (empty-input? @f)) "decoupled")
          :value @c
          :on-change (fn [e]
                       (let [input (.. e -target -value)]
                         (reset! c input)
                         (when (valid-number? input)
                           (reset! f (c->f input)))))}]
        "Celsius"]])))
