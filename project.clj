(defproject freebase-clj "0.1.0"
  :description "Clojure Library for Freebase."
  :url "https://github.com/r0man/freebase-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-http "0.7.6"]]
  :profiles {:dev {:dependencies [[org.slf4j/slf4j-log4j12 "1.7.5"]]
                   :resource-paths ["test-resources"]}})
