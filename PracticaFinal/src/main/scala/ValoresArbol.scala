class ValoresArbol(dominio: Dominio, nodo: Nodo) extends Valores {
  //Variables de la clase ValoresArbol
  val raiz = nodo

  /**
    * Sobrecarga de toString
    * @return
    */
  override def toString: String =  {
    var cadena = super.toString +"Arbol:"
    cadena += "\n"

    val asignacionPosible = List.fill(dominio.indiceVariables.size)(0)
    val ultimo = dominio.indiceVariables.size-1
    var listaCasoBase = List[Int]()
    dominio.indiceVariables.foreach((f:Int)=>{
      listaCasoBase = listaCasoBase :+ f-1
    })

    @annotation.tailrec
    def go(indice:List[Int],ultimoD:Int): Unit ={
      var ultimoAux = ultimoD
      var recargarLista = indice
      if (indice.equals(listaCasoBase)){
        cadena += Asignacion(dominio,indice)+" = "+obtenerValor(Asignacion(dominio,indice))+"\n"
      }else{
        if(indice(ultimoD) < (dominio.indiceVariables(ultimoD)-1)){
          cadena += Asignacion(dominio,indice)+" = "+obtenerValor(Asignacion(dominio,indice))+"\n"
          recargarLista = indice.updated(ultimoD,indice(ultimoD)+1)
        }else{
          if(indice(ultimo) > 0) {
            cadena += Asignacion(dominio,indice)+" = "+obtenerValor(Asignacion(dominio,indice))+"\n"
          }
          recargarLista = indice.updated(ultimoD,0)
          if(indice(ultimoD-1) < (dominio.indiceVariables(ultimoD-1)-1)){
            recargarLista = recargarLista.updated(ultimoD-1,indice(ultimoD-1)+1)
            ultimoAux = ultimo
          }else{
            ultimoAux = ultimoD-1
          }
        }
        go(recargarLista, ultimoAux)
      }
    }
    go(asignacionPosible,ultimo)
    cadena
  }

  /**
    * Obtención del valor correspondiente a una asignacion pasada por parámetro
    * @param asignacion asignación de la que queremos obtener el valor
    * @return
    */
  override def obtenerValor(asignacion: Asignacion): Int = {

    //Recorre el arbol con la asignacion dada para devolver el valor final
    @annotation.tailrec
    def go(indice:Int, nodoValor:Nodo):Int={
      if (indice == asignacion.dominio.variables.size-1){
        val valorAsignacion = asignacion.listaValores(indice)
        nodoValor.asInstanceOf[NodoVariable].listaNodos(valorAsignacion).asInstanceOf[NodoHoja].valorFinal
      }else{
        val valorAsignacion = asignacion.listaValores(indice)
        val nodoH = nodoValor.asInstanceOf[NodoVariable].listaNodos(valorAsignacion)
        go(indice+1,nodoH)
      }
    }

    val valor = go(0,nodo)
    valor
  }

  /**
    * Devuelve la lista de variables almacenados que definen el dominio del conjunto de valores
    * @return
    */
  override def obtenerVariables: List[Variable] = dominio.variables

  /**
    * Obtenemos todos los valores del arbol recorriendo todas sus posibles asignaciones
    * @return
    */
  override def obtenerValores: List[Int] = {
    var listaFinal = List[Int]()
    val asignacionPosible = List.fill(dominio.indiceVariables.size)(0)
    val ultimo = dominio.indiceVariables.size-1
    var listaCasoBase = List[Int]()
    dominio.indiceVariables.foreach((f:Int)=>{
      listaCasoBase = listaCasoBase :+ f-1
    })

    @annotation.tailrec
    def go(indice:List[Int],ultimoD:Int): Unit ={
      var ultimoAux = ultimoD
      var recargarLista = indice
      if (indice.equals(listaCasoBase)){
        listaFinal = listaFinal :+ obtenerValor(Asignacion(dominio,indice))
      }else{
        if(indice(ultimoD) < (dominio.indiceVariables(ultimoD)-1)){
          listaFinal = listaFinal :+ obtenerValor(Asignacion(dominio,indice))
          recargarLista = indice.updated(ultimoD,indice(ultimoD)+1)
        }else{
          if(indice(ultimo) > 0) {
            listaFinal = listaFinal :+ obtenerValor(Asignacion(dominio,indice))
          }
          recargarLista = indice.updated(ultimoD,0)
          if(indice(ultimoD-1) < (dominio.indiceVariables(ultimoD-1)-1)){
            recargarLista = recargarLista.updated(ultimoD-1,indice(ultimoD-1)+1)
            ultimoAux = ultimo
          }else{
            ultimoAux = ultimoD-1
          }
        }
        go(recargarLista, ultimoAux)
      }
    }
    go(asignacionPosible,ultimo)

    listaFinal
  }

  /**
    * Método que realiza la combinación de ValoresArbol
    * @param x Objeto de la clase Valores que vamos a combinar
    * @return
    */
  def combinarArbolArbol(x:Valores): ValoresArbol ={
    //Comprobamos el elemento a combinar, si es un valoresArray, lo convertimos a valoresArbol; si no, continuamos
    /*var vArbol:ValoresArbol = x.asInstanceOf[ValoresArbol]
    def compruebaTipo[T](v: T): Unit = v match {
      case _: ValoresArbol => vArbol = x.asInstanceOf[ValoresArbol]
      case _: ValoresArray => vArbol = convertir.asInstanceOf[ValoresArbol]
    }
    compruebaTipo(x)*/
    val arbolCombinado = this.convertirAArray.combinar(x)
    arbolCombinado.asInstanceOf[ValoresArbol]
  }

  /**
    * Método que devuelve el nodo hijo del indice pasado por parámetro de ValoresArbol
    * @param hijo indice del hijo a devolver
    * @return
    */
  def obtenerHijo(hijo:Int):Nodo={
    //Si es un nodoVariable, devolvemos el nodo deseado
    nodo match {
      case variable: NodoVariable =>
        val nodoHijo = variable.listaNodos(hijo)
        nodoHijo
      case _ =>
        val nodoHijo = nodo.asInstanceOf[NodoVariable].listaNodos(hijo)
        nodoHijo
    }
  }

  /**
    * Método que realiza la restricción de ValoresArray, para la Variable y el estado especificado por parámetro
    * @param variable variable a restringir
    * @param estado estado que queremos restringir
    * @return
    */
  override def restringir(variable: Variable, estado: Int): Valores = this.convertirAArray.restringir(variable,estado)

  /**
    * Método que realiza la conversión de ValoresArbol a ValoresArray
    * @return Objeto de la clase ValoresArray, previamente ValoresArbol
    */
  def convertirAArray: ValoresArray=ValoresArray(dominio,obtenerValores)
}

