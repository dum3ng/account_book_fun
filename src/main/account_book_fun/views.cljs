(ns account-book-fun.views
  (:require
    [reagent.core :as r]
    [re-frame.core :as rf]
    [account-book-fun.subs :as subs]
    [account-book-fun.events :as e]
    [account-book-fun.utils :as u]
    ))

(declare app date-selector)

(defn main-panel []
  (let [name (rf/subscribe [::subs/name])
        editing (rf/subscribe [::subs/editing])]
    [:div
     [:h1
      "Hello from " @name]
     [:label "input here:"]
     [:input {:value @editing :on-change #(do (println %) (rf/dispatch [::e/set-editing (-> % (aget "target") .-value)]))}]
     [:p "and the input value is: " @editing]
     [date-selector]
     [app]
     ]))

(defn app []
  (r/create-class {
                   :display-name        "app"
                   :component-did-mount (fn [_] (rf/dispatch [::e/fetch-data]))
                   :reagent-render      (fn []
                                          (let [bills (rf/subscribe [::subs/current-bills])]
                                            [:div [:h2 "app bills"]
                                             [:ul
                                              (map (fn [bill] [:li {:key (str (get bill "category") (get bill "type"))} (str bill)]) @bills)]]))
                   }))

(def months (range 12))
(def years (map #(+ % 2015) (range 10)))

(defn date-selector []
  (let [current (rf/subscribe [::subs/current])]
    [:div
     [:select {:on-change #(rf/dispatch [::e/set-current-year (-> % (u/event-value ) (u/str2int))])
               :value     (:year (or @current {}))} (map (fn [y] [:option {:key y :value y} y]) years)]
     [:select {:on-change #(rf/dispatch [::e/set-current-month (-> % (u/event-value ) u/str2int)])
               :value     (:month (or @current {}))} (map (fn [m] [:option {:key m :value m} (str (inc m) "月")]) months)]
     ]))


(defn add-bill
  []
  [:h2 "add bill here"])

(def routes
  [["/"
    {:name "home"
     :view main-panel}]
   ["/add"
    {:name "add-bill"
     :view add-bill}]])