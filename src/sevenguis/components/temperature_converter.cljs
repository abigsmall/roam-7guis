(ns sevenguis.components.temperature-converter
  (:require [reagent.core :as r]))

(defn c->f
  [c]
  (+ (* c (/ 9 5)) 32))

(defn f->c
  [f]
  (* (- f 32) (/ 5 9)))

(defn temperature-converter
  []
  (let [c (r/atom nil)
        f (r/atom nil)]
    (fn
      []
      [:div
       [:label
        [:input
         {:value @f
          :on-change (fn [e]
                       (let [input-val (.. e -target -value)]
                         (reset! f input-val)
                         (reset! c (f->c input-val))))}]
        "Fahrenheit"]
       [:label
        [:input
         {:value @c
          :on-change (fn [e]
                       (let [input-val (.. e -target -value)]
                         (reset! c input-val)
                         (reset! f (c->f input-val))))}]
        "Celsius"]])))

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
