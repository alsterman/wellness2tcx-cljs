(ns wellness2tcx-cljs.utils)

(defn download
  [filename, text]
  (as-> js/document $
        (.createElement $ "a")
        (do (.setAttribute $ "href" (str "data:text/plain;charset=utf-8," (js/encodeURIComponent text)))
            (.setAttribute $ "download" filename)
            (set! (.-style.display $) "none")
            (js/document.body.appendChild $)
            (.click $)
            (js/document.body.removeChild $)
            ))
  )