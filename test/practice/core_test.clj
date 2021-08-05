(ns practice.core-test
  (:require [clojure.test :refer [deftest is testing run-all-tests]]
            [practice.file-content :refer [output-1-comparator parse-str-delimeter]]
            [practice.util :refer [format-dob convert-to-map format-and-replace-dob]]))

(def commas-str "last, first, email, color, 02/03/2000")
(def pipes-str "last | first | email | color | 02/03/2000")
(def spaces-str "last first email color 02/03/2000")
(def invalid-delimeter-str "last-first-email-color-02/03/2000")
(def seq-vec '(["last" "first" "email" "color" "02/03/2000"]))

(def data-model '({:last-name "last" 
                   :first-name "first" 
                   :email "email" 
                   :fav-color "color" 
                   :dob "02/03/2000"}))

(deftest utils
  (testing "util functions"
    (is (= (format-dob "01/20/1999") "1/20/1999"))
    (is (thrown? java.lang.AssertionError 
                 (format-dob "invalid")))
    
    (is (= (convert-to-map seq-vec)
           data-model)) 
    (is (not (seq (convert-to-map nil))))
    (is (not (seq (convert-to-map []))))
    
    (is (= (format-and-replace-dob data-model) 
           '({:last-name "last", :first-name "first", :email "email", :fav-color "color", :dob "2/03/2000"})))))

(deftest file-contents
  (testing "functions in file-content ns"
    (is (= (output-1-comparator ["A" "A"] ["A" "B"])
           -1))
    (is (= (output-1-comparator ["A" "A"] ["B" "B"])
           1))
    (is (thrown? java.lang.AssertionError
                 (parse-str-delimeter nil)))
    (is (= (parse-str-delimeter commas-str) 
           seq-vec))
    (is (= (parse-str-delimeter pipes-str) 
           seq-vec))
    (is (= (parse-str-delimeter spaces-str) 
           seq-vec))
    (is (= (parse-str-delimeter invalid-delimeter-str) 
           []))))

(comment
  (run-all-tests))
