; 1. You used (comp :intelligence :attributes) to create a function that returns a character’s intelligence. Create a new function, attr , that you can call like (attr :intelligence) and that does the same thing.

(defn attr [a] (comp a :attributes))
