(ns practice.core
  (:require
   [practice.server :as server])
  (:gen-class))

(defn -main
  "Entry point to application"
  []
  (println "Initializing Application...")
  (server/web-server))

