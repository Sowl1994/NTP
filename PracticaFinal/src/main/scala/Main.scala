object Main extends App {
/*************************************************Zona de pruebas de Variables**********************************************************/
  val X1 = Variable("X1",3)
  val X2 = Variable("X2",4)
  val X3 = Variable("X3",2)
  val X4 = Variable("X4",2)
  val X5 = Variable("X5",5)

  /* val D1 = Dominio(List(X1))
   val D2 = Dominio(List(X2,X3))
   val D3 = D1+D2
   var D4 = D3 - X3
   D4 -=  X1
   println("Pesos: " +D3.pesos + " -> Max. indice: " +D3.maximoIndice)
   println(D4)*/

/**************************************************Zona de pruebas de Dominios**********************************************************/
  /*val dominioVacio = Dominio(List())
  println("Comprobacion de vacio sobre dominio vacio: "+dominioVacio.vacio)
  val dominioNoVacio = Dominio(List(X1,X2,X3,X4))
  println("Comprobacion de vacio sobre dominio no vacio: "+dominioNoVacio.vacio)
  println("Longitud del dominio no vacio(debe ser 4): "+ dominioNoVacio.longitud)
  println(dominioNoVacio)

  val dominioSuma = dominioNoVacio+X5
  println("Dominio suma (+X5): " + dominioSuma)

  val X6 = Variable("X6", 3)
  val dominioNovacio2 = Dominio(List(X1,X2,X5,X6))

  val dominioSuma2 = dominioNoVacio+dominioNovacio2
  println("Suma de dominios: "+dominioSuma2)

  println("Maximo indice de dominio de suma: "+dominioSuma2.maximoIndice)*/

/**************************************************Zona de pruebas de Asignaciones**********************************************************/
  /*val D1 = Dominio(List(X1,X2,X3))
  val D2 = Dominio(List(X3,X4))
  val asignacionVacia = Asignacion(Dominio(List()), List())
  println("Comprobacion vacio sobre asignacion vacia: "+asignacionVacia.vacia)
  val asignacion1=Asignacion(Dominio(List(X1,X2,X3,X4)), List(2,3,1,0))
  println("Comprobacion vacio sobre asignacion no vacia: " + asignacion1.vacia)
  println("Se muestra la asignacion: ")
  println(asignacion1)
  println("Calcular indice(46): "+asignacion1.calcularIndice)
  println("Asignacion resultante: "+Asignacion(asignacion1.dominio,asignacion1.calcularIndice))*/

/**************************************************Zona de pruebas de Valores & Potenciales**********************************************************/

  /*val D1 = Dominio(List(X1,X2,X3))
  val listaAsignaciones = 0 to 23 toList
  val asignacionGrande = Asignacion(D1,List(2,2,0))

  val valor2 = ValoresArray(D1,listaAsignaciones)
  println(valor2.obtenerVariables)
  println(valor2.obtenerValor(asignacionGrande))

  println(valor2)
  println(valor2.restringir(X2,3))*/

/***************************Restringir ValoresArray********************/
  val X1V = Variable("X1",2)
  val X2V = Variable("X2",2)
  val X3V = Variable("X3",2)
  val domV = Dominio(List(X1V,X2V,X3V))
  val valorPrueba = ValoresArray(domV,List(3,7,6,4,1,0,9,8))
  val asignacionPrueba = Asignacion(domV,List(1,1,1))
  /*println(domV + " " +asignacionPrueba)
  println(valorPrueba)
  println("Valor: "+valorPrueba.obtenerValor(asignacionPrueba))
  println(valorPrueba)
  println(valorPrueba.restringir(X1V,1))
  println(valorPrueba.convertirAArbol)*/

/***************************Combinar ValoresArray********************/
  /*val domCombinar1 = Dominio(List(X1V,X2V))
  val domCombinar2 = Dominio(List(X2V,X3V))
  val valores1 = ValoresArray(domCombinar1,List(3,7,6,4))
  val valores2 = ValoresArray(domCombinar2,List(9,1,10,0))

  val p1C = PotencialArray(domCombinar1,valores1)
  val p2C = PotencialArray(domCombinar2,valores2)
  println(p1C.combinar(p2C))*/

/***************************Pruebas ValoresArbol********************/

  /*val arbolito = ValoresArbol(domV, List(3,7,6,4,1,0,9,8))
  println(arbolito)
  println(arbolito.obtenerValores)
  println(arbolito.convertir)
  println(arbolito.obtenerValor(Asignacion(domV,List(0,0,0))))
  println(arbolito.raiz.obtenerValor(Asignacion(domV,List(0,1,1))))
  println(arbolito.obtenerValores)*/

/***************************Pruebas Potencial********************/

  /*val p1 = PotencialArray(domV,valorPrueba)
  println(p1)
  println("Listado de valores: "+p1.obtenerValores)
  println(p1.restringir(X1V,1))*/

}
