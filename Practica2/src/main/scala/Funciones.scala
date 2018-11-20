
/**
  * Objeto singleton para probar la funcionalidad del triangulo
  * de Pascal
  */
object Funciones{
  /**
    * Metodo main: en realidad no es necesario porque el desarrollo
    * deberia guiarse por los tests de prueba
    *
    * @param args
    */
  def main(args: Array[String]) {
    /*println("................... Triangulo de Pascal ...................")

    // Se muestran 10 filas del trinagulo de Pascal
    for (row <- 0 to 10) {
      // Se muestran 10 10 columnas
      for (col <- 0 to row)
        print(calcularValorTrianguloPascal(col, row) + " ")

      // Salto de linea final para mejorar la presentacion
      println()
    }

    // Se muestra el valor que debe ocupar la columna 5 en la fila 10
    println(calcularValorTrianguloPascal(10, 15))
    val cadena:List[Char] = List('(',')','(','(','(','a',')',')',')')
    val cadena1:List[Char] = List('(',')','(','(','(','(',')',')',')')
    val cadena2:List[Char] = List('(',')','(',')','(',')',')','(')
    val cadena3:List[Char] = List(')',')','(',')','(',')',')','(')
    val cadena4:List[Char] = List()
    println(chequearBalance(cadena3))

    println(contarCambiosPosibles(4,List(1,2)))
    println(contarCambiosPosibles(5,List(1,2,3,5)))

    val arr1 = Array(1,3,2,5,6,4,9,8,30,50,40)
    val arr2 = Array("a","b","c")
    println("El elemento esta en la posicion "+busquedaBinaria(arr1,1,(x:Int,y:Int) => x>y))
    println(busquedaBinaria(arr2,"a",(x:String,y:String) => x<y))*/
  }

  /**
    * Ejercicio 1: funcion para generar el triangulo de Pascal
    *
    * @param columna
    * @param fila
    * @return
    */
  def calcularValorTrianguloPascal(columna: Int, fila: Int): Int = {
    if (columna == 0 || columna == fila) 1
    else calcularValorTrianguloPascal(columna-1,fila-1)+calcularValorTrianguloPascal(columna,fila-1)
  }

  /**
    * Ejercicio 2: funcion para chequear el balance de parentesis
    *
    * @param cadena cadena a analizar
    * @return valor booleano con el resultado de la operacion
    */

  def chequearBalance(cadena: List[Char]): Boolean = {
    @annotation.tailrec
      def go(cadena: List[Char],abiertos:Int):Boolean = {
        if (cadena.isEmpty){
          if (abiertos == 0)true
          else false
        }
        else{
          if (cadena.head.equals('(')) go(cadena.tail,abiertos+1)
          else if (cadena.head.equals(')')){
            if (abiertos > 0) go(cadena.tail,abiertos-1)
            else false
          }else go(cadena.tail,abiertos)
        }
    }
    go(cadena,0)
  }

  /**
    * Ejercicio 3: funcion para determinar las posibles formas de devolver el
    * cambio de una determinada cantidad con un conjunto de monedas
    *
    * @param cantidad
    * @param monedas
    * @return contador de numero de vueltas posibles
    */
  def contarCambiosPosibles(cantidad: Int, monedas: List[Int]): Int = {
      if(cantidad.equals(0)) return 1
      if(cantidad < 0) return 0
      if(monedas.isEmpty) return 0
      else
        return contarCambiosPosibles(cantidad, monedas.tail) + contarCambiosPosibles(cantidad - monedas.head, monedas)
  }

  /**
   * Metodo generico para busqueda binaria
   * @param coleccion conjunto de datos sobre los que buscar
   * @param aBuscar elemento a buscar
   * @param criterio para comparar dos elementos de tipo A
   * @tparam A parametro de tipo
   * @return posicion del valor buscado o -1 en caso de no hallarlo
   */
  def busquedaBinaria[A](coleccion : Array[A], aBuscar: A, criterio : (A,A) => Boolean) : Int = {
    @annotation.tailrec
    def go(izq:Int, der:Int):Int={
      if(izq == der){
        if (izq >= der && coleccion.sortWith(criterio)(izq) != aBuscar) -1
        else izq
      }
      else{
        val mitad = (izq+der)/2
        if (izq >= der && coleccion.sortWith(criterio)(izq) != aBuscar) -1
        else if(coleccion.sortWith(criterio)(mitad) == aBuscar) mitad
        else{
          if(criterio(aBuscar,coleccion.sortWith(criterio)(mitad)) == true)go(izq, mitad-1)
          else go(mitad+1, der)
        }
      }
    }
    go(0,coleccion.length-1)
  }
}
