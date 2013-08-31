(ns freebase.core-test
  (:require [clojure.test :refer :all]
            [freebase.core :refer :all]))

(deftest test-wrap-api-key
  (is (= {}
         ((wrap-api-key identity nil) {})))
  (is (= {:oauth-token "API-KEY"}
         ((wrap-api-key identity "API-KEY") {}))))

(deftest test-wrap-input-coercion
  (is (= {} ((wrap-input-coercion identity) {})))
  (is (= {:query-params {:query "{\"type\":\"/music/artist\",\"name\":\"The Police\",\"album\":[]}"}}
         ((wrap-input-coercion identity)
          {:query-params {:query {:type :/music/artist :name "The Police" :album []}}}))))
