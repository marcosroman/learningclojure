; 4. in chapter 5 you created a series of functions (c-int,c-str,c-dex0 to read an RPG character's attributes. write a macro that defines an arbitrary number of attributes. write a macro that defines an arbitrary number of attribute-retrieving functions using one macro call.
;
; here's how you would call it:
; ---
;(defattrs c-int :intelligence c-str :strength c-dex :dexterity)
; ---


  ;; for 2 args only, this is straightforward
  ;(defmacro defattrs-2
  ;  ([func-name label]
  ;   `(def ~func-name (comp ~label :attributes))))
  ;; => (macroexpand '(defattrs-2 c-int :intelligence))
  ;;; -> (def c-int (clojure.core/comp :intelligence :attributes))
  ;
  ;
  ;; alright... what about more args?
  ;; i want the macro to return
  ;; (do
  ;;   (def f-1 (comp l-1 :attributes))
  ;;   ; ...
  ;;   (def f-n (comp l-n :attributes)))
  ;;
  ;
  ;; so at first i tried
  ;(defmacro defattrs
  ;  [ & args ]
  ;   `(do
  ;      ~@(map #(def %1 (comp %2 :attributes))
  ;             (partition 2 args))))
  ;; but 
  ;; => (macroexpand '(defattrs c-int :intelligence c-str :strength c-dex :dexterity))
  ;;; -> Error printing return value (ArityException) at clojure.lang.AFn/throwArity (AFn.java:429).
  ;;;    Wrong number of args (1) passed to: user/defattrs/fn--266
  ;
  ;; why? idk. the error message is weird, but i can see the 'wrong number of args' part... 
  ;  ; let's try some basic version to understand why it doesn't work:
  ;  (defmacro try1
  ;    [ & args ]
  ;    `(do (map #(println %) ~@args)))
  ;  ; => (try1  '("hola" "chau"))
  ;  ;; -> (hola
  ;  ;;     chau
  ;  ;;     nil nil)
  ;
  ;  (defmacro try2
  ;    [ args ]
  ;    `(do (map #(println %) ~args)))
  ;  ; esto funciona si hago
  ;  ; => (try2 '("hola" "chau"))
  ;  ;; -> (hola
  ;  ;;     chau
  ;  ;;     nil nil)
  ; 
  ;; what about:
  ;(defmacro defattrs
  ;  [ & args ]
  ;  `(do
  ;     ~@(map #(list apply defattrs-2 %)
  ;       (partition 2 args))))
  ;;; -> Syntax error compiling at (REPL:4:14).
  ;;;    Can't take value of a macro: #'user/defattrs-2
  ;
  ;; entonces como hago: primero genero una lista de 
  ;; '((def f-1 (comp l-1 :attributes))
  ;;   ...
  ;;   (def f-n (comp l-n :attributes)))
  ;; ?
  ;
  ;(defn fdefattrs-2
  ;  [func-name label]
  ;   (list 'def func-name (list 'comp label :attributes)))
  ;; esto funciona pero tengo que escribir como
  ;; => (fdefattrs-2 'a :a)
  ;; de lo contrario se evalua a, tiene que ir quoted para que no pase
  ;
  ;; entonces lo que me esta faltando es tener una mejor idea de como se evaluan los macros, en que orden, como se da...
  ;
  ;
  ;; como genero un macro que imprima eso?
  ;
  ;(defmacro x
  ;  [ & args ]
  ;  (println ~@args))
  ;
  ;
  ;
  ;(recur #(apply defattr-2 %) '() 
  ;
  ;
  ;; hagamos un test simple de macros
  ;(defmacro testdef
  ;  [a b]
  ;  `(defattrs-2 ~a ~b))
  ;; that works fine
  ;
  ;
  ;;; ok, releyendo lo de macros en el libro, entendi un poco mejor... SI puedo usar funciones... tengo que pensar nomas en que primero se evaluan los macros, luego funciones... como seria entonces:
  ;
  ;(defn fdefattrs-2
  ;  [func-name label]
  ;   `(def ~func-name (comp ~label :attributes)))
  ;
  ;; lo cual devuelve
  ;;=> (fdefattrs-2 'a :a)
  ;;; -> (def a (clojure.core/comp :a :attributes))
  ;
  ;; (notar que 'a tiene que ir quoted)
  ;
  ;; entonces si hago un map
  ;(map #(apply #(fdefattrs-2 '%1 %2) %)
  ;     (partition 2 '(a :a b :b)))
  ;
  ;
  ;; pero no funciona asi...
  ;; igual fdefattrs-2 esta bastante bien... lo unico 'malo' es que requiere que 
  ;
  ;
  ;
  ;(defn fdefattrs-3
  ;  [func-name label]
  ;   (list 'def ('quote func-name) (list 'comp label :attributes)))
  ;
  ;
  ;
  ;; copiemos el ejemplo del libro
  ;
  ;(defmacro doseq-macro
  ;  [macroname & args]
  ;  `(do
  ;       ~@(map (fn [arg] (list macroname arg)) args)))
  ;
  ;(defmacro defattrs
  ;  [& args]
  ;  (let [args-2part (partition 2 args)]
  ;    `(do
  ;         ~@(map (fn [arg] (list `defattrs-2 (first arg) (second arg))) args-2part))))
  ;
  ;; => (macroexpand '(defattrs a :a b :b c :c))
  ;;; -> (do 
  ;;;      (user/defattrs-2 a :a)
  ;;;      (user/defattrs-2 b :b)
  ;;;      (user/defattrs-2 c :c))
  ;
  ;;---

; solucion:

(defmacro defattrs-2
  ([func-name label]
   `(def ~func-name (comp ~label :attributes))))

(defmacro defattrs
  [& args]
  (let [args-2part (partition 2 args)]
    `(do
         ~@(map (fn [arg] (list `defattrs-2 (first arg) (second arg))) args-2part))))


