(ns clojure-noob.misc)

(defn separator
  [title]
  (println)
  (println (list "#########" title "#############")))

(println (type 999888777666555444333222111))
(println (* 999888777666555444333222111000 999888777666555444333222111000))
(println (+ 1N 0x7fffffffffffffff))

(println 0x7fffffffffffffff)

(println "hallo
asldjf
alsdjf")

(println "Hallo\"World")

(println 9999999999999999999.111111111111)
(println (type 9999999999999999999.111111111111))
(println 9999999999999999999.111111111111M)

(def persons {:name "hubert" :city "linz"})
(println (:name persons))

(println (:notexisting persons))

(separator "Symbols")
(def sym1 "sym1")
(println sym1)
(def sym1 "othersum")
(println sym1)

(separator "PI")
(def pi 3.1416)
(def pi? #(= % pi))

(println pi)
(println (pi? 3.1416))
(println (pi? 3.1417))

(println (count (time (for [x (range (* 900 700))] x))))
(println (count (time (doall (for [x (range (* 900 700))] x)))))
(println (count (time (vec (for [x (range (* 900 700))] x)))))

(do (println "111")
    (println "222")
    (println "333"))

(let [x 5] (println x))

(println true)

(println ({:a 1} :a))