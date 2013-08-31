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
              (if (sequential? (:query (:query-params req)))
                (->> (if cursor (fetch (assoc-in req [:query-params :cursor] cursor)))
                     (concat result)
                     (lazy-seq))
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
