

;; (fn name? [params*] exprs*)(fn name? ([params*] exprs*) +)

(defn f1 [x] (* 2 x))
(fn f2 [x] (* 2 x))




(println ["f1(5):" (f1 5)])
;(println ["f2(5):" (f2 5)])
(println ["ann(5):" (do (fn [x] (* 2 x)) 5)])