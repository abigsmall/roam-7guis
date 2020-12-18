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
        parsed-float (js/parseFloat input)]
    (.log js/console "validate: input:" input)
    (.log js/console "validate: parsed-float:" parsed-float)
    (.log js/console "validate: (js/isNaN number):" (js/isNaN number))
    (.log js/console "validate: (js/isNaN parsed-float):" (js/isNaN parsed-float))
    (.log js/console "validate: (and (not (js/isNaN number)) (not (js/isNaN parsed-float))):" (and (not (js/isNaN number)) (not (js/isNaN parsed-float))))
    {:input input
     :valid (if (and (not (js/isNaN number)) (not (js/isNaN parsed-float))) ;; maybe i can make this a let for readability
              true
              false)}))

(defn validated-input
  []
  (let [valid (r/atom true)]
    (fn
      [{:keys [label value change-handler]}]
      [:label
       [:input
        {:class (when-not @valid "invalid")
         :value value
         :on-change (fn [e]
                      (let [{valid? :valid input :input} (validate (.. e -target -value))]
                        (.log js/console "validated-input: on-change: valid?:" valid?)
                        (.log js/console "validated-input: on-change: input:" input)
                        (.log js/console "validated-input: on-change: @valid:" @valid)
                        (when (not= valid? @valid) (reset! valid valid?))
                        (change-handler input valid?)))}]

       label])))

(defn temperature-converter
  []
  (let [c (r/atom nil)
        f (r/atom nil)]
    (fn
      []
      [:div
       [:h2 "Temperature Converter"]
       [validated-input
        {:label "Fahrenheit"
         :value ((fn [v] (.log js/console "temperature-converter value prop to validated-input:" v) v) @f)
         :change-handler (fn [input valid?]
                           (reset! f input)
                           (cond
                             (= input "") (reset! c nil)
                             valid? (reset! c (f->c input))
                             :else (reset! c nil)))}]

       [validated-input
        {:label "Celsius"
         :value @c
         :change-handler (fn [input]
                           (reset! c input)
                           (reset! f (c->f input)))}]])))
