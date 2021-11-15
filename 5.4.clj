;4. Look up and use the update-in function.

  ;;clojure.core/update-in
  ;;([m ks f & args])
  ;;  'Updates' a value in a nested associative structure, where ks is a
  ;;  sequence of keys and f is a function that will take the old value
  ;;  and any supplied args and return the new value, and returns a new
  ;;  nested structure.  If any levels do not exist, hash-maps will be
  ;;  created.

(def my-m (assoc-in {} [:k1 :k2 :k3] 1))

(update-in my-m [:k1 :k2 :k3] inc)

;-> {:k1 {:k2 {:k3 2}}}

