(ns practice.api
  (:require 
    [clojure.data.json :as json]
    [compojure.core :refer :all]
    [compojure.route :as route]
    [practice.file-content :as file-content]
    [ring.middleware.defaults :refer :all]))

;; GET /records/email - returns records sorted by email
(defn get-email [{:keys [params] :as req}]
  (let [file (or (:file params) "pipes.txt")
        sorted-records (file-content/show-file-contents file "email")]
    {:status  200
     :headers {"Content-Type" "application/json"}
     :body    (json/write-str sorted-records)}))

;; GET /records/birthdate - returns records sorted by birthdate
(defn get-dob [{:keys [params] :as req}]
  (let [file (or (:file params) "pipes.txt")
        sorted-records (file-content/show-file-contents file "birth date")]
    {:status  200
     :headers {"Content-Type" "application/json"}
     :body    (json/write-str sorted-records)}))

;; GET /records/birthdate - returns records sorted by birthdate
(defn get-name [{:keys [params] :as req}]
  (let [file (or (:file params) "pipes.txt")
        sorted-records (file-content/show-file-contents file "last name")]
    {:status  200
     :headers {"Content-Type" "application/json"}
     :body    (json/write-str sorted-records)}))

(defroutes app-routes
  (GET "/records/email" [] get-email)
  (GET "/records/birthdate" [] get-dob)
  (GET "/records/name" [] get-name)
  (route/not-found "Error, page not found!"))