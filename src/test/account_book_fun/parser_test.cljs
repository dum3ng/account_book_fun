(ns account-book-fun.parser-test
  (:require [cljs.test :refer (deftest  is)]
            [account-book-fun.parser :refer [parse-csv]]
            ["fs" :as fs]
            ["path" :as path]))

(deftest parser-csv-test
  ;; note that the js/__dirname in repl would be the project root
  (let [pth (path/resolve js/__dirname "src/main.account_book_fun/data/bill.csv")
        bill-data (fs/readFileSync pth)
        bills (parse-csv bill-data)]
    (println (first bills))
    (is (not (nil? (get (first bills) "type"))))))