(ns battleship.board-test
  (:require [midje.sweet :refer :all]
            [battleship.board :refer :all]))

(def b empty-board)

(facts
  random-ship
  (fact
    "random ship for a board allocates largest possible ship first"
    (count (random-ship b)) => 5)
  (fact
    "random ship for a board allocates largest possible ship first"
    (count (random-ship (assoc b :sizes #{5}))) => 4)
  (fact
    "random ship for a board allocates largest possible ship first"
    (count (random-ship (assoc b :sizes #{4 5}))) => 3))

(def ship #{[:a 1]})
(def with-one-ship (place-ship ship b))

(facts
  place-ship
  (fact
    "placing a ship adds its size to the board"
    (:sizes with-one-ship) => #{(count ship)})
  (fact
    "placing a ship adds ships position"
    (:positions with-one-ship) => ship)
  (fact
    "trying to place invalid ship yields nil"
    (place-ship #{[:a 2]} with-one-ship) => nil))

(facts
  all-positions-hit?
  (fact
    "returns true when hits are the same as positions of ships"
    (all-positions-hit? {:positions #{[:a 1] [:j 10] [:i 10]}}
                         {:hit #{[:i 10] [:a 1] [:j 10]}}) => true)
   (fact
     "returns false when at least one position has not been hit"
     (all-positions-hit? {:positions #{[:a 10] [:c 2]}}
                          {:hit #{[:c 2]}}) => false))
(facts
  random-board
  (fact
    "board has five ships"
    (count (:sizes (random-board b))) => 5)
  (fact
    "sum of all ship lengths is 15"
    (count (:positions (random-board b))) => 15))
