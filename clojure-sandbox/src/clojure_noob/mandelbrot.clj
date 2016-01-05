
(def WIDTH 900)
(def HEIGHT 600)

(defonce history (atom []))
(defonce iteration (atom 0))

(defn mandelbrotPixelIter [cx, cy, maxIter]
  (loop [x 0.0
         y 0.0
         currentIter 0]
    (let [x2 (* x x) y2 (* y y)] ; just define squares
      (if (and (< currentIter maxIter)
               (< (+ x2 y2) 4))
        (recur (+ (- x2 y2) cx)
               (+ (* 2 x y) cy)
               (inc currentIter))
        currentIter))))

(defn mandelbrotBoardIter [xmin, xmax, ymin, ymax, iterations]
  (for [iy (range HEIGHT)
        ix (range WIDTH)]
    (let [x (+ xmin (/ (* (- xmax xmin) ix) (- WIDTH 1)))
          y (+ ymin (/ (* (- ymax ymin) iy) (- HEIGHT 1)))]
      (mandelbrotPixelIter x y iterations))))

;(time (doall (mandelbrotBoardIter -2 1 -1 1 2)))

(def xmin -2)
(def xmax 1)
(time (doall (for [ix (range (* 1000 1000))]
               (+ xmin (/ (* (- xmax xmin) ix) (- WIDTH 1))))))

(def help (double (/ 3 599)))
(def help2 (/ 3 599))
(time (doall (for [ix (range (* 1000 1000))]
               (+ xmin (* ix help)))))

(time (loop [x 0]
        (if (< x 1000000)
          (do (+ -2 (/ (* (- 1 -2) x) (- WIDTH 1)))
              (recur (inc x))))))