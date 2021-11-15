; 2. You saw that and is implemented as a macro. Implement or as a macro.

;(defmacro and
;  ([] true)
;  ([x] x)
;  ([x & next]
;   `(let [and# ~x]
;      (if and# (and ~@next) and#))))

(defmacro or
  ([] true)
  ([x] x)
  ([x & next]
   `(let [or# ~x]
      (if or# or# (or ~@next)))))

