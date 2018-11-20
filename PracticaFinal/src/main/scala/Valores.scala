trait Valores {

  /**
    * Método que sobreescribe toString para poder imprimir los objetos de la clase Valores
    * @return
    */
  override def toString: String = "Valores "

  /**
    * Obtención del valor correspondiente a una asignacion pasada por parámetro
    * @param asignacion asignación de la que queremos obtener el valor
    * @return
    */
  def obtenerValor(asignacion: Asignacion):Int

  /**
    * Devuelve la lista de variables almacenados que definen el dominio del conjunto de valores
    * @return
    */
  def obtenerVariables:List[Variable]

  /**
    * Devuelve la lista de valores almacenados
    * @return
    */
  def obtenerValores:List[Int]

  /**
    * Método que realiza la combinación de Valores
    * @param x Objeto de la clase Valores que vamos a combinar
    * @return
    */
  def combinar(x:Valores): Valores ={
    //Comprobamos el tipo de Valores que entra para mandarlo a su respectivo método de combinación
    def compruebaTipo[T](v: T) = v match {
      case _: ValoresArray    => {
        val v = this.asInstanceOf[ValoresArray].combinarArrayArray(x)
        v
      }
      case _: ValoresArbol => {
        val v = this.asInstanceOf[ValoresArbol].combinarArbolArbol(x)
        v
      }
    }

    compruebaTipo(this)
  }

  /**
    * Método que realiza la restricción de ValoresArray, para la Variable y el estado especificado por parámetro
    * @param variable variable a restringir
    * @param int estado que queremos restringir
    * @return
    */
  def restringir(variable: Variable,int: Int):Valores

  /**
    * Método que realiza la conversión entre un tipo de valor a otro
    * (ValoresArray -> ValoresArbol)
    * (ValoresArbol -> ValoreArray)
    * @return
    */
  def convertir: Valores ={
    //Comprobamos el tipo de Valores que entra para mandarlo a su respectivo método de conversión
    def compruebaTipo[T](v: T) = v match {
      case _: ValoresArray    => {
        val v = this.asInstanceOf[ValoresArray].convertirAArbol
        v
      }
      case _: ValoresArbol => {
        val v = this.asInstanceOf[ValoresArbol].convertirAArray
        v
      }
    }

    compruebaTipo(this)
  }
}
