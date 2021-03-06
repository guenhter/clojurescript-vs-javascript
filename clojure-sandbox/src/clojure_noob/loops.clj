
(def vec [7 8])
(def a (into vec (set [1 2 3 1])))

(println a)

; doseq has no return value!
(println (doseq [x (range 10)]
  x))

; loop has a return value
(println (loop [x 10] x))

(println (for [x ['a 'b 'c]
      y [1 2 3]]
  [x y]))


(println (for [x (range 5)
               y (range 5)]
           (let [z (* 2 x)] [z y])))


(doall (map #(println %) [1 2 3]))

(println (loop [x 0
                sum 0]
           (if (< x 10)
             (recur (inc x) (+ sum x))
             sum)))

(defn s [x sum]
  (if (< x 1000000)
    (s (inc x) (+ x sum))
    sum)
  )
(println (s 0 0))

(println ((fn [x sum]
   (if (< x 1000000)
     (recur (inc x) (+ sum x))
     sum))
  0 0))