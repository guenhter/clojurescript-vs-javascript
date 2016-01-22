(ns clojurescript-sandbox.core
  (:require [reagent.core :as reagent :refer [atom]]))

(def global-counter (reagent/atom 0.0))

;; Ticker
(defn timer-component []
  (let [counter (reagent/atom 0)]
    (fn []
      (js/setTimeout #(do (swap! counter inc)
                           (swap! global-counter + 0.2)) 100)
      [:div
       [:div "Seconds Elapsed: " (int (/ @counter 10))]
       [:div "Global Counter: " (int @global-counter)]])))


;; Set input on label
(defn atom-input [value]
  [:input {:type "text"
           :value @value
           :on-change #(reset! value (-> % .-target .-value))}])

(defn shared-state []
  (let [val (reagent/atom "foo")]
    (fn []
      [:div
       [:p "The value is now: " @val]
       [:p "Change it here: " [atom-input val]]])))

;; mount the reagent document
(reagent/render-component [timer-component]
                          (.-body js/document))