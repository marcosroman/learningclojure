; 4. in chapter 5 you created a series of functions (c-int,c-str,c-dex0 to read an RPG character's attributes. write a macro that defines an arbitrary number of attributes. write a macro that defines an arbitrary number of attribute-retrieving functions using one macro call.

  ;;;
  ;;; here's how you would call it:
  ;;; ---
  ;;(defattrs c-int :intelligence
  ;;          c-str :strength
  ;;          c-dex :dexterity)
  ;;; ---
  ;;
  ;;; cf:
  ;;;(def character
  ;;;  {:name "Smooches McCutes"
  ;;;   :attributes {:intelligence 10
  ;;;                :strength 4
  ;;;                :dexterity 5}})
  ;;;(def c-int (comp :intelligence :attributes))
  ;;;(def c-str (comp :strength :attributes))
  ;;;(def c-dex (comp :dexterity :attributes))
  ;;
  ;;;this works, for 2 args:
  ;;;;(defmacro defattrs
  ;;;;  ;([func-name label]
  ;;;;  ; `(def ~func-name (comp ~label :attributes)))
  ;;;;  ([func-name label]
  ;;;;   `(def ~func-name (comp ~label :attributes)))
  ;;;;
  ;;;;  ([func-name label & others]
  ;;;;   (do
  ;;;;   `(def ~func-name (comp ~label :attributes))
  ;;;;   `(defattrs ~@others))
  ;;;;   )
  ;;;;)
  ;;
  ;;(defmacro defattrs-2
  ;;  ([func-name label]
  ;;   `(def ~func-name (comp ~label :attributes))))
  ;;
  ;;; lo que podemos hacer es un do y adentro un map y a los argumentos vamos a agruparles de a dos: el primero el func-name y el 2do el label
  ;;
  ;;
  ;;;lo que yo quiero es que devuelva
  ;;;
  ;;;(do
  ;;;  (def f-1 (comp l-1 :attributes))
  ;;;  ; ...
  ;;;  (def f-n (comp f-n :attributes)))
  ;;;
  ;;;
  ;;;lo de adentro es 
  ;;;(def f (comp l :attributes))
  ;;;
  ;;;
  ;;;usaria entonces
  ;;;~@(map #(def %1 (comp %2 :attributes))
  ;;;     (partition 2 args)
  ;;
  ;;(defmacro defattrs
  ;;  [ & args ]
  ;;   `(do
  ;;      ~@(map #(def %1 (comp %2 :attributes))
  ;;             (partition 2 args))))
  ;;
  ;;; esto no esta funcionando
  ;;
  ;;
  ;;; quizas tengo que hacer esto con 2 funciones?
  ;;
  ;;; let's then do a macro for the (def ..) part
  ;;
  ;;(defmacro def-in
  ;;  [func-name label]
  ;;  `(def ~func-name (comp ~label :attributes)))
  ;;
  ;;; oh yes
  ;;
  ;;(defmacro defattrs
  ;;  [ & args ]
  ;;   `(do
  ;;      ~@(map #(`(apply def-in %)))
  ;;             (partition 2 ~args)))
  ;;
  ;;; todavia no funciona
  ;;
  ;;; pero por aca va... tengo que usar defattrs-2, de alguna forma
  ;;
  ;;(defmacro defattrs
  ;;  [ & args ]
  ;;   `(do
  ;;      ~@(map #(apply defattrs-2 %)
  ;;             (partition 2 ~args))))
  ;;
  ;;
  ;;
  ;;; voy a probar entonces tomar cada 2
  ;;(defmacro xdefattrs
  ;;  [ & args ]
  ;;  `(partition 2 ~args))
  ;;
  ;;
  ;;(defn defattrs-2
  ;;  ([func-name label]
  ;;   `(def ~func-name (comp ~label :attributes))))
  ;;
  ;;(defmacro defattrs
  ;;  [ & args ]
  ;;   `(do
  ;;      ~@(map #(apply fdefattrs-2 %)
  ;;             (partition 2 ~args))))
  ;;
  ;;
  ;;; capaz lo que tnego que hacer es pasar la lista de defs... como creo esa lista???? (vamos ahora por ALGUNA solucion, en vez de la mejor)
  ;;
  ;;
  ;;(defmacro doseq-defattrs
  ;;  [macroname & args]
  ;;  `(do
  ;;    (map (fn [arg1 arg2] (list macroname arg1 arg2)) (partition 2 args))))
  ;;
  ;;
  ;;
  ;;
  ;;
  ;;
  ;;
  ;;
  ;;
  ;;; como hago que salga?
  ;;
  ;;lo que quiero que imprima es 
  ;;
  ;;
  ;;(do
  ;;    (def c1
  ;;
  ;;
  ;;
  ;;
  ;;
  ;;
  ;;(defn defattrs-2
  ;;  ([func-name label]
  ;;   `(def ~func-name (comp ~label :attributes))))
  ;;
  ;;
  ;;(defmacro defattrs
  ;;  [ & args ]
  ;;  `(let [two-args# ~(partition 2 args)]
  ;;     (do
  ;;       ~@(map #(list apply defattrs-2 %))
  ;;       two-args#)))
  ;;
  ;;
  ;;(defmacro defattrs-seq
  ;;  [ args ] ; recibe '( (f1 l1) (f2 l2) ... (fn ln) )
  ;;  `(do
  ;;     ~@(map #(list defattrs-2 %) args)))
  ;;
  ;;
  ;;
  ;;(defattrs c-int :intelligence c-str :strength)
  ;;
  ;;
  ;;
  ;;
  ;;
  ;;(defmacro defattrs
  ;;  [ & args ]
  ;;   `(do
  ;;      ~@(map #(list apply defattrs-2 %))
  ;;             (partition 2 args)))
  ;;
  ;;
  ;;; hagamos de la forma recursiva... sin map y todo eso
  ;;
  ;;(defmarcro defattrs-ins
  ;;  ([]
  ;;   
  ;;   )
  ;;  ([func-name label]
  ;;   
  ;;   )
  ;;  ([func-name label & others])
  ;;)
  ;;
  ;;; se me hace mas quilombo...
  ;;
  ;;
  ;;
  ;;; aprendamos del ejemplo del libro
  ;;(defmacro doseq-macro
  ;;  [macroname & args]
  ;;  `(do
  ;;       ~@(map (fn [arg] (list macroname arg)) args)))


