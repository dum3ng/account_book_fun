(ns account-book-fun.subs
  (:require
    [re-frame.core :as re-frame]
    [account-book-fun.utils :as u]))

(re-frame/reg-sub
  ::name
  (fn [db]
    (:name db)))

(re-frame/reg-sub
  ::editing
  (fn [db]
    (:editing db)))

(re-frame/reg-sub
  ::categories
  (fn [db]
    (-> db :data :categories)))

(re-frame/reg-sub
  ::bills
  (fn [db]
    (-> db :data :bills)))

(re-frame/reg-sub
  ::current-bills
  (fn [qv] [(re-frame/subscribe [::bills])
            (re-frame/subscribe [::current])])
  (fn [[bills current] qv]
    (filter #(u/in-date? (get % "time") (:year current) (:month current)) bills) ))

(re-frame/reg-sub
  ;; current date
  ::current
  (fn [db] (:current db)))