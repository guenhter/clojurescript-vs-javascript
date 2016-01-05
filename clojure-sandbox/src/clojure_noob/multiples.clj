
(def LIMIT 1000)
(def multiples [3 5])

(defn multiplier-creator [x]
  #(* x %))

(defn multiple [factor]
  (map (multiplier-creator factor) (range 1 (/ LIMIT factor))))

(println (set (reduce concat (map multiple multiples))))
(println (reduce + (set (reduce concat (map multiple multiples)))))
