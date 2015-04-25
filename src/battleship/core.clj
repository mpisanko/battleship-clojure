(ns battleship.core
  (:gen-class :main true)
  (:require [battleship.game :as g]
            [battleship.board :as b]))

(defn create-empty-boards!
  "Create empty boards for two players - resets game's state"
  []
  (g/set-boards! b/empty-board b/empty-board))

(defn create-random-boards!
  "Create random boards for both players - resets game's state"
  []
  (g/set-boards! b/random-board b/random-board))

(defn current-game-state
  "Establish state of game represented by boards"
  []
  (g/current-state))

(defn place-ship!
  "places a ship for given player"
  [player ship]
  (g/place-ship-for! player ship))

(defn attack!
  "Attack other player's ships - mutates state"
  [player position]
  (g/attack! player position))
