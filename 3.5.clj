(def asym-hobbit-body-parts [
    {:name "head" :size 3}
    {:name "left-eye" :size 1}
    {:name "left-ear" :size 1}
    {:name "mouth" :size 1}
    {:name "nose" :size 1}
    {:name "neck" :size 2}
    {:name "left-shoulder" :size 3}
    {:name "left-upper-arm" :size 3}
    {:name "chest" :size 10}
    {:name "back" :size 10}
    {:name "left-forearm" :size 3}
    {:name "abdomen" :size 6}
    {:name "left-kidney" :size 1}
    {:name "left-hand" :size 2}
    {:name "left-knee" :size 2}
    {:name "left-thigh" :size 4}
    {:name "left-lower-leg" :size 3}
    {:name "left-achilles" :size 1}
    {:name "left-foot" :size 2}])

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

; este es un comentario

(defn symmetrize-body-parts
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
          (into final-body-parts
                (set [part (matching-part part)])))))))

(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))




(def asym-alien-body-parts [
    {:name "head" :size 3}
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


(defn matching-alien-parts
  [part] 
  [
  {:name (clojure.string/replace (:name part) #"^1-" "2-")
   :size (:size part)}
  {:name (clojure.string/replace (:name part) #"^1-" "3-")
   :size (:size part)}
  {:name (clojure.string/replace (:name part) #"^1-" "4-")
   :size (:size part)}
  {:name (clojure.string/replace (:name part) #"^1-" "5-")
   :size (:size part)}])

(defn symmetrize-alien-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set (concat #{part} (set (matching-alien-parts part))))))
          []
          asym-body-parts))





;; vamos por el ejercicio 6...

; lo primero es poder generar matching body parts pasando n como argumento, el nro de partes q hay que 'simetrizar'

(defn n-matching-alien-parts
  [n part] 
  (loop [i 2
         nmax n
         symparts []]
  (if (> i nmax)
    symparts
    (

  [{:name (clojure.string/replace (:name part) #"^1-" (str i "-"))
    :size (:size part)}

