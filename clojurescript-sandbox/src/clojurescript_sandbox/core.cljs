(ns clojurescript-sandbox.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(println "Reagent Snippet console output")

(defn timer-component []
  (let [seconds-elapsed (reagent/atom 0)]
    (fn []
      (js/setTimeout #(swap! seconds-elapsed inc) 100)
      [:div
       "Seconds Elapsed: " (int (/ @seconds-elapsed 10))])))

(reagent/render-component [timer-component]
                          (. js/document (getElementById "main")))

