class NodoHoja(variable:Variable,valor:Int) extends Nodo {
  //Variables de la clase NodoHoja
  val nivel = variable
  val valorFinal = valor

  /**
    * Permite obtener el hijo del NodoHoja, que será su valor final
    * @param indice indice del nodo
    * @return
    */
  override def obtenerHijo(indice: Int): Int = valorFinal

  /**
    * Obtenemos el valor del NodoHoja
    * @return
    */
  override def obtenerValor(asignacion: Asignacion): Int = valorFinal

  /**
    * Obtenemos el valor del NodoHoja
    * @return
    */
  override def obtenerValores: List[Int] = List(valorFinal)
}

object NodoHoja{
  /**
    * Método para crear objetos de la clase NodoHoja de forma sencilla
    * @param variable variable que tendrá el objeto
    * @param valor valor que poseerá el nodo
    * @return
    */
  def apply(variable: Variable, valor: Int): NodoHoja = new NodoHoja(variable, valor)
}
