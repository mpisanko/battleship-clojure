(ns battleship.validation
  (:require [clojure.set :as s]))

(defn valid?
  [pred ship board]
  (pred ship board))

(defn v-col
  [ship board]
  (valid? s/superset? (:v-cols board) (map first ship)))

(defn v-row
  [ship board]
  (valid? s/superset? (:v-rows board) (map second ship)))

(defn v-size
  [ship board]
  (valid? contains? (:v-sizes board) (count ship)))

(defn v-b-size
  [ship board]
  (valid? (comp not contains?) (:sizes board) (count ship)))

(defn pos-vacant
  [ship board]
  (valid? (comp empty? s/intersection) (:positions board) ship))

(defn- all-true?
  [ship board validators]
  (reduce
    (fn [acc validator] (and acc (validator ship board)))
    true
    validators))

(defn can-place?
  [ship board]
  (all-true? ship board [v-col v-row v-size v-b-size pos-vacant]))

(defn valid-position?
  [pos board]
  (all-true? #{pos} board [v-col v-row]))
