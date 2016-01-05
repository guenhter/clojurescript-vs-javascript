
(def GRID_H_W 4) ; 1 px border makes 10 px per cell
(def COLOR_DEAD_CELL "#D9D9D9")
(def COLOR_LIFE_CELL "#0000FF")
(def COLOR_BORDER "#9E9E9E")
(def MAX_COLUMNS 200)
(def MAX_ROWS 100)
(def SPEED_MS 10)
(def TRACK_GENERATIONS true)

(def pattern1 [[1,0], [1,2], [0,2], [3,1], [4,2], [5,2], [6,2]])

(def count (atom 1)) ; to be deleted
(def gamefield (atom []))

(defn coordinate [x y]
  (+ (* MAX_COLUMNS x) (* MAX_ROWS y)))

(defn set [field x y value]
  (assoc field (coordinate x y) value))

(defn initialGeneration []
  (let [initialField  (atom (vec (replicate (* MAX_COLUMNS MAX_ROWS) false)))]
    (doseq [p pattern1]
      (swap! initialField assoc (coordinate (first p) (last p)) true))
    @initialField))

(defn newGeneration []
  (let [current (last gamefield)]
    current))

(defn moveToNextGeneration![]
  (let [newGen (newGeneration)]
    (swap! gamefield conj newGen)
    (println @gamefield)))

;setup the gamefield with a base state
(swap! gamefield conj (initialGeneration))

;this runs simulate the single lives
(moveToNextGeneration!)
(moveToNextGeneration!)