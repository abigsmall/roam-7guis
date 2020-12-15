(ns sevenguis.components.temperature-converter
  (:require [reagent.core :as r]))

(defn c->f
  [c]
  (+ (* c (/ 9 5)) 32))

(defn f->c
  [f]
  (* (- f 32) (/ 5 9)))

;; we want to know when something is not a number
;; let’s play around with numbers
(defn validate-input
  [input]
  (let [parse-floated (js/parseFloat input)
        is-nan (js/isNaN parse-floated)]
    {:input input
     :valid is-nan}))

(defn input-validator
  [{:keys [label value change-handler]}]
  (let [valid (r/atom true)]
    (fn
      []
      [:label
       [:input
        {:class (when (not @valid) "invalid")
         :value value
         :on-change (fn [e]
                      (let [{valid? :valid input :input} (validate-input (.. e -target -value))]
                        (.log js/console "valid?:" valid?)
                        (.log js/console "input:" input)
                        (.log js/console "value-from-inside-input-validator:" value)
                        (when (not= valid? @valid) (reset! valid valid?))
                        (change-handler input)))}]

       label])))

(defn temperature-converter
  []
  (let [c (r/atom nil)
        f (r/atom nil)]
    (fn
      []
      [:div
       [:h2 "Temperature Converter"]
       [:p "“value” (really @f) from temperature-converter:" @f]
       [input-validator
        {:label "Fahrenheit1"
         :value @f
         :change-handler (fn [input]
                           (reset! f input)
                           (reset! c (f->c input)))}]])))
       ; [:label
       ;  [:input
       ;   {:value @f
       ;    :on-change (fn [e]
       ;                 (let [input-val (validate-input (.. e -target -value))]
       ;                   (reset! f input-val)
       ;                   (reset! c (f->c input-val))))}]
       ;  "Fahrenheit"]
       ; [:label
       ;  [:input
       ;   {:value @c
       ;    :on-change (fn [e]
       ;                 (let [input-val (.. e -target -value)]
       ;                   (reset! c input-val)
       ;                   (reset! f (c->f input-val))))}]
       ;  "Celsius"]])))

;; well, how do we want to do this?
;; we gotta do some validation
;; let’s just dive in
;; we can make K be the canonical value
;; ok let’s just make C be the canonical
;; or F

;; okay, some combination of on-blur and a toggle for when we’re actively editing and then also checking when the value has changed
;; okay we may not need the change in value part, or, i mean we could use the on-change callback as a place for this.
;; on-blur will help with setting the toggle back.
;; we should set the toggle on in the on-change

;; wait, i don’t even need the active, inactive check

;; need to do input validation
