(ns wellness2tcx-cljs.helper)

(defn to-unix-time
  [yyyy-MM-dd-string hh:mm:ss-string]
  #?(:clj (let [SDF (new java.text.SimpleDateFormat "yyyy-MM-dd'T'HH:mm:ss'Z'")
                inputstr (str yyyy-MM-dd-string "T" hh:mm:ss-string "Z")]
            (as-> (. SDF parse inputstr) $
                  (. $ getTime)))
     :cljs ((fn [] (.parse js/Date (str yyyy-MM-dd-string " " hh:mm:ss-string))))
     )
  )
(to-unix-time "2019-01-01" "17:10:10")

(defn timestamp
  [UNIX-time]
  #?(:clj
     (let [SDF (new java.text.SimpleDateFormat "yyyy-MM-dd'T'HH:mm:ss'Z'")]
       (.format SDF (new java.util.Date UNIX-time)))
     :cljs
     (as-> (new js/Date UNIX-time) $
           (.toISOString $)
           (subs $ 0 19)
           (str $ "Z")
           )))

(timestamp (to-unix-time "2019-01-01" "17:10:10"))

(defn mean
  [coll]
  (/ (reduce + coll) (count coll)))

(mean [1 2 3 4])

(defn date-string->yyyy-MM-dd
  "Currently string input on form yyyy-MM-dd and dd/MM/yyyy"
  [date-string]
  {:pre [(string? date-string)
         (= (count date-string) 10)]
   :post [(string? %)
          (re-matches #"....-..-.." %)]}
  (if (re-matches #"../../...." date-string)
    (let [dd (subs date-string 0 2)
          MM (subs date-string 3 5)
          yyyy (subs date-string 6)]
      (str yyyy "-" MM "-" dd))
    date-string))

(date-string->yyyy-MM-dd "2017-01-01")
(date-string->yyyy-MM-dd "30/01/2017")