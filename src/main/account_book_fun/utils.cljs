(ns account-book-fun.utils)

(defn event-value [e ]
  (-> e
      (aget "target")
      (aget "value")
      ) )

(defn in-date?
  "check whether the date is in given year and month"
  [ ^js/Date date year month]
  (let [_year (.getFullYear date)
        _month (.getMonth date)]
    (and (= year _year) (= month _month))) )

(defn str2int
  ([s]
   (str2int s 10))
  ([s base]
   (js/parseInt s base) ))