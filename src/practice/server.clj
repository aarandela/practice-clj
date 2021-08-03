(ns practice.server
  (:require 
    [org.httpkit.server :as server]
    [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
    [practice.api :refer [app-routes]])
  (:gen-class))

(defn web-server
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    ; Run the server with Ring.defaults middleware        
    (server/run-server (wrap-defaults #'app-routes api-defaults) {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
