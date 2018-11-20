class ValoresArray(dom:Dominio,lista:List[Int]) extends Valores{
  //Variables de la clase ValoresArray
  val dominio = dom
  var listaDeValores = lista
  /**
    * Método que sobreescribe toString. En este caso particular, recorre todas las asignaciones posibles
    * y les da el valor a cada una guardándolas en la cadena final a mostrar
    * @return
    */
  override def toString: String ={
    var cadena = super.toString + "Array: \n"
    var asignacionPosible = List.fill(dom.indiceVariables.size)(0)
    var ultimo = dom.indiceVariables.size-1

    //Creamos una lista para comparar a ver si tenemos ya todas las asignaciones recorridas
    //(Vemos si es la ultima)
    var listaCasoBase = List[Int]()
    dom.indiceVariables.foreach((f:Int)=>{
      listaCasoBase = listaCasoBase :+ f-1
    })

    @annotation.tailrec
    def todasAsignaciones(indice:List[Int],ultimoD:Int): Any ={
      var ultimoAux = ultimoD
      var recargarLista = indice
      //Si la asignacion pasada por parámetro es la última a analizar, la imprimimos y acabamos
      if (indice.equals(listaCasoBase)){
        val asignacion = Asignacion.apply(dom, indice)
        val valor = obtenerValor(asignacion)
        cadena+=asignacion+" = "+valor+"\n"
      }else{
        if(indice(ultimoD) < (dom.indiceVariables(ultimoD)-1)){
          val asignacion = Asignacion.apply(dom, indice)
          val valor = obtenerValor(asignacion)
          cadena+=asignacion+" = "+valor+"\n"
          recargarLista = indice.updated(ultimoD,indice(ultimoD)+1)
        }else{
          if(indice(ultimo) > 0){
            val asignacion = Asignacion.apply(dom, indice)
            val valor = obtenerValor(asignacion)
            cadena+=asignacion+" = "+valor+"\n"
          }

          recargarLista = indice.updated(ultimoD,0)
          if(indice(ultimoD-1) < (dom.indiceVariables(ultimoD-1)-1)){
            recargarLista = recargarLista.updated(ultimoD-1,indice(ultimoD-1)+1)
            ultimoAux = ultimo
          }else{
            ultimoAux = ultimoD-1
          }
        }
        todasAsignaciones(recargarLista,ultimoAux)
      }
    }
    todasAsignaciones(asignacionPosible,ultimo)

    cadena
  }

  /**
    * Obtención del valor correspondiente a una asignacion pasada por parámetro
    * @param asignacion asignación de la que queremos obtener el valor
    * @return
    */
  override def obtenerValor(asignacion: Asignacion): Int = {
    @annotation.tailrec
    def go(listaV:List[Int], indiceD:Int, dominio: Dominio): List[Int] ={
      //Si llegamos al ultimo valor del dominio, devolvemos la lista de valores posibles
      if(indiceD  == dom.variables.size-1){
        listaV
      }else{
        //Obtenemos el indice y los estados de la variable
        val variableDominio = dom.apply(indiceD)
        val indiceVariable = asignacion.obtenerValorVariable(dom.apply(indiceD))
        val estadosVariable = dom.apply(indiceD).estados
        val nAgrupacion = dominio.maximoIndice+1
        //Agrupamos la lista por el numero de elementos que nos sale de dividir
        //el maximo indice del dominio entre los estados de la variable analizada
        //Tras esto, escogemos la lista designada por el indice de la variable(el que sacamos de la asignacion)
        val listaOK = listaV.grouped((nAgrupacion/estadosVariable)).toList(indiceVariable)
        //Pasamos la lista para volverla a analizar, junto con un incremento del indice para comprobar la siguiente variable
        // de la asignacion dada al principio
        go(listaOK,indiceD+1,dominio-variableDominio)
      }
    }
    //Obtenemos la lista de valores posibles a falta de escoger el ultimo de la asignacion
    val valor = go(lista,0,asignacion.dominio)
    //Este indiceFinal nos dirá que valor cogeremos
    val indiceFinal = asignacion.obtenerValorVariable(asignacion.dominio.apply(asignacion.dominio.variables.size-1))
    //Devolvemos el valor
    valor(indiceFinal)
  }

  /**
    * Devuelve la lista de variables almacenados que definen el dominio del conjunto de valores
    * @return
    */
  override def obtenerVariables:List[Variable]= dom.variables

  /**
    * Devuelve la lista de valores almacenados
    * @return
    */
  override def obtenerValores: List[Int] = lista

  /**
    * Método que realiza la combinación de ValoresArray
    * @param x Objeto de la clase Valores que vamos a combinar
    * @return
    */
  def combinarArrayArray(x:Valores):ValoresArray ={
    //Comprobamos el elemento a combinar, si es un valoresArbol, lo convertimos a valoresArray; si no, continuamos
    var vArray:ValoresArray = x.asInstanceOf[ValoresArray]
    def compruebaTipo[T](v: T) = v match {
      case _: ValoresArray =>  vArray = x.asInstanceOf[ValoresArray]
      case _: ValoresArbol =>  vArray = convertir.asInstanceOf[ValoresArray]
    }
    compruebaTipo(x)

    val dominioFinal = dominio + vArray.dominio
    var listaValores = List[Int]()

    val asignacionPosible = List.fill(dominioFinal.indiceVariables.size)(0)
    val ultimo = dominioFinal.indiceVariables.size-1
    var listaCasoBase = List[Int]()
    dominioFinal.indiceVariables.foreach((f:Int)=>{
      listaCasoBase = listaCasoBase :+ f-1
    })

    @annotation.tailrec
    def go(indice:List[Int],ultimoD:Int): Any ={
      var ultimoAux = ultimoD
      var recargarLista = indice
      if (indice.equals(listaCasoBase)){

        val asignacionFinal = Asignacion(dominioFinal, indice)
        val asignacionThis = Asignacion.proyectar(asignacionFinal,dominio)
        val asignacionOtro = Asignacion.proyectar(asignacionFinal,vArray.dominio)
        val producto = obtenerValor(asignacionThis)*vArray.obtenerValor(asignacionOtro)

        listaValores = listaValores :+ producto

      }else{
        if(indice(ultimoD) < (dominioFinal.indiceVariables(ultimoD)-1)){

          val asignacionFinal = Asignacion(dominioFinal, indice)
          val asignacionThis = Asignacion.proyectar(asignacionFinal,dominio)
          val asignacionOtro = Asignacion.proyectar(asignacionFinal,vArray.dominio)
          val producto = obtenerValor(asignacionThis)*vArray.obtenerValor(asignacionOtro)

          listaValores = listaValores :+ producto
          recargarLista = indice.updated(ultimoD,indice(ultimoD)+1)
        }else{
          if(indice(ultimo) > 0) {
            val asignacionFinal = Asignacion(dominioFinal, indice)
            val asignacionThis = Asignacion.proyectar(asignacionFinal, dominio)
            val asignacionOtro = Asignacion.proyectar(asignacionFinal, vArray.dominio)
            val producto = obtenerValor(asignacionThis) * vArray.obtenerValor(asignacionOtro)

            listaValores = listaValores :+ producto
          }

          recargarLista = indice.updated(ultimoD,0)
          if(indice(ultimoD-1) < (dom.indiceVariables(ultimoD-1)-1)){
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

    ValoresArray(dominioFinal,listaValores)
  }

  /**
    * Método que realiza la restricción de ValoresArray, para la Variable y el estado especificado por parámetro
    * @param variable variable a restringir
    * @param estado estado que queremos restringir
    * @return
    */
  override def restringir(variable: Variable,estado: Int): ValoresArray = {
    val dominioFinal = dom-variable
    var valoresR = List[Int]()

    val asignacionPosible = List.fill(dominioFinal.indiceVariables.size)(0)
    val ultimo = dominioFinal.indiceVariables.size-1
    var listaCasoBase = List[Int]()
    dominioFinal.indiceVariables.foreach((f:Int)=>{
      listaCasoBase = listaCasoBase :+ f-1
    })

    @annotation.tailrec
    def go(indice:List[Int],ultimoD:Int): Any ={
      var ultimoAux = ultimoD
      var recargarLista = indice
      if (indice.equals(listaCasoBase)){
        val asignacionFinal = Asignacion.apply(dominioFinal,indice)
        val asignacionCompleta = asignacionFinal + (variable, estado)
        val asignacionOrdenada = Asignacion.proyectar(asignacionCompleta,dom)
        val valor = obtenerValor(asignacionOrdenada)
        valoresR = valoresR :+ valor
      }else{
        if(indice(ultimoD) < (dominioFinal.indiceVariables(ultimoD)-1)){
          val asignacionFinal = Asignacion.apply(dominioFinal,indice)
          val asignacionCompleta = asignacionFinal + (variable, estado)
          val asignacionOrdenada = Asignacion.proyectar(asignacionCompleta,dom)
          val valor = obtenerValor(asignacionOrdenada)
          valoresR = valoresR :+ valor

          recargarLista = indice.updated(ultimoD,indice(ultimoD)+1)
        }else{
          val asignacionFinal = Asignacion.apply(dominioFinal,indice)
          val asignacionCompleta = asignacionFinal + (variable, estado)
          val asignacionOrdenada = Asignacion.proyectar(asignacionCompleta,dom)
          val valor = obtenerValor(asignacionOrdenada)
          valoresR = valoresR :+ valor

          recargarLista = indice.updated(ultimoD,0)
          if (ultimoD == 0){
            recargarLista = listaCasoBase
          }else{
            if(indice(ultimoD-1) < (dom.indiceVariables(ultimoD-1)-1)){
              recargarLista = recargarLista.updated(ultimoD-1,indice(ultimoD-1)+1)
              ultimoAux = ultimo
            }else{
              ultimoAux = ultimoD-1
            }
          }
        }

        go(recargarLista, ultimoAux)
      }
    }

    go(asignacionPosible,ultimo)

    ValoresArray.apply(dominioFinal,valoresR)
  }

  /**
    * Método que realiza la conversión de ValoresArray a ValoresArbol
    * @return Objeto de la clase ValoresArbol, previamente ValoresArray
    */
  def convertirAArbol: ValoresArbol=ValoresArbol(dominio,lista)

}

object ValoresArray{
  /**
    * Metodo que permite crear objetos de la clase ValoresArray con un Dominio y una lista de valores
    * @param dominio dominio sobre el que vamos a crear el objeto ValoresArray
    * @param lista lista de valores que poseerá el objeto ValoresArray
    * @return
    */
  def apply(dominio: Dominio,lista: List[Int]): ValoresArray = new ValoresArray(dominio,lista)
}
