(ns om-sandbox.core
  (:require [om.core :as om :include-macros true]
            [sablono.core :as html :refer-macros [html]]))

(def state (atom {:alpha {:beta {:gamma {:delta {:epsilon {:zeta "Some Value"}}}}}}))

(let [cursor (om/ref-cursor (get-in (om/root-cursor state) [:alpha :beta :gamma :delta :epsilon]))]
  (om/update! cursor :zeta "Some Other value"))



(def app-state (atom {:counter 0}))

(defn ticker-component [data owner]
  (om/component
    (js/setTimeout #(do (swap! app-state (fn[state] (merge-with + state {:counter 1})))) 100)
    (html [:div
           [:div "Seconds Elapsed: " (int (/ (:counter @app-state) 10))]])))

(om/root
  ticker-component
  app-state
  {:target (.-body js/document)})