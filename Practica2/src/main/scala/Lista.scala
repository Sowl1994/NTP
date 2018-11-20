import scala.collection.mutable.ListBuffer
/**
  * Interfaz generica para la lista
  * @tparam A
  */
sealed trait Lista[+A]

/**
  * Objeto para definir lista vacia
  */
case object Nil extends Lista[Nothing]

/**
  * Clase para definir la lista como compuesta por elemento inicial
  * (cabeza) y resto (cola)
  * @param cabeza
  * @param cola
  * @tparam A
  */
case class Cons[+A](cabeza : A, cola : Lista[A]) extends Lista[A]

/**
  * Objeto para desarrollar las funciones pedidas
  */
object Lista {
   /**
     * Metodo que imprime una lista
     * @param lista objeto de tipo Lista
     * @tparam A
     * @return
     */
   def escribe[A](lista : Lista[A]) : Unit = {
      lista match {
         case Nil => {
            print("Fin")
            println()
         }
         case Cons(cabeza,cola) => {
            print(cabeza+", ")
            escribe(cola)
         }
      }
   }

   /**
     * Metodo para crear un objeto de tipo Lista a partir de un objeto de tipo List
     * @param list objeto de tipo List
     * @tparam A
     * @return
     */
   def crearLista[A](list: List[A]): Lista[A] ={
      if(list.isEmpty)Nil
      else Cons(list.head,apply(list.tail : _*))
   }

   /**
     * Metodo para crear un objeto de tipo List a partir de un objeto de tipo Lista
     * @param lista objeto de tipo Lista
     * @tparam A
     * @return
     */
   def toList[A](lista: Lista[A]):List[A] = {
      var listaAuxGo = ListBuffer[A]()
      //Control para que no entren listas vacias
      if(longitud(lista) == 0){lista}

      @annotation.tailrec
      def go(lista1: Lista[A],listaAuxi:ListBuffer[A]): ListBuffer[A] = {
         //Si las 2 listas han sido analizadas, devolvemos la lista resultante
         if(longitud(lista1) == 0) listaAuxi
         //Si no se ha analizado al completo la primera lista, entramos aqui
         else{
            lista1 match {
               case Cons(cabeza,cola) =>{
                  listaAuxi += (cabeza)
                  go(cola, listaAuxi)
               }
            }
         }
      }
      val listaAL = go(lista,listaAuxGo).toList
      listaAL
   }

   /**
     * Metodo para permitir crear listas sin usar new
     * @param elementos secuencia de elementos a incluir en la lista
     * @tparam A
     * @return
     */
   def apply[A](elementos : A*) : Lista[A] = {
      if(elementos.isEmpty)Nil
      else Cons(elementos.head,apply(elementos.tail : _*))
   }

   /**
     * Obtiene la longitud de una lista
     * @param lista
     * @tparam A
     * @return
     */
   def longitud[A](lista : Lista[A]) : Int = {
      lista match {
         case Nil => 0
         case Cons(cabeza,cola) => 1+longitud(cola)
      }
   }

   /**
     * Metodo para sumar los valores de una lista de enteros
     * @param enteros
     * @return
     */
   def sumaEnteros(enteros : Lista[Int]) : Double = enteros match {
      case Nil => 0.0
      case Cons(cabeza, cola) => cabeza.toDouble + sumaEnteros(cola)
   }


   /**
     * Metodo para multiplicar los valores de una lista de enteros
     * @param enteros
     * @return
     */
   def productoEnteros(enteros : Lista[Int]) : Double = enteros match {
      case Nil => 1.0
      case Cons(cabeza,cola) => cabeza.toDouble * productoEnteros(cola)
   }

   /**
     * Metodo para agregar el contenido de dos listas
     * @param lista1
     * @param lista2
     * @tparam A
     * @return
     */
   def concatenar[A](lista1: Lista[A], lista2: Lista[A]): Lista[A] = {
      var listaAuxGo = ListBuffer[A]()
      //Control para que no entren listas vacias
      if(longitud(lista1) == 0) lista2
      if(longitud(lista2) == 0) lista1

      def go(lista1p: Lista[A], lista2p: Lista[A],listaAuxi:ListBuffer[A]): ListBuffer[A] = {
         //Si las 2 listas han sido analizadas, devolvemos la lista resultante
         if(longitud(lista1p) == 0 && longitud(lista2p) == 0) listaAuxi
         //Si la primera lista ha sido analizada, analizamos la segunda
         else if(longitud(lista1p) == 0){
            lista2p match {
               case Cons(cabeza2,cola2)=>{
                  listaAuxi += (cabeza2)
                  go(lista1p,cola2,listaAuxi)
               }
            }
         }
         //Si no se ha analizado al completo la primera lista, entramos aqui
         else{
            lista1p match {
               case Cons(cabeza,cola) =>{
                  listaAuxi += (cabeza)
                  go(cola, lista2p,listaAuxi)
               }
            }
         }
      }
      val listaAL = go(lista1,lista2,listaAuxGo).toList
      crearLista(listaAL)
   }