object ValoresArbol{
  /**
    * Método que permite crear objetos de la clase ValoresArbol con un Dominio y una lista de valores
    * @param dominio dominio del arbol que vamos a crear
    * @param lista lista de valores que poseerá nuestro árbol en sus nodos hoja
    * @return
    */
  def apply(dominio: Dominio, lista: List[Int]): ValoresArbol = {
    // Vamos recorriendo el dominio y sus estados a fin de crear el nodoRaiz

    def go(indice:Int,asignacion: Asignacion): Nodo ={
      //Obtenemos la variable correspondiente al indice pasado como argumento
      val variableCorrespondiente = dominio.variables(indice)
      //Se crea un NodoVariable definido sobre la variable seleccionada anteriormente
      val nodo = NodoVariable(variableCorrespondiente)

      //Si es la última variable iteramos sobre los estados
      if (indice >= dominio.variables.size-1) {
        @annotation.tailrec
        def recorreHijos(ini: Int, fin: Int):Unit = {
          if (ini < fin) {
            val asignacionHoja = asignacion + (variableCorrespondiente,ini)
            //Creamos un NodoHoja y calculamos su indice para añadirlo como valor del nodo
            val hoja = NodoHoja(variableCorrespondiente,lista(asignacionHoja.calcularIndice))
            nodo.aniadeNodo(hoja)
            recorreHijos(ini+1, fin)
          }
        }
        recorreHijos(0,nodo.hijos)

      }
      //si no es la última variable del dominio iteramos sobre los estados de la variable,
      // generando nuevas llamadas a go()
      else{
        @annotation.tailrec
        def recorreHijos(ini: Int, fin: Int):Unit = {
          if (ini < fin) {
            val asignacionSumada = asignacion+(variableCorrespondiente,ini)
            nodo.aniadirHijo(asignacionSumada)
            val nuevoNodo = go(indice+1,asignacionSumada)
            //El resultado de cada nueva llamada lo vamos almacenando, cada NodoVariable tendrá una lista con sus nodosHijos
            nodo.aniadeNodo(nuevoNodo)
            recorreHijos(ini+1, fin)
          }
        }
        recorreHijos(0,nodo.hijos)

      }
      nodo
    }
    val nodoRaiz = go(0,Asignacion(Dominio(List())))
    new ValoresArbol(dominio,nodoRaiz)
  }
}
