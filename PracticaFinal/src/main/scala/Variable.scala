class Variable(val nombre:String, val nEstados:Int) {
  //Variables de la clase Variable
  val nombreV = nombre
  val estados = nEstados

  /**
    * Sobrecarga del método toString para mostrar la variable
    * @return String
    */
  override def toString: String = {
    val elementos = for (i <- 0 until nEstados) yield i
    nombre+": "+elementos.mkString("(", ",", ")")
  }
}

object Variable{
  /**
    * Método que permite crear objetos de la clase Variable de forma sencilla
    * @param nombre nombre de la variable
    * @param nEstados número de estados que posee la variable
    * @return
    */
  def apply(nombre:String, nEstados:Int): Variable = new Variable(nombre,nEstados)
}
