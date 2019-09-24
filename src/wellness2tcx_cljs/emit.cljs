(ns wellness2tcx-cljs.emit
  (:require [clojure.string :refer [join]]))

(defn emit-open-tag
  [tag attrs]
  (str "<"
       (name tag)
       (if attrs
         (str " "
              (join " " (map (fn [x] (str (first x) "=\"" (second x) "\"")) attrs)))
         "")
       ">\n"))

(defn emit-close-tag
  [tag]
  (str "</"
       (name tag)
       ">\n"))

(declare emit-content)

(defn emit-contents
  [contents]
  (join (map emit-content contents)))

(defn emit-content
  [content]
  (if (map? content)
    (str (emit-open-tag (get content :tag) (get content :attrs))
         (emit-contents (get content :content))
         (emit-close-tag (get content :tag)))
    (str content "\n")))

(defn emit-xml-obj
  [xml-obj]
  (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
       (emit-content xml-obj))
  )

;(println(emit-content xml-obj))

;(defn xml-obj->str
;  [xml-obj]
;  ;(str "<" (name (get xml-obj :tag))
;  ;     (if (contains? xml-obj :attrs)
;  ;       (join " " (map (fn [x] (str (first x) "=\"" (second x) "\"")) (get xml-obj :attrs)))
;  ;       ;(doseq [kv (get xml-obj :attrs)]
;  ;       ;  (str (first kv) "=\"" (second kv) "\"")
;  ;       ;  )
;  ;       ".") "hi"
;  ;     )
;  (println (str (emit-open-tag (get xml-obj :tag) (get xml-obj :attrs))
;                (emit-close-tag (get xml-obj :tag))
;       ))
;  )

;(let [xml-obj {:tag :TrainingCenterDatabase
;               :attrs {"xsi:schemaLocation" "http://www.garmin.com/xmlschemas/TrainingCenterDatabase/v2  http://www.garmin.com/xmlschemas/TrainingCenterDatabasev2.xsd"}
;               :content [{:tag :Activities
;                          :content ["2019-01-01"]}]}]
;
;  (-> (xml-obj->str xml-obj)
;      )
;  )

;(def xml-obj {:tag :TrainingCenterDatabase
;              :attrs {"xsi:schemaLocation" "http://www.garmin.com/xmlschemas/TrainingCenterDatabase/v2  http://www.garmin.com/xmlschemas/TrainingCenterDatabasev2.xsd"}
;              :content [{:tag :Activities
;                         :content ["2019-01-01"]}]})

;(def attrs (get xml-obj :attrs))
