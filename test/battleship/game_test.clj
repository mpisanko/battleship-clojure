(ns battleship.game-test
  (:require [midje.sweet :refer :all]
            [battleship.game :refer :all]
            [battleship.board :as b]))

(def eb b/empty-board)

(def b-a-1 (assoc eb :positions #{[:a 1] [:a 2]} :sizes #{2}))
(def b-c-9 (assoc eb :positions #{[:c 9] [:d 9] [:e 9]} :sizes #{3}))

(against-background [(before :facts (set-boards! b-a-1 b-c-9))]
  (facts
    attack!
    (tabular "returns appropriate result"
      (fact (attack! ?player ?position) => ?result)
      ?player   ?position   ?result
      :p2       [:a 1]      :hit
      :p2       [:a 2]      :hit
      :p2       [:b 2]      :miss
      :p1       [:a 1]      :miss
      :p1       [:c 9]      :hit
      :p1       [:d 9]      :hit
      :p1       [:e 9]      :hit
      :p1       [:m 12]     :invalid-position))
  (facts
    current-state
    (fact "when there are no hits"
      (current-state) => {:state :in-play})
    (fact "when neither player has hit all other's positions"
      (do
        (attack! :p2 [:a 1])
        (attack! :p1 [:d 9])
        (current-state)) => {:state :in-play})
    (fact "when p2 has hit all p1's positions"
      (do
        (attack! :p2 [:a 1])
        (attack! :p2 [:a 2])
        (current-state)) => {:state :game-over :winner :p2}))
  (facts
    place-ship-for!
      (fact "places another ship for given player"
        (place-ship-for! :p1 #{[:c 3] [:d 4] [:e 5]}) =>
          (contains
            {:positions #{[:a 1] [:a 2] [:c 3] [:d 4] [:e 5]}
             :sizes #{2 3}}))))
