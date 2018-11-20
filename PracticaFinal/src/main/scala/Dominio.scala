class Dominio(lista: List[Variable]) {
  //Variables de la clase Dominio
  val variables = lista
  var indiceVariables = List[Int]()
  var pesosVariables = List[Int]()

  //Lo primero que hacemos si el dominio no está vacío es rellenar las listas de pesos e indices
  //a fin de facilitar el uso de las mismas
  if (!vacio){
    lista.foreach((e: (Variable)) => indiceVariables = indiceVariables:+e.estados)
    pesosVariables = pesos
  }

  /**
    * Sobrecarga del método toString para mostrar el dominio
    * @return String
    */
  override def toString: String = {
    @annotation.tailrec
    def go(cadena:String, indice:Int):String={
      if (indice == longitud)  cadena
      else{
        var cadAux = cadena
        cadAux += lista(indice).nombre + "(s: "+indiceVariables(indice) +  ", w: "+ pesosVariables(indice) +") "
        go(cadAux, indice+1)
      }
    }
    go("",0)
  }

  /**
    * Sobrecarga del operador + para añadir variables
    * @param variable Variable que queremos añadir del dominio
    * @return
    */
  def +(variable: Variable):Dominio ={
    //Si la variable ya se encuentra en el dominio, no la introducimos, devolviendo el mismo dominio
    if (!variables.contains(variable)){
      val newList = lista:+variable
      new Dominio(newList)
    }else new Dominio(lista)
  }

  /**
    * Sobrecarga del operador + para añadir dominios
    * @param dominio dominio que queremos añadir a nuestro dominio
    * @return
    */
  def + (dominio: Dominio) ={
    var newList = variables
    dominio.variables.foreach((e:Variable) => {
      if(!variables.contains(e)){
        newList = newList :+ e
      }
    })
    new Dominio(newList)
  }

  /**
    * Sobrecarga del operador - para eliminar variables
    * @param variable Variable que queremos eliminar del dominio
    * @return
    */
  def -(variable: Variable) ={
    if (variables.contains(variable)){
      val listaFinal = variables diff List(variable)
      new Dominio(listaFinal)
    }else{
      new Dominio(variables)
    }
  }

  /**
    * Devuelve la longitud del dominio
    * @return
    */
  def longitud():Int = lista.size

  /**
    * Comprueba si el dominio está vacío
    * @return
    */
  def vacio:Boolean = if(longitud == 0) true else false

  /**
    * Método que nos da la variable que ocupa la posicion 'posicion' del dominio
    * @param posicion índice que queremos consultar
    * @return Variable
    */
  def apply(posicion: Int): Variable = lista(posicion)

  /**
    * Devuelve la lista de pesos de un dominio
    * @return
    */
  def pesos():List[Int]={
    var listaPesos = List[Int]()
    @annotation.tailrec
    def go(longActual:Int, aux:Int):List[Int]={
      //Si es el primer valor, automaticamente guardamos un 1 y continuamos recorriendo el dominio
      if(longActual == longitud-1){
        listaPesos = 1::listaPesos    //aux = 1
        go(longActual-1,1)
      }else{
        val calculaPeso = aux * variables(longActual+1).estados
        listaPesos = calculaPeso :: listaPesos
        //Si es el ultimo valor, realizamos la operación y devolvemos la lista
        if (longActual == 0)
          listaPesos
        //Si no es ni el primer ni el ultimo valor, realizamos la operacion
        //y realizamos llamada recursiva para continuar recorriendo el dominio
        else
          go(longActual-1,calculaPeso)
      }
    }

    //Si el dominio tiene solo longitud 1, peso=1
    if(longitud == 1){
      listaPesos = 1::listaPesos
      listaPesos
    }else go(longitud-1,0)
  }

  /**
    * Devuelve el máximo indice del dominio
    * @return Int
    */
  def maximoIndice:Int = {
    val listaPesos = pesos
    var acum = 0

    //Recorremos el dominio y vamos calculando su maximo indice en funcion al peso de cada Variable
    @annotation.tailrec
    def go(longitudActual:Int): Int ={
      if(longitudActual < longitud){
        acum += (variables(longitudActual).estados-1) * listaPesos(longitudActual)
        go(longitudActual+1)
      }else acum
    }
    go(0)
  }
}

object Dominio{
  /**
    * Método que permite crear objetos de la clase Dominio de forma sencilla
    * @param list lista de variables que poseerá el Dominio
    * @return Dominio
    */
  def apply(list: List[Variable]): Dominio = new Dominio(list)
}