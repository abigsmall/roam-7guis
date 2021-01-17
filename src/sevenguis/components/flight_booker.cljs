(ns sevenguis.components.flight-booker
  (:require [reagent.core :as r]
            [reagent.ratom :as ratom]
            [clojure.string :as string]))

(defn zeropad
  "not an actual full-fledged zeropad helper
   is only good for padding string of length 1 to 2"
  [string]
  (if (= (count string) 1)
    (str "0" string)
    string))

(def today-str (let [today (js/Date.)]
                 (string/join
                  "-"
                  [(.getFullYear today)
                   (zeropad (str (+ (.getMonth today) 1)))
                   (zeropad (str (.getDate today)))])))

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
        t1 (r/atom {:value today-str})
        t2 (r/atom {:value today-str
                    :enabled (ratom/reaction (= :return @c))})]
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
         :disabled (not @(:enabled @t2))
         :value (:value @t2)
         :on-change (fn [e]
                      (let [input (.. e -target -value)]
                        (swap! t2 assoc :value input)))}]
       [:button
        {:disabled (cond
                     (nil? (validate-date (:value @t1))) true
                     (and @(:enabled @t2) (nil? (validate-date (:value @t2)))) true
                     (and @(:enabled @t2) (> (compare (:value @t1) (:value @t2)) 0)) true
                     :else false)}
        "Book"]])))
