/**
  * Clase para representar conjuntos definidos mediante una funcion
  * caracteristica (un predicado). De esta forma, se declara el tipo
  * conjunto como un predicado que recibe un entero (elemento) como
  * argumento y dvuelve un valor booleano que indica si pertenece o no
  * al conjunto
  *
  * @param funcionCaracteristica
  */
class Conjunto(val funcionCaracteristica: Int => Boolean){
/**
    * Crea una cadena con el contenido completo del conjunto
    *
    * @return
    */
  override def toString(): String = {
    // El uso de this(i) implica la el uso del metodo apply
    val elementos = for (i <- -Conjunto.LIMITE to Conjunto.LIMITE
                         if this(i)) yield i
    elementos.mkString("{", ",", "}")
  }

  /**
    * Metodo para determinar la pertenencia de un elemento al
    * conjunto
    * @param elemento
    * @return valor booleano indicando si elemento cumple
    *         la funcion caracteristica o no
    */
  def apply(elemento: Int): Boolean = funcionCaracteristica(elemento)
}

/**
  * Objeto companion que ofrece metodos para trabajar con
  * conjuntos
  */
object Conjunto extends App {
   /**
    * Limite para la iteracion necesaria algunas operaciones,
    * entre -1000 y 1000
    */
  private final val LIMITE = 1000
  /**
    * Metodo que permite crear objetos de la clase Conjunto
    * de forma sencilla
    * @param f
    * @return
    */
  def apply(f: Int => Boolean): Conjunto = {new Conjunto(f)}

  /**
    * Crea un conjunto de un solo elemento
    * @param elemto
    * @return
    */
  def conjuntoUnElemento(elemto : Int) : Conjunto = apply((x:Int) => x == elemto)

  /**
    * Método que realiza la unión entre 2 conjuntos
    * @param c1
    * @param c2
    * @return
    */
  def union(c1 : Conjunto, c2 : Conjunto) : Conjunto = apply((x: Int) => c1(x) || c2(x))

  /**
    * Intersección de conjuntos(elementos que tienen en común)
    * @param c1
    * @param c2
    * @return
    */
  def interseccion(c1 : Conjunto, c2 : Conjunto) : Conjunto = apply((x: Int) => c1(x) && c2(x))

  /**
    * Froma un conjunto con los elementos que tiene c1 que no tiene c2
    * @param c1
    * @param c2
    * @return
    */
  def diferencia(c1 : Conjunto, c2 : Conjunto) : Conjunto = apply((x: Int) => c1(x) && !c2(x))

  /**
    * Forma un conjunto filtrado en base al predicado
    * @param c
    * @param predicado
    * @return
    */
  def filtrar(c : Conjunto, predicado : Int => Boolean) : Conjunto = apply((x: Int) => c(x) && predicado(x))

  /**
    * Recorre el conjunto
    * @param conjunto
    * @param predicado
    * @return
    */
  def paraTodo(conjunto : Conjunto, predicado : Int => Boolean) : Boolean = {
    @annotation.tailrec
    def iterar(elemento : Int) : Boolean = {
      if(elemento > LIMITE) true
      else if (!conjunto(elemento)) iterar(elemento+1)
      else predicado(elemento) && iterar(elemento+1)
    }
    iterar(-LIMITE)
  }

  /**
    * Comprueba la existencia de un elemento dentro de un conjunto
    * @param c
    * @param predicado
    * @return
    */
  def existe(c : Conjunto, predicado : Int => Boolean) : Boolean = !paraTodo(c, x => !predicado(x))

  /**
    * Realiza un mapeo sobre el conjunto
    * @param c
    * @param funcion
    * @return
    */
  def map(c : Conjunto, funcion : Int => Int) : Conjunto = apply((x: Int) => existe(c, y => funcion(y) == x))


  /*Modulo de pruebas temporal*/
  val conjunto=apply((x:Int) => x < 2)
  val conjunto2 = apply((x:Int) => (x>5 && x<10))
  println(conjunto)
  println(conjunto2)
  //val pertenece=conjunto(3)
  //println(pertenece)
  val conj1 = conjuntoUnElemento(4)
  //println(conj1)
  println(union(conjunto,conjunto2))
  println(interseccion(conjunto,conjunto2))
  println(diferencia(conjunto,conjunto2))
  println(filtrar(conjunto,((x:Int) => x > 3)))
  println(existe(conjunto,(x:Int) => x > 0))
  println(map(conjunto,(x:Int) => x + 5))
}