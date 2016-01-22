(ns test)

;; This Method represents the async call (even if it isnt async)
(defn async-external-call [url callback]
  (println "External call with " url)
  ;; here the real external call would be done
  (callback "result from external call"))

(defn process-result[result]
  ; do somthing with the result
  (println result)
  )

;; Macro to run async calls one after another
(defmacro call-after-each-other [& body]
  (reduce
    (fn [x y]
      `(async-external-call (first ~y)
                            (fn [result#]
                              ~x
                              ((second ~y) result#))))
    #() ; needed as terminator in the chain
    (reverse body)))

(call-after-each-other
  ["www.Aristoteles.org" process-result]
  ["www.Archimedes.org" process-result]
  ["www.Kopernikus.org" process-result]
  ["www.Kepler.org" process-result]
  ["www.Pascal.org" process-result]
  ["www.Newton.org" process-result]
  ["www.Bernoulli.org" process-result]
  ["www.Euler.org" process-result])
