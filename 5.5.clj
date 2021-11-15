; 5. Implement update-in .

  ;;(defn my-update-in [m ks f & args]
  ;;;..
  ;;
  ;;; lo primero es obtener el valor... eso hago con get-in
  ;;
  ;;(get-in m ks)
  ;;
  ;;; lo sgte es aplicar la funcion (con sus argumentos) al valor obtenido
  ;;
  ;;(f (get-in m ks) args) ; aca que pasa si args es nil? capaz tenga que distinguir los dos casos con un if, para que no tire algun error
  ;;; * probar por separado en el repl
  ;;== NEWVALUE
  ;;
  ;;y una vez que tengo el nuevo valor, aplicar nuevamente con assoc-in
  ;;
  ;;(assoc-in m ks NEWVALUE)
  ;;
  ;;y listo! (no? parece facil... que problema tendra?? XD)

(defn new-value [m ks f & args]
  (apply f (get-in m ks) args))
; en realidad creo que tengo que usar apply por aca...


