(ns practice.util
  (:require
   [clojure.string :as string]))

(defn format-dob
  [dob]
  {:pre [(string/includes? dob "/")]} ;; simple validation
  (->> dob
       (.parse (java.text.SimpleDateFormat. "MM/dd/yyyy"))
       (.format (java.text.SimpleDateFormat. "M/dd/yyyy"))))

(defn format-and-replace-dob 
  "Takes in a sequence of maps and replaces dob with the formatted dob"
  [coll]
  (map (fn [e]
         (let [old-dob (:dob e)] 
           (assoc e :dob (format-dob old-dob))))
       coll))

(defn convert-to-map
  "Takes in a sequence of vectors and converts to a sequence of maps assuming values are what they are supposed to be"
  [coll]
  (map #(apply assoc {} 
               (interleave [:last-name :first-name :email :fav-color :dob] %)) coll))

(defn determine-delimeter
  [s]
  (cond
    (string/includes? s "|")
    "PIPE"
    (string/includes? s ",")
    "COMMA"
    (string/includes? s " ")
    "SPACE"
    :else nil))
