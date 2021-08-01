(ns practice.server
  (:require 
    [org.httpkit.server :as server]
    [compojure.core :refer :all]
    [compojure.route :as route]
    [ring.middleware.defaults :refer :all])
  (:gen-class))

; Simple Body Page
(defn some-text [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "Hello World"})

(defroutes app-routes
  (GET "/" [] some-text)
  (route/not-found "Error, page not found!"))

(defn web-server
  "This is our entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    ; Run the server with Ring.defaults middleware
    (server/run-server (wrap-defaults #'app-routes site-defaults) {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
