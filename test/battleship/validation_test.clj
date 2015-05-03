(ns battleship.validation-test
  (:require [battleship.validation :refer :all]
            [battleship.board :as b]
            [midje.sweet :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]))

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

(def val-pos-gen
  (gen/tuple (gen/elements (:v-cols b)) (gen/elements (:v-rows b))))

(def invalid-pos-gen
  (gen/tuple
    (gen/such-that #(not (contains? (:v-cols b) %)) gen/keyword)
    (gen/such-that #(not (contains? (:v-rows b) %)) gen/int)))

(facts
  valid-position?
  (fact "valid"
    (tc/quick-check 1000
      (prop/for-all
        [p val-pos-gen]
        (valid-position? p b))) => (contains {:result true}))
  (fact "invalid"
    (tc/quick-check 1000
      (prop/for-all
        [p invalid-pos-gen]
        (= false (valid-position? p b)))) => (contains {:result true})))
