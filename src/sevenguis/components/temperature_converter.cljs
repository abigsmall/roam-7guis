(ns sevenguis.components.temperature-converter
  (:require [reagent.core :as r]))

(defn c->f
  [c]
  (+ (* c (/ 9 5)) 32))

(defn f->c
  [f]
  (* (- f 32) (/ 5 9)))

(defn validate-old
  [input]
  (let [number (js/Number input)
        parsed-float (js/parseFloat input)
        valid (and (not (js/isNaN number)) (not (js/isNaN parsed-float)))]
    (.log js/console "validate: input:" input)
    (.log js/console "validate: (= input \"\"):" (= input ""))
    (.log js/console "validate: parsed-float:" parsed-float)
    (.log js/console "validate: valid:" valid)
    {:input input
     :valid valid}))

(defn validate
  [input]
  (let [number (js/Number input)
        parsed-float (js/parseFloat input)
        valid (and (not (js/isNaN number)) (not (js/isNaN parsed-float)))]
    (condp = input
      nil true
      "" true
      valid)))

; (defn validated-input
;   [{:keys [label change-handler]}]
;   (let [valid (r/atom true)]
;     (fn
;       [{:keys [value]}]
;       [:label
;        [:input
;         {:class (when-not @valid "invalid")
;          :value value
;          :on-change (fn [e]
;                       (let [{valid? :valid input :input} (validate (.. e -target -value))]
;                         ; (.log js/console "validated-input: on-change: valid?:" valid?)
;                         ; (.log js/console "validated-input: on-change: input:" input)
;                         ; (.log js/console "validated-input: on-change: @valid:" @valid)
;                         (when (not= valid? @valid) (reset! valid valid?))
;                         (change-handler input valid?)))}]
;        label])))

; (defn temperature-converter
;   []
;   (let [c (r/atom nil)
;         f (r/atom nil)]
;     (fn
;       []
;       [:div
;        [:h2 "Temperature Converter"]
;        [validated-input
;         {:label "Fahrenheit"
;          :value @f
;          :change-handler (fn [input valid?]
;                            (reset! f input)
;                            (cond
;                              (= input "") (reset! c nil)
;                              valid? (reset! c (f->c input))
;                              :else (reset! c nil)))}]
;
;        [validated-input
;         {:label "Celsius"
;          :value @c
;          :change-handler (fn [input]
;                            (reset! c input)
;                            (reset! f (c->f input)))}]])))
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
                           (reset! f (c->f input)))))}]]])))
