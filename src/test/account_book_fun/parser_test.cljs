(ns account-book-fun.parser-test
  (:require [cljs.test :refer (deftest is)]
            [shadow.resource :refer-macros [inline]]
            [account-book-fun.parser :refer [parse-csv]]
            ["fs" :as fs]
            ["path" :as path]))

(deftest parser-csv-test
  ;; note that the js/__dirname in repl would be the project root
  (let [bill-data (inline "./data/bills.csv")
        bills (parse-csv bill-data {"time" #(-> % (js/parseInt 10) js/Date.)})]
    (println (first bills))
    (is (not (nil? (get (first bills) "type"))))))