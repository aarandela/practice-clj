(ns practice.file-content
  (:require
   [clojure.pprint :refer [print-table]]
   [clojure.string :as string]
   [practice.util :as util]))
  

(defn parse-str-delimeter 
  "Takes in a string and determines what delimeter it uses and returns a sequence of vectors"
  [file-contents]
  {:pre [(string? file-contents)]}
  (let [file-contents-vec (string/split-lines file-contents)
        delimeter (util/determine-delimeter file-contents)]
    (cond
      (= delimeter "PIPE")
      (->> file-contents-vec 
           (map #(string/split % #" \| ")))
      
      (= delimeter "COMMA")
      (->> file-contents-vec
           (map #(string/split % #", ")))
      
      (= delimeter "SPACE")
      (->> file-contents-vec
           (map #(string/split % #" ")))
      
      :else [])))

(defn sort-direction
  [direction s1 s2]
  (if (= direction :ascending)
    (compare (string/lower-case s1) (string/lower-case s2))
    (compare (string/lower-case s2) (string/lower-case s1))))

(defn output-1-comparator
  "Takes in the juxt values, [:email :last-name], and compares whether it should be ascending or descending"
  [coll1 coll2]
  (if (= (first coll1) (first coll2))
    (sort-direction :ascending coll1 coll2) 
    (sort-direction :descending coll1 coll2)))
  
(defn sort-by-column
  [file-contents column]
  (let [by-email (sort-by (juxt :email :last-name) #(output-1-comparator %1 %2) file-contents)
        by-dob (sort-by :dob file-contents)
        by-last-name (sort-by #(-> % :last-name string/lower-case)
                              #(sort-direction :ascending %1 %2)
                              file-contents)]
    (cond
      (or (= column "email") (= column "1"))
      by-email

      (or (= column "birth date") (= column "2"))
      by-dob

      (or (= column "last name") (= column "3"))
      by-last-name

      :else 
      {:output-1 by-email
       :output-2 by-dob
       :output-3 by-last-name})))

(defn show-output
  [coll output]
  (if (seq? coll)
    (do
      (println "Output # " output)
      (print-table coll))
    (do 
      (println "Output # 1 – sorted by email, descending, then by last name ascending.")
      (print-table (:output-1 coll))
      (println " ")
      (println "Output # 2 – sorted by birth date, ascending.")
      (print-table (:output-2 coll))
      (println " ")
      (println "Output # 3 – sorted by last name, descending.")
      (print-table (:output-3 coll)))))
  

(defn show-file-contents
  "Takes as input a file with a set of records and outputs the set of records sorted
   Returns the data for API"
  ([file]
   (show-file-contents file nil))
  ([file sort-mode]
   (let [file-contents (slurp file)
         parsed-file-contents (parse-str-delimeter file-contents)
         vecs->maps (util/convert-to-map parsed-file-contents)
         formatted-file-contents (util/format-and-replace-dob vecs->maps)
         sorted-columns (sort-by-column formatted-file-contents sort-mode)]
     (show-output sorted-columns sort-mode)
     sorted-columns)))

(defn determine-file
  [delimeter]
  (cond
    (= delimeter "PIPE")
    "./records/pipes.txt"
    (= delimeter "COMMA")
    "./records/commas.csv"
    (= delimeter "SPACE")
    "./records/spaces.txt"
    :else nil))

(defn append-to-file
  "Adds new line to a file"
  [filename s]
  (spit filename (apply str "\n" s) :append true)) 
