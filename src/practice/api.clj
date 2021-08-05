(ns practice.api
  (:require 
    [clojure.data.json :as json]
    [compojure.core :refer [defroutes GET POST]]
    [compojure.route :as route]
    [practice.file-content :as file-content]
    [practice.util :as util]
    [ring.middleware.defaults :refer :all]))

;; ---------------------------------------------------------------------------------------------------
;; Handlers

;; GET /records/email - returns records sorted by email
(defn get-email [{:keys [params] :as req}]
  (let [file (or (:file params) "./records/pipes.txt")
        sorted-records (file-content/show-file-contents file "email")]
    {:status  200
     :headers {"Content-Type" "text/json"}
     :body (with-out-str (json/pprint sorted-records))}))

;; GET /records/birthdate - returns records sorted by birthdate
(defn get-dob [{:keys [params] :as req}]
  (let [file (or (:file params) "./records/pipes.txt")
        sorted-records (file-content/show-file-contents file "birth date")]
    {:status  200
     :headers {"Content-Type" "text/json"}
     :body    (with-out-str (json/pprint sorted-records))}))

;; GET /records/birthdate - returns records sorted by birthdate
(defn get-name [{:keys [params] :as req}]
  (let [file (or (:file params) "./records/pipes.txt")
        sorted-records (file-content/show-file-contents file "last name")]
    {:status  200
     :headers {"Content-Type" "text/json"}
     :body    (with-out-str (json/pprint sorted-records))}))

(defn add-record [{:keys [params] :as req}]
  (let [content-to-add (:new-line params)
        delimeter (util/determine-delimeter content-to-add)
        filename (file-content/determine-file delimeter)]
     (file-content/append-to-file filename content-to-add)
     {:status  201
      :headers {"Content-Type" "text/json"}
      :body    (json/write-str {:message "success"})}))


;; ---------------------------------------------------------------------------------------------------
;; Routes

(defroutes app-routes
  (POST "/records" [] add-record)
  (GET "/records/email" [] get-email)
  (GET "/records/birthdate" [] get-dob)
  (GET "/records/name" [] get-name)
  (route/not-found "Error, page not found!"))
