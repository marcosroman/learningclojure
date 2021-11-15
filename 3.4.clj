(defn mapset
  "A function that works like 'map', except the return value is a set"
  [f s]
  (set (map f s)))
