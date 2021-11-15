; 1. write the macro 'when-valid', so that it behaves similarly to when.

  ;;; call example:
  ;;(when-valid order-details order-details-validation
  ;;            (println "Success!")
  ;;            (render :success))
  ;
  ;; we have the 'when' macro already:
  ;
  ;;(defmacro when
  ;;  [test & body]
  ;;  (list 'if test (cons 'do body)))

(defmacro when-valid
  [order-details order-details-validation & body]
  `(if (empty? (validate ~order-details ~order-details-validation))
     (do ~@body)))


