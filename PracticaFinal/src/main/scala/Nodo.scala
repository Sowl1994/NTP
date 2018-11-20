abstract class Nodo {
  //Variable de la clase Nodo
  val nivel:Variable

  /**
    * Sobrecarga de toString
    * @return
    */
  override def toString: String = {nivel.toString}

  /**
    * Nos sirve para obtener el hijo del nodo
    * @param indice indice del hijo que queremos obtener
    * @return
    */
  def obtenerHijo(indice:Int):Any

  /**
    * Obtenemos los valores de ese nodo
    * @return
    */
  def obtenerValores:List[Int]

  /**
    * Obtenemos el valor de la asignacion dada al nodo
    * @param asignacion
    * @return
    */
  def obtenerValor(asignacion: Asignacion): Int

}