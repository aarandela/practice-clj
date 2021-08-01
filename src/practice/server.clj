(ns practice.server
  (:require 
    [org.httpkit.server :as server]
    [ring.middleware.defaults :refer :all]
    [practice.api :refer [app-routes]]
    [clojure.pprint :as pp]
    [clojure.string :as str]
    [clojure.data.json :as json])
  (:gen-class))

(defn web-server
  "This is our entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    ; Run the server with Ring.defaults middleware
    (server/run-server (wrap-defaults #'app-routes site-defaults) {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
