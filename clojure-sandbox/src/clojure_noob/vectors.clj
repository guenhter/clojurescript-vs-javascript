
(def v [1 3 2])
(println v)

(println (get v 5))
(def w (assoc v 1 5))
(println w)
(def w (assoc v 1 6))
(println w)

(doseq [a [1 2 3]]
  (println a))

(defn init []
  (let [s (atom [])]
    (doseq [a [1 2 3]]
      (swap! s conj a))
    @s))

(println (init))

(println (assoc [1 2 3] 1 33))

(println '(1 2 3))

(println [1 3 2])
(println (type [1 3 2]))
(println (type (vector 1 2)))
(println (type (vector-of :int 1 2)))
(println (type (conj (vector-of :int 1 2) 4)))
(println (type (vec '(1 2))))

(println (type (set [1 1 2])))
(println (type (into [] (set [1 1 2]))))
