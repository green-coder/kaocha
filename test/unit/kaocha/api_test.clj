(ns kaocha.api-test
  (:require [clojure.test :refer :all]
            [kaocha.api :refer :all]
            [kaocha.test-util :refer [with-out-err]]))

(deftest run-test
  (testing "allows API usage"
    (let [config {:kaocha/tests [{:kaocha.testable/id        :unit
                                  :kaocha.testable/type      :kaocha.type/suite
                                  :kaocha.suite/test-paths   ["fixtures/a-tests"]
                                  :kaocha.suite/source-paths ["src"]
                                  :kaocha.suite/ns-patterns  ["-test$"]}]}]
      (is (match?
           {:kaocha.result/tests
            [{:kaocha.testable/id        :unit
              :kaocha.testable/type      :kaocha.type/suite
              :kaocha.suite/test-paths   ["fixtures/a-tests"]
              :kaocha.suite/source-paths ["src"]
              :kaocha.suite/ns-patterns  ["-test$"]
              :kaocha.result/tests
              [{:kaocha.testable/type :kaocha.type/ns
                :kaocha.testable/id   :foo.bar-test
                :kaocha.result/tests
                [{:kaocha.testable/type :kaocha.type/var
                  :kaocha.testable/id   :foo.bar-test/a-test
                  :kaocha.var/name      'foo.bar-test/a-test
                  :kaocha.result/count  1
                  :kaocha.result/pass   1
                  :kaocha.result/error  0
                  :kaocha.result/fail   0}]}]}]}
           (:result (with-out-err (run config))))))))