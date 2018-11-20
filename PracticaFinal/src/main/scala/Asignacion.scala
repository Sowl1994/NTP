class Asignacion(dom: Dominio, lista:List[Int]) {
  //Variables de la clase Asignacion
  val dominio = dom
  val listaValores = lista

  /**
    * Metodo tail-recursive que recorre las variables del dominio para crear un mapa [Variable -> valor]
    * @param indice
    * @param mapa
    * @return
    */
    @annotation.tailrec
    private def go(indice:Int, mapa:Map[String, Int]):Map[String, Int] ={
      //Si hemos llegado al final del dominio, devolvemos el mapa creado
      if(indice == dominio.longitud) mapa
      //Si queda dominio por recorrer, añadimos el par que toque
      else{
        val dato = mapa+( dominio.apply(indice).nombre -> listaValores(indice) )
        go(indice+1, dato)
      }
    }
    val datos = go(0, Map[String, Int]() )

  /**
    * Sobrecarga del método toString para mostrar la asignación
    * @return
    */
  override def toString: String = {
    var cadena = ""
    datos.foreach(p => cadena +="["+p._1+" - " + p._2 + "]")
    cadena
  }

  /**
    * Comprueba si el dominio esta vacio
    * @return
    */
  def vacia:Boolean=if(dominio.vacio)true else false

  /**
    * Devuelve el numero de variable que define la asignación(su dominio)
    * @return
    */
  def obtenerNumeroVariables:Int = dominio.longitud

  /**
    * Devuelve el valor de una variable del dominio de la asignación
    * @param variable
    * @return
    */
  def obtenerValorVariable(variable: Variable):Int = {
    val valor = dominio.variables.indexOf(variable)
    listaValores(valor)
  }

  /**
    * Sobrecarga del metodo suma que devuelve una asignación partiendo de un par variable, valor
    * @param par
    * @return
    */
  def +(par:(Variable, Int)) = {
    var valor = (par._2)
    if(valor < 0 ) valor = 0
    val variab = Variable(par._1.nombre, par._1.estados)
    val dominioNuevo  = dominio + variab
    val listaNueva = listaValores:+valor
    new Asignacion(dominioNuevo,listaNueva)
  }

  /**
    * Devuelve el índice asociado a la asignación
    * @return
    */
  def calcularIndice:Int = {
    val pesos = dominio.pesos
    val valores = listaValores
    var acum = 0

    @annotation.tailrec
    def go(indice:Int): Int ={
      if(indice == dominio.longitud) acum
      else{
        acum += pesos(indice) * valores(indice)
        go(indice+1)
      }
    }

    go(0)
  }

  /**
    * Devuelve una asignación con las variables del dominio y sus valores
    * @param dominio
    * @return
    */
  def proyectar(dominio: Dominio):Asignacion = {
    var valoresDominio = List[Int]()

    //Introducimos los estados de cada variable del dominio en una lista
    dominio.variables.foreach((f:Variable) => {
      valoresDominio = valoresDominio :+ f.estados
    })

    new Asignacion(dominio,valoresDominio)
  }

  /**
    * Comprueba la validez de la lista que recibe
    * @return
    */
  /*def listaValida:Boolean={
    if(dominio.longitud != lista.size) false
    for(i<- 0 until lista.size)
      if(lista(i) > (dominio.apply(i).estados-1) || lista(i) < 0) false
    true
  }*/


}

object Asignacion{
  /**
    * Método que permite crear objetos de la clase Asignacion con un Dominio y una lista de valores
    * @param dominio
    * @param lista
    * @return
    */
  def apply(dominio: Dominio,lista: List[Int]): Asignacion = new Asignacion(dominio,lista)

  /**
    * Método que permite crear objetos de la clase Asignacion solamante pasando un Dominio
    * @param dominio
    * @return
    */
  def apply(dominio: Dominio): Asignacion ={
    var lista = List[Int]()
    @annotation.tailrec
    def go(ini:Int):Unit={
      if(ini < dominio.longitud){
        lista = 0::lista
        go(ini+1)
      }
    }
    go(0)
    new Asignacion(dominio,lista)
  }

  /**
    * Método que permite crear objetos de la clase Asignacion un dominio y un valor de indice
    * @param dom
    * @param indiceAsignacion
    * @return
    */
  def apply(dom: Dominio, indiceAsignacion: Int): Asignacion ={
    val pesos = dom.pesos
    var nuevaLista = List[Int]()
    @annotation.tailrec
    def go(indice:Int): Unit ={
      if(indice < dom.longitud){
        val valor = (indiceAsignacion/pesos(indice))%dom(indice).estados
        nuevaLista = nuevaLista:+valor
        go(indice+1)
      }
    }
    go(0)
    new Asignacion(dom, nuevaLista)
  }

  /**
    * Devuelve una asignación con las variables del dominio y los valores de la asignación pasada por parametro
    * @param dominio
    * @return
    */
  def proyectar(asignacion: Asignacion, dominio: Dominio):Asignacion = {
    var valoresDominio = List[Int]()
    var i2 = 0
    def go(indice:Int): Unit ={
      if(valoresDominio.size < dominio.variables.size){
        if(!dominio.variables(indice).nombre.equals(asignacion.dominio.variables(i2).nombre)){
          i2 = i2 +1
          go(indice)
        }else{
          valoresDominio = valoresDominio :+ asignacion.obtenerValorVariable(asignacion.dominio.variables(i2))
          i2 = 0
          go(indice+1)
        }
      }
    }

    go(0)
    new Asignacion(dominio,valoresDominio)
  }

  /**
    * Devuelve una asignación con las variables del dominio y sus valores
    * @param dominio
    * @return
    */
  def proyectar(dominio: Dominio):Asignacion = {
    var valoresDominio = List[Int]()
    //Introducimos los estados de cada variable del dominio en una lista
    dominio.variables.foreach((f:Variable) => {
      valoresDominio = valoresDominio :+ (f.estados)
    })
    new Asignacion(dominio,valoresDominio)
  }
}
