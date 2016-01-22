(ns org.fhooe.swk)

(defn public-f [x]
  x)
(defn ^:private private-f1 [x]
  x)
(defn- private-f2 [x]
  x)
(def ^:private private-v 5)

;; (fn name? [params*] exprs*)(fn name? ([params*] exprs*) +)

(defn f1 [x] (* 2 x))
(fn f2 [x] (* 2 x))




(println ["f1(5):" (f1 5)])
;(println ["f2(5):" (f2 5)])
(println ["ann(5):" (do (fn [x] (* 2 x)) 5)])



(println (public-f private-v))
(println (private-f1 private-v))
(println (private-f2 private-v))