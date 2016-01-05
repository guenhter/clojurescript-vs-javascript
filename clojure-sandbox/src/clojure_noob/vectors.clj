
(def v [1 2 3])
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