(ns mysqldump-extend.core-test
  (:require [clojure.test :refer :all]
            [mysqldump-extend.core :refer :all]))

(use 'clojure.data)

(deftest a-test
  (testing "Diffs extended-format and result of processing."
    (let [diff-result (diff
     (process-script-file "resources/test_data.sql")
     (str (slurp "resources/test_data_extended.sql")))]
      (println (count (nth diff-result 0)))
      (println (count (nth diff-result 1)))
      (println (count (nth diff-result 2))))
    (is (= 0 1))))
