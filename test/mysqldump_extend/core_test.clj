(ns mysqldump-extend.core-test
  (:require [clojure.test :refer :all]
            [mysqldump-extend.core :refer :all]))

(use 'clojure.data)

(deftest a-test
  (testing "Diffs extended-format and result of processing."
    (let [diff-result (diff
     (process-script-file "resources/test_data.sql")
     (process-script-file "resources/test_data_extended.sql"))]
      (is (= 0
          (count (first diff-result))
          (count (second diff-result)))))))
