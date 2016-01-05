(def fibonacci
  ((fn recurFib [a b]
     (lazy-seq (cons a (recurFib b (+ a b)))))
    0 1))
(println (take 20 fibonacci))