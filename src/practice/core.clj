(ns practice.core
  (:require
   [practice.server :as server]
   [practice.file-content :as file-content])
  (:gen-class))

(defn -main
  "Entry point to application"
  [& args]
  (let [server? (= (first args) "server")
        file-name (first args)
        sort-mode (second args)]
    (if server?
      (server/web-server)
      (do
        (println "Initializing CLI...")
        (file-content/show-file-contents file-name sort-mode)))))

  
