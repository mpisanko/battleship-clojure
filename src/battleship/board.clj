(ns battleship.board
  (:require [clojure.set :as s]
            [battleship.validation :as v]))

;; TODO the dimesions (v-cols v-rows v-sizes)
;; might be better off being meta-data of the board?
(def empty-board
  {:sizes #{}
   :positions #{}
   :hit #{}
   :missed #{}
   :v-cols (sorted-set :a :b :c :d :e :f :g :h :i :j)
   :v-rows (sorted-set 1 2 3 4 5 6 7 8 9 10)
   :v-sizes (sorted-set 1 2 3 4 5)})

(defn- horiz-tuppler [fix o] (vector o fix))
(defn- vert-tuppler [fix o] (vector fix o))

(defn- randomise-positions
  [size offset rows cols tuppler]
  (let [fix (rand-nth (into [] rows))
        vars (take size (drop offset cols))]
    (set (map (partial tuppler fix) vars))))

(defn random-ship
  [board]
  (if-not (= (:sizes board) (:v-sizes board))
    (let [{sizes :sizes v-sizes :v-sizes v-rows :v-rows v-cols :v-cols} board
          size (apply max (s/difference v-sizes sizes))
          len (count v-rows)
          offset (rand-int (- len size))]
      (if (rand-nth [true false])
        (randomise-positions size offset v-rows v-cols horiz-tuppler)
        (randomise-positions size offset v-cols v-rows vert-tuppler)))))

(defn place-ship
  "Try placing a ship on given board
   Return updated board with ship placed or nil"
  [ship board]
  (if (v/can-place? ship board)
    (let [{sizes :sizes pos :positions} board
          size (count ship)]
      (assoc board
        :positions (s/union pos ship)
        :sizes (conj sizes size)))))

(defn random-board
  "Randomly fill a given board (board specifies its dimentions)"
  [empty-board]
  (letfn [(next-board [ship board]
            (if-let [new-board (place-ship ship board)]
              (if (= (:sizes new-board) (:v-sizes new-board))
                new-board
                (recur (random-ship new-board) new-board))
              (recur (random-ship board) board)))]
    (next-board (random-ship empty-board) empty-board)))

(defn all-positions-hit?
  "Have all the positions in target board been hit by attacks?"
  [target attacks]
  (= (:positions target) (:hit attacks)))
