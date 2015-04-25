(ns battleship.game
  (:require [battleship.board :as b]
            [battleship.validation :as v]))

(def ^:private game-boards (atom {:p1 {} :p2 {}}))
(def ^:private players #{:p1 :p2})

(defn- board-for
  "get board for player"
  [player]
  (player @game-boards))

(defn- other-player
  "get the other player"
  [player]
  (first (filter (partial (comp not =) player) players)))

(defn- other-board
  "get other player's board"
  [player]
  (board-for (other-player player)))

(defn set-boards!
  "Set boards to those passed in - resets game's state"
  ([new-boards]
   (reset! game-boards new-boards))
  ([p1-board p2-board]
   (reset! game-boards {:p1 p1-board :p2 p2-board})))

(defn place-ship-for!
  "Place ship on a players board and return updated board"
  [player ship]
  (if-let [updated-board (b/place-ship ship (board-for player))]
    (player (swap! game-boards assoc player updated-board))))

(defn current-state
  "Establish state of game represented by boards"
  []
  (cond
    (b/all-positions-hit?
      (board-for :p1) (board-for :p2)) {:state :game-over :winner :p2}
    (b/all-positions-hit?
      (board-for :p2) (board-for :p1)) {:state :game-over :winner :p1}
    :else {:state :in-play}))

(defn attack!
  "Attack other player's ships - mutates state. Returns updated board"
  [player position]
  (let [board (board-for player)
        others-board (other-board player)]
    (if (v/valid-position? position others-board)
      (if (contains? (:positions others-board) position)
        (do
          (player (swap! game-boards update-in [player :hit] conj position))
          :hit)
        (do
          (player (swap! game-boards update-in [player :missed] conj position))
          :miss))
      :invalid-position)))
