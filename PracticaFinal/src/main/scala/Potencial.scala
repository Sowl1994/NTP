abstract class Potencial(dominio: Dominio, valores: Valores) {
  //Variables de la clase Potencial
  val valor = valores

  /**
    * Sobrecarga de toString. Hace uso de las sobrecargas del mismo método realizadas en otras clases
    * @return
    */
  override def toString: String = {
    var cadena = "Dominio del potencial: "
    cadena+=dominio
    cadena+="\nContenedor de valores: \n"
    cadena+=valores
    cadena
  }

  /**
    * Devuelve la lista de valores del potencial
    * @return
    */
  def obtenerValores: List[Int]=valores.obtenerValores

  /**
    * Obtiene los valores finales de un potencial teniendo en cuenta la restricción de una variable con un estado fijo
    * @param variable variable a restringir
    * @param estado estado especificado para la variable restringida
    * @return
    */
  def restringir(variable: Variable, estado:Int):Potencial={
    valores match {
      case array: ValoresArray =>
        val valoresRestringir = array.restringir(variable, estado)
        val potencialFinal = PotencialArray.apply(dominio, valoresRestringir)
        potencialFinal
      case array: ValoresArbol =>
        val valoresRestringir = array.restringir(variable, estado).asInstanceOf[ValoresArray]
        val potencialFinal = PotencialArray.apply(dominio, valoresRestringir)
        potencialFinal.convertir
    }
  }

  /**
    * Realiza una combinación de potenciales
    * @param potencial potencial a combinar
    * @return
    */
  def combinar(potencial:Potencial):Potencial={
    val combinacion = valor.combinar(potencial.valor)
    combinacion match {
      case array: ValoresArray =>
        PotencialArray(dominio, array)
      case array: ValoresArbol =>
        PotencialArbol(dominio, array)
    }
  }

  /**
    * Realiza la conversión entre los tipos de Potencial
    * (PotencialArray -> PotencialArbol)
    * (PotencialArbol -> PotencialArray)
    * @return
    */
  def convertir:Potencial={
    this.valores match {
      case _: ValoresArray =>
        PotencialArbol(dominio, this.valores.convertir.asInstanceOf[ValoresArbol])
      case _ =>
        PotencialArray(dominio, this.valores.convertir.asInstanceOf[ValoresArray])
    }
  }
}
