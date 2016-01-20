(ns mandelbrot-cljs.core
  (:require [cljs.reader :as reader])
  (:use [jayq.core :only [$ css html]]))

(enable-console-print!)

(def WIDTH 900)
(def HEIGHT 600)

(def state (atom { :started false }))
(def history (atom [(vector (repeat (* WIDTH HEIGHT) 0))]))
(def iteration (atom 1))

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

(defn mandelbrotBoardIterB [model, xmin, xmax, ymin, ymax, ix, iy, iterations]
  (if (and (= iy (dec HEIGHT)) (= ix (dec WIDTH)))
    model
    (let [x (+ xmin (/ (* (- xmax xmin) ix) (- WIDTH 1)))
          y (+ ymin (/ (* (- ymax ymin) iy) (- HEIGHT 1)))
          pixel (mandelbrotPixelIter x y iterations)
          index (+ (* WIDTH iy) ix)]
      (recur (if (not= (get model index) pixel)
               (assoc model index pixel)
               model)
             xmin
             xmax
             ymin
             ymax
             (if (= (inc ix) WIDTH) 0 (inc ix))
             (if (= (inc ix) WIDTH) (inc iy) iy)
             iterations))))

(defn mandelbrotBoardIter [model, xmin, xmax, ymin, ymax, iterations]
  (mandelbrotBoardIterB model xmin xmax ymin ymax 0 0 iterations))

;(defn mandelbrotBoardIterOri [model, xmin, xmax, ymin, ymax, iterations]
;  (vec
;    (for [iy (range HEIGHT)
;          ix (range WIDTH)]
;      (let [x (+ xmin (/ (* (- xmax xmin) ix) (- WIDTH 1)))
;            y (+ ymin (/ (* (- ymax ymin) iy) (- HEIGHT 1)))]
;        (mandelbrotPixelIter x y iterations)))))

(defn rgb [pix ppos r g b]
  (aset pix ppos r)
  (aset pix (+ ppos 1) g)
  (aset pix (+ ppos 2) b)
  (aset pix (+ ppos 3) 255)
  )

; TODO check if image can be cept persistier
(defn mandelbrotToCanvas [model canvas iterations]
  (let [ctx (.getContext (.get canvas 0) "2d")
        img (.getImageData ctx 0 0 WIDTH HEIGHT)
        pix (.-data img)]

    (doseq [ix (range WIDTH)
            iy (range HEIGHT)]
      (let [index (+ (* WIDTH iy) ix)
            ppos (* 4 index)
            i (get model index)]
        (if (>= i iterations)
          (rgb pix ppos 0 0 0)
          (let [c (/ (* 3 (Math/log i)) (Math/log (dec iterations)))]
            (cond
              (< c 1) (rgb pix ppos (* 255 c) 0 0)
              (< c 2) (rgb pix ppos 255 (* 255 (- c 1)) 0)
              :else (rgb pix ppos 255 255 (* 255 (- c 2))))))
       ))
    (.putImageData ctx img 0 0)))

(defn startStop [_e]
  "Set the corresponding information for the game to be stopped/started"
  (let [$button ($ :#startStopButton)
        $input ($ :#iterationsInput)]

    (if (@state :started)
      (do (.html $button "Start")
          (.prop $input "disabled" false)
          (swap! state assoc :started false))
      (do (.html $button "Stop")
          (reset! iteration 0)
          (.prop $input "disabled" true)
          (swap! state assoc :started true)))))

(defn gameLoop []
  (if (@state :started)
    (if (<= @iteration (reader/read-string (.val ($ :#iterationsInput))))
      (let [$out ($ :#iterationsOutput)
            $canvas ($ :#canvas)
            lastModel (last @history)]
        (set! (.-width $canvas) WIDTH)
        (set! (.-height $canvas) HEIGHT)

        (if (<= (count @history) @iteration)
          (swap! history conj (mandelbrotBoardIter lastModel -2 1 -1 1 @iteration)))

        (mandelbrotToCanvas (get @history @iteration) $canvas @iteration)
        (.html $out @iteration)
        (swap! iteration inc))
      (startStop nil)
      )))

(.click ($ :#startStopButton) startStop)
(.setInterval js/window gameLoop 30)

(defn on-js-reload [] )
