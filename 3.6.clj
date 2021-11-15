(def asym-alien-body-parts
  [{:name "head" :size 3}
   {:name "1-eye" :size 1}
   {:name "1-ear" :size 1}
   {:name "mouth" :size 1}
   {:name "nose" :size 1}
   {:name "neck" :size 2}
   {:name "1-shoulder" :size 3}
   {:name "1-upper-arm" :size 3}
   {:name "chest" :size 10}
   {:name "back" :size 10}
   {:name "1-forearm" :size 3}
   {:name "abdomen" :size 6}
   {:name "1-kidney" :size 1}
   {:name "1-hand" :size 2}
   {:name "1-knee" :size 2}
   {:name "1-thigh" :size 4}
   {:name "1-lower-leg" :size 3}
   {:name "1-achilles" :size 1}
   {:name "1-foot" :size 2}])

;; vamos por el ejercicio 6...

; lo que tengo que hacer es extender la funcion matching-alien-parts para que simetrice hasta n partes, donde n es parametro 

(defn n-matching-alien-parts
  [n part] 
  (loop [i 2
         symparts []]
  (if (> i n)
    symparts
    (recur (inc i)
           (into symparts
                 [{:name (clojure.string/replace (:name part) #"^1-" (str i "-"))
                   :size (:size part)}])))))


(defn n-symmetrize-alien-body-parts
  "Expects a seq of maps that have a :name and :size"
  [n asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set (concat #{part} (set (n-matching-alien-parts n part))))))
          []
          asym-body-parts))


