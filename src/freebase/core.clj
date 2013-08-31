(ns freebase.core
  (:require [cheshire.core :as json]
            [clj-http.client :as client]))

(def ^:dynamic *mql-read-url*
  "https://www.googleapis.com/freebase/v1/mqlread")

(defn wrap-api-key
  "Returns a `client` that adds a OAuth2 token to the request."
  [client oauth-token]
  (fn [request]
    (client
     (if oauth-token
       (assoc request :oauth-token oauth-token)
       request))))

(defn wrap-input-coercion
  "Returns a `client` that encodes the Freebase query into JSON."
  [client]
  (fn [request]
    (client
     (if-let [query (-> request :query-params :query)]
       (assoc-in request [:query-params :query] (json/encode query))
       request))))

(defn wrap-output-coercion
  "Returns a `client` that decodes the Freebase JSON response."
  [client]
  (fn [request]
    (let [{:keys [body] :as request} (client request)]
      body)))

(defn wrap-cursor
  "Returns a `client` that handles Freebase cursors as a lazy sequence."
  [client]
  (letfn [(fetch [req]
            (let [{:keys [cursor result]} (client req)]
              (println (format "SEQUENTIAL: %s" (sequential? (:query (:query-params req)))))
              (println (format "    CURSOR: %s" cursor))
              (println (format "      NAME: %s" (prn-str (map :name result))))
              (if (sequential? (:query (:query-params req)))
                (lazy-seq
                 (concat result (if cursor (fetch (assoc-in req [:query-params :cursor] cursor)))))
                result)))]
    #(fetch %1)))

(defn client
  "Returns a Freebase HTTP client."
  [& [oauth-token]]
  (-> client/request
      (wrap-api-key oauth-token)
      (wrap-input-coercion)
      (wrap-output-coercion)
      (wrap-cursor)))

(defmacro defquery
  "Define a Freebase query."
  [name doc args & body]
  `(defn ~name ~doc [~@args & [~'req]]
     {:method :get
      :as :auto
      :url *mql-read-url*
      :query-params {:cursor nil :query (do ~@body)}}))

(comment

  (defquery continents []
    "Fetch continents from Freebase."
    [{:type "/base/locations/continents"
      :guid nil
      :name nil}])

  (defquery countries []
    "Fetch countries from Freebase."
    [{:type "/base/locations/countries"
      :guid nil
      :name nil
      :continent {:guid nil :name nil}}])

  (defquery languages []
    "Fetch languages from Freebase."
    [{:type "/language/human_language"
      :name nil
      :guid nil
      :iso_639_1_code []
      :iso_639_2_code []
      :iso_639_3_code []}])

  (defquery companies []
    "Fetch companies from Freebase."
    [{:type "/organization/organization"
      :guid nil
      :name []}])

  (defquery stock-exchanges []
    "Fetch Stock Exchanged from Freebase."
    [{:type "/finance/stock_exchange"
      :guid nil
      :name []
      :/finance/stock_exchange/companies_traded [{}]}])

  (continents)
  (first (countries))

  (clojure.pprint/pprint (take 2 (stock-exchanges)))

  (count (stock-exchanges))

  (take 5 (companies))

  (clojure.pprint/pprint (take 10 (continents)))

  (keyword "ISO 639-3 Code")

  (let [xs (languages)]
    (doseq [x xs]
      (clojure.pprint/pprint x)
      (println))
    (println (str "TOTAL: " (count xs))))

  )
