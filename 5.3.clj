; Implement the assoc-in function.
; (Hint: use the assoc function and define its parameters as [m [k & ks] v])

  ;;; que era assoc, para empezar?
  ;;  ; clojure.core/assoc
  ;;  ; ([map key val] [map key val & kvs])
  ;;  ;   assoc[iate]. When applied to a map, returns a new map of the
  ;;  ;     same (hashed/sorted) type, that contains the mapping of key(s) to
  ;;  ;     val(s). When applied to a vector, returns a new vector that
  ;;  ;     contains val at index. Note - index must be <= (count vector).
  ;; 
  ;;; y assoc-in?
  ;;  ; clojure.core/assoc-in
  ;;  ; ([m [k & ks] v])
  ;;  ;   Associates a value in a nested associative structure, where ks is a
  ;;  ;   sequence of keys and v is the new value and returns a new nested structure.
  ;;  ;   If any levels do not exist, hash-maps will be created.
  ;;
  ;;
  ;;; probemos... ahora voy entendiendo
  ;;; m es el mapa, [k & ks] es la lista de keys, v es el valor
  ;;; si la lista de keys es [:k1 :k2 :k3], lo que hace es agregar el mapa m
  ;;; es valor {:k1 {:k2 {:k3 v}}}
  ;;; que puedo obtener con get-in...
  ;;;   asi como (get m :key) me da el value asociado (que a menudo se acorta con (:key m))
  ;;;   get-in me saca el valor cuando hay nested maps
  ;;; con el mapa de arriba seria (get-in mapa [:k1 :k2 :k3])
  ;;
  ;;(defn my-assoc-in [m [k & ks] v]
  ;;  (recur assoc i...
  ;;
  ;;
  ;;; no tengo muy claro todavia como seria
  ;;
  ;;; digamos que empiezo con un mapa vacio y quiero agregar {:k1 {:k2 {:k3 "v"}}}
  ;;; para esto haria: (assoc-in {} [:k1 :k2 :k3] "v")
  ;;
  ;;; internamente que pasaria?
  ;;; tendria que ir de atras para adelante:
  ;;; primero genero el mapa (assoc {} :k3 "v")
  ;;
  ;;; luego uso el resultado de eso como value... y como key uso :k2
  ;;; asi: (assoc {} :k2 (assoc {} :k3 "v"))
  ;;; luego lo mismo
  ;;; asi: (assoc {} :k1 (assoc {} :k2 (assoc {} :k3 "v")))
  ;;
  ;;; y ahi termina si es que era un mapa nuevo... pero si lo vamos a agregar a un mapa ya existente
  ;;; digamos 'm'
  ;;; entonces hacemos
  ;;; (assoc m :k1 (assoc {} :k2 (assoc {} :k3 "v")))
  ;;
  ;;; aca el unico problema que puede existir es que el key :k1 ya haya estado presente antes
  ;;
  ;;; que pasaria en ese caso?
  ;;; pruebo
  ;;; (assoc {:k1 "vx"} :k1 (assoc {} :k2 (assoc {} :k3 "v")))
  ;;; ah! simplemente sobreescribe. listo!!!!
  ;;
  ;;; entonces parece que voy a tomar ks (rest de keys)
  ;;; y hacer
  ;;; (recur #(assoc %1 %2 %3) {} {(last ks) v} (reverse ks))
  ;;; ...hmmm not quite, pero por ahi va tomando forma
  ;;
  ;;; listemos lo de arriba, los pasos:
  ;;; primero genero el mapa (assoc {} :k3 "v")
  ;;
  ;;; luego : (assoc {} :k2 (assoc {} :k3 "v")) == (assoc {} :k2 OUTPUT)
  ;;
  ;;; lueg (assoc {} :k1 (assoc {} :k2 (assoc {} :k3 "v"))) == (assoc {} :k1 OUTPUT)
  ;;
  ;;; o sea:
  ;;(recur (assoc {} next-key %) ...
  ;;
  ;;       no esta muy claro
  ;;       next-key va a sacar de (reverse ks)
  ;;       % va empezar con "v" y luego va a ir tomando el output para hacer el sgte input
  ;;
  ;;no convendria hacer un two-assoc-in tambien?
  ;;
  ;;como haria eso?
  ;;
  ;;si tengo [:k1 :k2] y "v"
  ;;
  ;;haria
  ;;
  ;;(assoc {} :k1 (assoc {} :k2 "v"))
  ;;
  ;;es casi un recur, pero la funcion va cambiando
  ;;como hago eso?
  ;;
  ;;con loop y recur
  ;;
  ;;o sino lo que le voy a ir pasando son los keys
  ;;
  ;;o que sea una funcion que devulva una funcion con la cosa adentro
  ;;
  ;;loop [function (fn [f] (assoc {} k f))
  ;;      klist ks]
  ;;  (if (empty?
  ;;  (recur (fn [f] (function (assoc {} k f)))
  ;;         (rest klist))
  ;;
  ;;
  ;;
  ;;o sea, estuve pensando diferente... lo que tengo que hacer es construir una funcion de forma recursiva
  ;;
  ;;la funcion tiene 2 parametros:
  ;;
  ;;key y otra funcion
  ;;
  ;;(defn inner-func [k f v]
  ;;  (fn [k g] (assoc {} k (g k g)))
  ;;
  ;;
  ;;espera que estoy queriendo pensar todo a la vez...
  ;;
  ;;y lo mejor seria hacerlo por partes
  ;;
  ;;
  ;;quiero armar la funcion de forma recursiva...
  ;;
  ;;empiezo con (fn [k v] (assoc {} k (?)))
  ;;y dentro de ? tiene que ir el sgte k
  ;;
  ;;(fn [x1 x2] (assoc {} x2 x1) [v (reverse ks)]
  ;;
  ;;
  ;;oooo
  ;;
  ;;primero hago
  ;;
  ;;(map (fn [k v] (assoc {} k v)) ks)
  ;;
  ;;asi?
  ;;
  ;;en realidad lo que quiero es tener
  ;;
  ;;'((fn [f] (assoc {} :k1 f))
  ;;  (fn [f] (assoc {} :k2 f))
  ;;  (fn [f] (assoc {} :k3 f))
  ;;    ...
  ;;  (fn [f] (assoc {} :kn f)))
  ;;
  ;;
  ;;y como usaria esto?
  ;;
  ;;componiendo...
  ;;con la funcion
  ;;
  ;;(fn [f] (f))
  ;;
  ;;pero no se si esto funciona... porque lo que estaria haciendo es componer funciones...
  ;;no puede piko con un macro? XD
  ;;
  ;;
  ;;esto me genera una lista
  ;;
  ;;'((fn [k v] (assoc {}  v)
  ;;
  ;;y 
  ;;
  ;;ahi tengo una lista de funciones assoc
  ;;
  ;;y dsp con recur voy a ir componiendo recursivamente
  ;;
  ;;con que funcion??
  ;;
  ;;o sea, tengo que hacer (recur FUNCION valores)
  ;;
  ;;valores van a ser las funciones de map
  ;;
  ;;FUNCION que es??
  ;;es una funcion que come funciones
  ;;
  ;;(fn [x f] (f x))
  ;;
  ;;
  ;;
  ;;
  ;;
  ;;... fuck, no se che, estoy pensando y estoy disperso, dando vueltas, sin llegar
  ;;
  ;;vamos desde adentro
  ;;
  ;;empiezo con el ultimo:
  ;;
  ;;(assoc {} :kn v)
  ;;que me da un map
  ;;{:kn v}
  ;;(es el output)
  ;;
  ;;el output tengo que usar como value en otro map
  ;;
  ;;o sea podria usar (fn [k] (fn [v] (assoc {} k v)))
  ;;
  ;;eso mapear asi:
  ;;
  ;;(map (fn [k] (fn [v] (assoc {} k v))) ks)
  ;;
  ;;que me da como output
  ;;
  ;;'((fn [v] (assoc {} :k2 v))
  ;;  (fn [v] (assoc {} :k3 v))
  ;;  ...
  ;;  (fn [v] (assoc {} :kn v)))
  ;;
  ;;y esto voy a usar para iterar desde la cola..
  ;;
  ;;o sea, voy a empezar metiendo el valor "v" a la ultima funcion
  ;;
  ;;( (first (reverse functionlist)) "v" )
  ;;
  ;;y el output de esa voy a meter en la sgte funcion...
  ;;y asi.
  ;;
  ;;una vez terminada la lista
  ;;voy a devolver (assoc m k OUTPUT)
  ;;
  ;;vamos

(defn my-assoc-in [m [k & ks] v]
  (loop [function-list (reverse 
                        (map
                          (fn [k]
                            (fn [v] (assoc {} k v)))
                          ks))
         final-map v]
    (if (empty? function-list)
      (assoc m k final-map)
      (recur (rest function-list)
             ((first function-list) final-map)))))

