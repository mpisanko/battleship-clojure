(ns battleship.validation-test
  (:require [battleship.validation :refer :all]
            [battleship.board :as b]
            [midje.sweet :refer :all]))

(def b b/empty-board)
(def valid-ship #{[:c 2] [:c 3] [:c 4]})
(def invalid-ship #{[:z 11] [:z 12] [:z13]})
(def non-empty (assoc (assoc b :sizes #{4}) :positions #{[:a 1] [:a 2] [:a 3] [:a 4]}))

(facts
  can-place?
  (fact
    "can place valid ship"
    (can-place? valid-ship b) => true)
  (fact
    "cannot place invalid ship"
    (can-place? invalid-ship b) => false)
  (fact
    "can place ship on non-empty board"
    (can-place? valid-ship non-empty) => true)
  (fact
    "cannot place ship overlapping another"
    (can-place? #{[:a 1] [:b 1] [:c 1]} non-empty) => false)
  (fact
    "cannot place more than one shio of the same size"
    (can-place? #{[:a 6] [:a 7] [:a 8] [:a 9]} non-empty) => false))

(facts
  valid-position?
  (tabular "valid - invalid positions"
    (fact (valid-position? ?position b) => ?valid)
    ?position   ?valid
    [:a 1]      true
    [:j 10]     true
    [:c 7]      true
    [:a -1]     false
    [:k 2]      false
    [:z 11]     false))
