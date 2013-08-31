(ns freebase.core-test
  (:require [clojure.test :refer :all]
            [freebase.core :refer :all]))

(def query (client))

(defquery police-albums
  "Returns the album of `The Police`."
  []
  {:type :/music/artist
   :name "The Police"
   :album []})

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

(deftest test-wrap-output-coercion
  )

(deftest test-police-albums-request
  (is (= {:query-params {:cursor nil, :query {:type :/music/artist, :name "The Police", :album []}}
          :method :get
          :as :auto
          :url "https://www.googleapis.com/freebase/v1/mqlread"}
         (police-albums))))

(deftest test-police-albums-query
  (is (= {:album
          ["Outlandos d'Amour"
           "Reggatta de Blanc"
           "Zenyattà Mondatta"
           "Ghost in the Machine"
           "Synchronicity"
           "Every Breath You Take: The Singles"
           "Message in a Box: The Complete Recordings"
           "Live!"
           "Every Breath You Take: The Classics"
           "Their Greatest Hits"
           "Roxanne '97"
           "The Police"
           "Greatest Hits"
           "The Very Best of Sting &amp; The Police"
           "Brimstone &amp; Treacle"
           "De Do Do Do, De Da Da Da"
           "Certifiable: Live in Buenos Aires"
           "Roxanne"
           "2007-09-16: Geneva"
           "Live in Boston"
           "The 50 Greatest Songs"
           "King of Pain"
           "Invisible Sun"
           "Message in a Bottle"
           "Spirits in the Material World"
           "Don't Stand So Close to Me '86"
           "When the World Is Running Down (You Can't Go Wrong)"
           "Reunion Concert 1986"
           "1981-02-02: Budokan Hall, Tokyo, Japan"
           "Every Breath You Take"
           "Can't Stand Losing You"
           "Six Pack"
           "Fall Out"
           "So Lonely"
           "Bring on the Night"
           "Walking on the Moon"
           "The Bed's Too Big Without You"
           "Don't Stand So Close to Me"
           "Every Little Thing She Does Is Magic"
           "Secret Journey"
           "Wrapped Around Your Finger"
           "Synchronicity II"
           "When the World Is Running Down, You Make the Best of What's Still Around"
           "Voices in my Head"
           "Backing Track"
           "Can't Stand Losing You"],
          :name "The Police",
          :type "/music/artist"}
         (query (police-albums)))))
