(defproject battleship "0.0.1"
  :description "battleship simulation"
  :url "https://github.com/mpisanko/battleship-clojure"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]]
                   :plugins [[lein-midje "3.1.3"]]}
             :uberjar {:aot :all}}
  :main ^:skip-aot battleship.core)
