
import ConjuntoSuitePropiedades.valor
import org.scalacheck.Properties
import org.scalacheck.Prop.{AnyOperators, forAll, throws}
import org.scalacheck.Gen._

object ConjuntoSuitePropiedades extends Properties("Test sobre conjunto") {
   val valor = choose(0, 10)

   /**
     * Generacion de secuencia de tamaño
     *
     * @param tam
     * @return
     */
   def secuencia(tam: Int): Range = {
      val inicio = valor.sample.getOrElse(0)
      inicio to (inicio + tam)
   }

   /**
     * Propiedad para probar el metodo de obtencion de la longitud
     */
   property("conjunto de tamaño uno") =
      forAll(valor) {
         valor => {
            // Se crea el conjunto de un elemento
            val conjunto = Conjunto.conjuntoUnElemento(valor)

            // Se comprueba que el conjunto contiene el valor
            conjunto(valor) == true
         }
      }

   property("conjunto union") =
      forAll(valor) {
         valor => {
            val secuencia1 = secuencia(10)
            val secuencia2 = secuencia(10)

            // Se generan los conjuntos a unir
            val conjunto1 = Conjunto(x => x >= secuencia1.min && x <= secuencia1.max)
            val conjunto2 = Conjunto(x => x >= secuencia2.min && x <= secuencia2.max)

            // Se produce la union
            val union = Conjunto.union(conjunto1, conjunto2)

            // Se itera sobre la union de ambos rangos y se comprueba la
            // pertenencia al conjunto
            val rangoUnion = secuencia1.toList ::: secuencia2.toList

            // De cumplirse que cada elemento esta en el conjunto union
            val resultado = rangoUnion.map(valor => {
               union(valor) == true
            })

            val global: Boolean = resultado.forall(res => res == true)
            global == true
         }
      }

   property("conjunto interseccion") =
     forAll(valor) {
        valor => {
           val secuencia1 = secuencia(10)
           val secuencia2 = secuencia(10)

           // Generación de conjuntos
           val conjunto1 = Conjunto(x => x >= secuencia1.min && x <= secuencia1.max)
           val conjunto2 = Conjunto(x => x >= secuencia2.min && x <= secuencia2.max)

           //Aplicamos el metodo interseccion propio
           val interseccion = Conjunto.interseccion(conjunto1,conjunto2)

           //Comprobamos la interseccion con el método intersect que posee List
           val interseccionList = secuencia1.toList.intersect(secuencia2)

           // De cumplirse que cada elemento esta en el conjunto interseccion
           val resultado = interseccionList.map(valor => {
              interseccion(valor) == true
           })

           val global: Boolean = resultado.forall(res => res == true)
           global == true
        }
     }

   property("conjunto diferencia") =
     forAll(valor) {
        valor => {
           val secuencia1 = secuencia(10)
           val secuencia2 = secuencia(10)

           // Generación de conjuntos
           val conjunto1 = Conjunto(x => x >= secuencia1.min && x <= secuencia1.max)
           val conjunto2 = Conjunto(x => x >= secuencia2.min && x <= secuencia2.max)

           //Aplicamos el metodo diferencia propio
           val diferencia = Conjunto.diferencia(conjunto1,conjunto2)

           //Comprobamos la diferencia con el método diff que posee List
           val diferenciaList = secuencia1.toList.diff(secuencia2)

           // De cumplirse que cada elemento esta en el conjunto diferencia
           val resultado = diferenciaList.map(valor => {
              diferencia(valor) == true
           })

           val global: Boolean = resultado.forall(res => res == true)
           global == true
        }
     }

   property("conjunto filtrar")=
     forAll(valor){
        valor => {
           val secuencia1 = secuencia(10)

           // Generación de conjuntos
           val conjunto1 = Conjunto(x => x >= secuencia1.min && x <= secuencia1.max)
           //Creamos un predicado basandonos en restar el valor maximo del conjunto al valor minimo
           val predicado = (x:Int) => x > (secuencia1.max - secuencia1.min)

           //Aplicamos el metodo filtro propio
           val filtro = Conjunto.filtrar(conjunto1, predicado)

           //Comprobamos el filtro con el método filter que posee List
           val filtroList = secuencia1.toList.filter(predicado)

           // De cumplirse que cada elemento esta en el conjunto diferencia
           val resultado = filtroList.map(valor => {
              filtro(valor) == true
           })

           val global: Boolean = resultado.forall(res => res == true)
           global == true
        }
     }

   property("conjunto map")=
      forAll(valor){
         valor =>{
            val secuencia1 = secuencia(10)

            // Generación de conjuntos
            val conjunto1 = Conjunto(x => x >= secuencia1.min && x <= secuencia1.max)
            //Creamos un predicado basandonos en sumar a los elementos del conjunto la diferencia entre el valor maximo del conjunto y el valor minimo
            val predicado = (x:Int) =>  (secuencia1.max - secuencia1.min)

            //Aplicamos el metodo map propio
            val mapeo = Conjunto.map(conjunto1, predicado)
            //Comprobamos el mapeo con el método map que posee List
            val mapList = secuencia1.toList.map(predicado)
            // De cumplirse que cada elemento esta en el conjunto aplicando el mapeo
            val resultado = mapList.map(valor => {
               mapeo(valor) == true
            })

            val global: Boolean = resultado.forall(res => res == true)
            global == true
         }
      }

   property("conjunto existe")=
     forAll(valor){
        valor => {
           val secuencia1 = secuencia(10)
           // Generación de conjuntos
           val conjunto1 = Conjunto(x => x >= secuencia1.min && x <= secuencia1.max)
           //Creamos un predicado basandonos en restar el valor maximo del conjunto al valor minimo
           val predicado = (x:Int) => x > (secuencia1.max - secuencia1.min)
           //Aplicamos el metodo existe propio
           val existe = Conjunto.existe(conjunto1,predicado)
           //Comprobamos la existencia de elementos con el método exists que posee List
           val existeList = secuencia1.toList.exists(predicado)
           // Comprobamos que coinciden los resultados del conjunto como de la List
           val resultado = existeList == existe
           resultado
        }
     }
}