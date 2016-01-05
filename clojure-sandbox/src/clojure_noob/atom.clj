
(def fred (atom {:cuddle-hunger-level 0
                 :percent-deteriorated 0}))

(println @fred)

(let [zombie-state @fred]
     (if (>= (:percent-deteriorated zombie-state) 50)
       (future (println (:percent-deteriorated zombie-state)))))

(swap! fred
  #(merge-with + % {:cuddle-hunger-level 1}))


(println @fred)
(println fred)

(def v (atom [1 2 3]))
(println @v)
(swap! v conj 4)
(println @v)


(def c (atom 0))
(println @c)
(swap! c inc)
(println @c)