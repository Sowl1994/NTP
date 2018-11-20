class NodoVariable(variable:Variable) extends Nodo {
  //Variables de la clase NodoVariable
  val nivel = variable
  val hijos = variable.estados
  var listaAsignaciones = List[Asignacion]()
  var listaNodos = List[Nodo]()

  /**
    * Método que permite ir almacenando las asignaciones con las que se llega a el nodo en cuestión
    * @param asignacion asignación a almacenar
    */
  def aniadirHijo(asignacion: Asignacion):Unit=listaAsignaciones = listaAsignaciones:+asignacion

  /**
    * Método que permite el almacenamiento de los nodos hijos
    * @param nodo nodo a almacenar
    */
  def aniadeNodo(nodo: Nodo):Unit=listaNodos = listaNodos:+nodo

  /**
    * Obtenemos el hijo del nodo
    * @param indice indice del nodo, el cual poseerá un hijo a devolver
    * @return
    */
  override def obtenerHijo(indice: Int): Nodo = this.listaNodos(indice)

  /**
    * Método que nos devuelve el valor que se consigue al seguir una asignación
    * @param asignacion asignación que tenemos de base para alcanzar un valor de un NodoHoja
    * @return
    */
  override def obtenerValor(asignacion: Asignacion): Int = {
    //Recorremos la asignacion por el árbol para obtener el valor que produce
    def go(indice:Int,nodoAnalizado:Nodo):Int = {
      if(indice >= asignacion.listaValores.size-1){
        nodoAnalizado.asInstanceOf[NodoVariable].listaNodos(asignacion.listaValores(indice)).asInstanceOf[NodoHoja].valorFinal
      }else{
        val nodoHijo = nodoAnalizado.asInstanceOf[NodoVariable].obtenerHijo(asignacion.listaValores(indice))
        go(indice+1,nodoHijo)
      }
    }
    go(0,this)
  }

  override def obtenerValores: List[Int] = {???
    /*var listaFinal = List[Int]()
    var listaNodosPorVer = List[Nodo]()

    def go(indice:Int,nodoAnalizado:Nodo): Unit ={
      if(indice <= nodoAnalizado.nivel.estados-1){
        nodoAnalizado.asInstanceOf[NodoVariable].obtenerHijo(indice) match {
          case hoja: NodoHoja =>
            println(hoja.valorFinal)
            listaFinal = listaFinal :+ hoja.valorFinal
          case _ =>
            println("Quedan hijos por ver, los metemos en la lista")
            val nodoHijo = nodoAnalizado.asInstanceOf[NodoVariable].obtenerHijo(indice)
            listaNodosPorVer = listaNodosPorVer :+ nodoHijo
            println("lista de nodos: " + listaNodosPorVer)
            go(indice + 1, nodoAnalizado)
        }
      }else{

        println("Hijos recorridos, vamos a analizar la lista")
        def recorreNodosPorVer(ini:Int,fin:Int): Unit ={
          if(ini < fin && listaNodosPorVer.size >0){
            println(ini+ " "+ fin +" Nodo: "+listaNodosPorVer(ini))
            val nodoT = listaNodosPorVer(0)
            listaNodosPorVer = listaNodosPorVer.tail
            println("lista de nodos tras recorrer: "+listaNodosPorVer)
            go(0,nodoT)

            recorreNodosPorVer(ini+1,fin)
          }
        }
        recorreNodosPorVer(0,listaNodosPorVer.size)
      }


    }

    go(0,this)

    listaFinal*/
  }
}
object NodoVariable{

  /**
    * Método que permite la creación de objetos de la clase NodoVariable de forma sencilla
    * @param variable variable del NodoVariable
    * @return
    */
  def apply(variable: Variable): NodoVariable = new NodoVariable(variable)
}
