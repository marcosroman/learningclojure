; 2. Implement the comp function.

(defn two-comp [f g]
  (fn [& args] (f (apply g args))))

(defn my-comp [& functions] 
  (recur two-comp functions))

; --------

; my guess is that one would recursively call the two-comp function, that composes two functions (there was an example in the book)

; how would i do that?

;; (comp f g)
; composes two functions... first g, that takes n-variables... (g v1 v2 ... vn) and then f
; so: (f (g v1 v2 ...vn)) == (two-comp f g)

(defn two-comp [f g]
  (fn [& args] (f (apply g args))))

;(defn my-comp [& functions]
;  (recur two-comp (first functions) (rest functions)))

; -> esto que sigue tampoco funciona...
;(defn recursive-comp [& functions]
;  (two-comp (first functions) (recursive-comp (rest functions))))

; necesito (f1 (f2 (f2 (f3 (... (apply fn args) ...)))))

; PROBA COSAS CARAJO, HACE, NO PODES ESTAR PENSANDO NOMAS!!!!!
(defn my-comp [& functions]
  (loop [remaining-functions (drop-last functions)
         composite-function (take-last functions)]
    (if (nil? remaining-functions)
      composite-function
      (do
        ;(print (str remaining-functions))
        (recur ;[remaining-functions (drop-last remaining-functions)
               (drop-last remaining-functions) ;remaining-functions
               ;composite-function (two-comp
               ;                     (take-last 1 remaining-functions)
               ;                     (composite-function))]))))
               (two-comp
                 (take-last 1 remaining-functions)
                 (composite-function)))))))
  
; ahora veo la implementacion del libro de 'reduce' y la forma de lo que estoy escribiendo es... MUY parecida... no sera que con reduce se puede?

;no puedo hacer
;(reduce two-comp functions)
;????

; jajaja parece que si funciona!!!
(defn my-comp [& functions] 
  (recur two-comp functions))
; yup! anda re bien. los args hay que pasar explotados, no dentro de un seq

