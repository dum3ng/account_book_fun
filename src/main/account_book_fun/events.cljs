(ns account-book-fun.events
  (:require
   [re-frame.core :as re-frame]
   [account-book-fun.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))
