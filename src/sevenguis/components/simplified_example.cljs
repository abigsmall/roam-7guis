(ns sevenguis.components.simplified-example
  (:require [reagent.core :as r]))

(defn input-wrapper
  [{:keys [label change-handler]}]
  (let [valid (r/atom true)]
    (fn
      [{:keys [value]}]
      [:label
       label
       [:input
        {:class (when-not @valid "invalid")
         :value value
         :on-change (fn [e]
                      (let [input (.. e -target -value)
                            parsed-float (js/parseFloat input)]
                        (.log js/console "validated-input: on-change: input:" input)
                        (.log js/console "validated-input: on-change: value:" value)
                        (change-handler parsed-float)))}]])))

(defn parent
  []
  (let [parent-ratom (r/atom nil)]
    (fn
      []
      [:div
       [:h2 "Simplified Example"]
       [input-wrapper
        {:label "What a simple input"
         :value @parent-ratom
         :change-handler #(reset! parent-ratom %)}]])))
