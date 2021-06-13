(ns account-book-fun.parser
  (:require [clojure.string :as s]))

(defn- split
  ([str]
   (split str {}))

  ([str {:keys [delimiter] :or {delimiter ","}}]
   (s/split str (re-pattern delimiter))))

;; (defn parse-category-data
;;   [rows]
;;   (let [header (first rows)
;;         keys (split header)
;;         data (rest rows)]
;;     (map data #(zipmap keys  (split %)))))


(defn parse-csv
  ([str]
   (parse-csv str {}))

  ([str transformers]
   ;; TODO: do the transformers
   (let [rows (s/split str #"\n")
         header (first rows)
         keys (split header)
         data (rest rows)]
     (->> (map #(zipmap keys (split %)) data)
          (map #(->> %
                     (map (fn [[k v]] (if-let [t (get transformers k)]
                                        [k (t v)]
                                        [k v])))
                     (into {})))
          ))))
