(ns account-book-fun.events
  (:require
    [shadow.resource :refer-macros [inline]]
    [re-frame.core :as rf]
    [account-book-fun.parser :as parser]
    [account-book-fun.db :as db]))

(rf/reg-event-db
  ::initialize-db
  (fn [_ _]
    db/default-db))

(rf/reg-event-db
  ::set-editing
  (fn [db [_ txt]]
    (assoc db :editing txt)))

(rf/reg-event-db
  ::fetch-data
  (fn [db _]
    (let [categories (parser/parse-csv (inline "./data/categories.csv"))
          bills (parser/parse-csv (inline "./data/bill.csv") {"time" #(-> % (js/parseInt 10)
                                                                          (js/Date. ))})]
      (-> db
          (assoc-in [:data :categories] categories)
          (assoc-in [:data :bills] bills)))
    ))

(rf/reg-event-db
  ::set-current-year
  (fn [db [_ year]]
    (-> db
        (assoc-in [:current :year] year)
        )))

(rf/reg-event-db
  ::set-current-month
  (fn [db [_ month]]
    (-> db
        (assoc-in [:current :month] month)
        )))