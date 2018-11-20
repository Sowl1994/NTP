class PotencialArray(dominio: Dominio, valoresArray: ValoresArray) extends Potencial(dominio,valoresArray) {}
object PotencialArray{
  /**
    * Metodo que permite crear objetos de la clase PotencialArray con un Dominio y una lista de valores
    * @param dominio dominio que tendrá el potencial
    * @param valoresArray lista de valores que tendrá el potencial
    * @return
    */
  def apply(dominio: Dominio,valoresArray: ValoresArray): PotencialArray = new PotencialArray(dominio,valoresArray)
}
