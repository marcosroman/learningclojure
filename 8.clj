(def order-details
  {:name "Marcos"
   :mail "mroman.com.py"})

; map que contiene: key value=[error-message_1 validating-function_1 ... error-message_n validating-function_n]
(def order-details-validations
  {:name
   ["Enter name" not-empty]

   :mail
   ["Enter mail" not-empty

    "That's not a mail address"
    ;#(or (empty? %) (= 1 (count (re-seq #"@" %))))]})
    #(or (empty? %) (re-seq #"@" %))]})


; now we gonna write the validation function...
; this will be decomposed in two functions:
;   one to apply validation to a single field
;   the other to add all error messages (if any)

; first one (to validate a single field)
(defn error-messages-for
  "returns a seq of error messages"
  [to-validate message-validator-pairs]
  ; (second %) would be the validating function, to-validate the message
  (map first (filter #(not ((second %) to-validate)) 
                     (partition 2 message-validator-pairs))))


; example
(error-messages-for
   "noesunmail"
   ["Enter mail" not-empty

    "That's not a mail address"
    ;#(or (empty? %) (= 1 (count (re-seq #"@" %))))]})
    #(or (empty? %) (re-seq #"@" %))]
   )
;; -> ("That's not a mail address")


; accumulates error messages in a map
; 'validations' is the map with keys and associated 2n vectors
;                               (fieldnames)        (validation-check-groups)
;                                                   each pair with err msj and
;                                                   validating function
; 'to-validate' is the map with fields and their values to be validated
(defn validate
  "Returns map with a vector of errors for each key"
  [to-validate validations]
  (reduce (fn [errors validation]
            (let [[fieldname validation-check-groups] validation
                  value (get to-validate fieldname)
                  error-messages (error-messages-for value validation-check-groups)]
              (if (empty? error-messages)
                errors
                (assoc errors fieldname error-messages))))
          {}
          validations))

