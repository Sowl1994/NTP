class PotencialArbol(dominio: Dominio, valoresArbol: ValoresArbol) extends Potencial(dominio,valoresArbol) {}
object PotencialArbol{
  /**
    * Metodo que permite crear objetos de la clase PotencialArray con un Dominio y una lista de valores
    * @param dominio dominio que tendrá el potencial
    * @param valoresArbol lista de valores que tendrá el potencial
    * @return
    */
  def apply(dominio: Dominio, valoresArbol: ValoresArbol): PotencialArbol = new PotencialArbol(dominio, valoresArbol)
}
