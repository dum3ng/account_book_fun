(ns account-book-fun.views
  (:require
   [re-frame.core :as re-frame]
   [account-book-fun.subs :as subs]
   ))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1
      "Hello from " @name]
     ]))