   /**
     * Funcion de utilidad para aplicar una funcion de forma sucesiva a los
     * elementos de la lista
     * @param lista
     * @param neutro
     * @param funcion
     * @tparam A
     * @tparam B
     * @return
     */
   def foldRight[A, B](lista : Lista[A], neutro : B)(funcion : (A, B) => B): B = {
      val listFR = toList(lista)
      listFR.foldRight(neutro)(funcion)
   }

   /**
     * Suma mediante foldRight
     * @param listaEnteros
     * @return
     */
   def sumaFoldRight(listaEnteros : Lista[Int]) : Double = foldRight(listaEnteros,0.0)(_ + _)

   /**
     * Producto mediante foldRight
     * @param listaEnteros
     * @return
     */
   def productoFoldRight(listaEnteros : Lista[Int]) : Double = foldRight(listaEnteros,1.0)(_*_)

   /**
     * Reemplaza la cabeza por nuevo valor. Se asume que si la lista esta vacia
     * se devuelve una lista con el nuevo elemento
     *
     * @param lista
     * @param cabezaNueva
     * @tparam A
     * @return
     */
   def asignarCabeza[A](lista : Lista[A], cabezaNueva : A) : Lista[A] = {
      lista match {
         case Nil => apply(cabezaNueva)
         case Cons(cabeza,cola) => Cons(cabezaNueva,cola)
      }
   }

   /**
     * Elimina el elemento cabeza de la lista
     * @param lista
     * @tparam A
     * @return
     */
   def tail[A](lista : Lista[A]): Lista[A] = eliminar(lista,1)

   /**
     * Elimina los n primeros elementos de una lista
     * @param lista lista con la que trabajar
     * @param n numero de elementos a eliminar
     * @tparam A tipo de datos
     * @return
     */
   def eliminar[A](lista : Lista[A], n: Int) : Lista[A] = {
      if (n > 0) lista match {case Cons(cabeza,cola) => eliminar(cola,n-1)}
      else lista
   }

   /**
     * Elimina elementos mientra se cumple la condicion pasada como
     * argumento
     * @param lista lista con la que trabajar
     * @param criterio predicado a considerar para continuar con el borrado
     * @tparam A tipo de datos a usar
     * @return
     */
   def eliminarMientras[A](lista : Lista[A], criterio: A => Boolean) : Lista[A] = {
      lista match {
         case Cons(cabeza,cola) => {
            if(criterio(cabeza)) eliminarMientras(cola,criterio)
            else lista
         }
         case Nil => lista
      }
   }

   /**
     * Elimina el ultimo elemento de la lista. Aqui no se pueden compartir
     * datos en los objetos y hay que generar una nueva lista copiando
     * datos
     * @param lista lista con la que trabajar
     * @tparam A tipo de datos de la lista
     * @return
     */
   def eliminarUltimo[A](lista : Lista[A]) : Lista[A] = {
      var listaAux = ListBuffer[A]()
      @annotation.tailrec
      def go(resto:Lista[A], n:Int,listaAuxi:ListBuffer[A]):ListBuffer[A]={
         if (n>1){
            resto match {
               case Cons(cabeza,cola) =>{
                  listaAuxi += cabeza
                  go(cola,longitud(resto)-1,listaAuxi)
               }
            }
         }else listaAuxi
      }
      val listaSinUltimo = go(lista,longitud(lista),listaAux).toList
      crearLista(listaSinUltimo)
   }


   /**
     * foldLeft con recursividad tipo tail
     * @param lista lista con la que trabajar
     * @param neutro elemento neutro
     * @param funcion funcion a aplicar
     * @tparam A parametros de tipo de elementos de la lista
     * @tparam B parametro de tipo del elemento neutro
     * @return
     */
   @annotation.tailrec
   def foldLeft[A, B](lista : Lista[A], neutro: B)(funcion : (B, A) => B): B = {
      lista match {
         case Nil => neutro
         case Cons(cabeza, cola) => foldLeft(cola,funcion(neutro,cabeza))(funcion)
      }
   }
}
