(ns practice.core-test
  (:require [clojure.test :refer [deftest is testing run-all-tests]]
            [practice.core :refer [output-1-comparator determine-and-format]]
            [practice.util :refer [format-dob convert-to-map format-and-replace-dob]]))

(deftest a-test
  (testing "test"
    (is (= 1 1))))

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
    
    (is (= (convert-to-map '(["last" "first" "email" "color" "02/03/2000"]))
           data-model)) 
    (is (not (seq (convert-to-map nil))))
    
    (is (= (format-and-replace-dob data-model) 
           '({:last-name "last", :first-name "first", :email "email", :fav-color "color", :dob "2/03/2000"})))))

(deftest core
  (testing "functions in core ns"
    (is (= (output-1-comparator ["A" "A"] ["A" "B"])
           -1))
    (is (= (output-1-comparator ["A" "A"] ["B" "B"])
           1))
    (is (thrown? java.lang.AssertionError
                 (determine-and-format nil)))))

(comment
  (run-all-tests))
