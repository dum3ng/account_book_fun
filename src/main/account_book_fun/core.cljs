(ns account-book-fun.core
  (:require
    [reitit.frontend.easy :as rfe]
    [reitit.frontend :as rf]
    [reitit.coercion.spec :as rss]
    [reagent.core :as r]
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [account-book-fun.events :as events]
   [account-book-fun.views :as views]
   [account-book-fun.config :as config]
   ))

(defonce match (r/atom nil))

(defn current-page []
  (if @match
    (let [view (-> @match :data :view)]
      [view @match])))

(defn dev-setup [ ]
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (rfe/start!
    (rf/router views/routes {:data {:coercion rss/coercion}})
    (fn [m] (reset! match m))
    {:use-fragment true})
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [current-page] root-el)))

(defn init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
