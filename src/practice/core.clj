(ns practice.core
  (:require
   [clojure.pprint :refer [print-table]]
   [clojure.string :as string]
   [practice.util :as util])
  (:gen-class))

(defn determine-and-format 
  "Takes in a file and determines what delimeter it uses, then formats it to a sequence of maps"
  [file-contents]
  {:pre [(string? file-contents)]}
  (let [file-contents-vec (string/split-lines file-contents)]
    (cond
      (string/includes? file-contents "|")
      (->> file-contents-vec 
           (map #(string/split % #" \| "))
           (util/convert-to-map)
           (util/format-and-replace-dob))
      
      (string/includes? file-contents ",")
      (->> file-contents-vec
           (map #(string/split % #", "))
           (util/convert-to-map)
           (util/format-and-replace-dob))
      
      (string/includes? file-contents " ")
      (->> file-contents-vec
           (map #(string/split % #" "))
           (util/convert-to-map)
           (util/format-and-replace-dob))
      
      :else {})))
      

(defn output-1-comparator
  "Takes in the juxt values, [:email :last-name], and compares whether it should be ascending or descending"
  [coll1 coll2]
  (if (= (first coll1) (first coll2))
    (compare (string/lower-case coll1) (string/lower-case coll2)) ;; ascending
    (compare (string/lower-case coll2) (string/lower-case coll1)))) ;; descending
  
(defn sort-by-column
  [file-contents column]
  (let [by-email (sort-by (juxt :email :last-name) #(output-1-comparator %1 %2) file-contents)
        by-dob (sort-by :dob file-contents)
        by-last-name (sort-by #(-> % :last-name string/lower-case)
                              #(compare (string/lower-case %2) (string/lower-case %1))
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
  

(defn -main
  "Takes as input a file with a set of records and outputs the set of records sorted"
  ([file]
   (-main file nil))
  ([file column-to-sort]
   (let [file-contents (slurp file)
         formatted-file-contents (determine-and-format file-contents)
         sorted-columns (sort-by-column formatted-file-contents column-to-sort)]
     (show-output sorted-columns column-to-sort))))


(comment
  (-main "./pipes.txt" "1")
  (-main "./commas.csv")
  (-main "./spaces.txt"))
