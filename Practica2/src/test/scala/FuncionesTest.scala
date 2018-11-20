import org.scalacheck.{Gen, Properties}
import org.scalacheck.Prop.{AnyOperators, forAll}
import org.scalacheck.Gen._

object FuncionesTest extends Properties("FuncionesTest"){
  val MAXIMO = 10
  // Se generan los valores de fila y columna para los bordes
   val coordenadasExtremos = for {
     fila <- Gen.choose(0, MAXIMO)
     columna <- Gen.oneOf(0, fila)
     } yield (columna, fila)

   property("Elementos en lados del triangulo valen 1") = {
     forAll(coordenadasExtremos) { (i) => {
       val resultado=Funciones.calcularValorTrianguloPascal(i._1, i._2)
       println("Fila = "+i._1 +" Columna = "+ i._2+ " Resultado = "+resultado)
       resultado == 1
     }    }
   }

  val strGen =
    (n:Int) =>
        Gen.listOfN(n,Gen.oneOf('(',')',Gen.alphaChar.sample.get)).map(_.mkString)

  property("Comprobar balanceo de paréntesis") = {
    forAll(strGen(10)){ (i) => {
      var pAbierto = 0
      var pCerrado = 0
      for (x<- 0 to i.size-1){
        print(i(x))
      }
      println
      for (x<- 0 to i.size-1){
        i(x) match {
          case '(' => {
            pAbierto += 1
          }
          case ')' => {
            pCerrado += 1
          }
          case _ => {}
        }
      }
      println
      println(pAbierto + " " + pCerrado)
      (pAbierto-pCerrado) >= 0
    }   }
  }

  val arr = Array(1,3,2,5,6,4,9,8,30,50,40)
  property("Busqueda binaria") ={
    println("El elemento esta en la posicion "+ Funciones.busquedaBinaria(arr,5,(x:Int,y:Int) => x>y))
    val arrP = arr.sortWith((x:Int,y:Int) => x>y)
    Funciones.busquedaBinaria(arr,5,(x:Int,y:Int) => x>y) ?= arrP.indexOf(5)
  }

  /*********************Pruebas Lista.scala*********************/
  val lista1List = List(3,7,5,6,7,8)
  val lista1 = Lista.apply(3,7,5,6,7,8)
  val lista2List = List(5,6,8)
  val lista2 = Lista.apply(5,6,8)
  val listaVaciaList = List()
  val listaVacia = Lista.apply()
  val lista3List = List(5,"a",6,'b')
  val lista3 = Lista.apply(5,"a",6,'b')

  property("Longitud")={
    println(Lista.longitud(lista1)+" "+lista1List.size)
    Lista.longitud(lista1) ?= lista1List.size
  }
  property("Suma enteros")={
    println(Lista.sumaEnteros(lista1)+" "+Lista.sumaEnteros(Lista.crearLista(lista1List)))
    Lista.sumaEnteros(lista1) ?= Lista.sumaEnteros(Lista.crearLista(lista1List))
  }
  property("Producto enteros")={
    println(Lista.productoEnteros(lista1)+" "+Lista.productoEnteros(Lista.crearLista(lista1List)))
    Lista.productoEnteros(lista1) ?= Lista.productoEnteros(Lista.crearLista(lista1List))
  }
  property("Concatenacion")={
    val listaPrueba = Lista.toList(Lista.concatenar(lista1,lista2))
    for(x<- 0 to listaPrueba.size-1){print(listaPrueba(x)+ ", ") }
    println()

    val listaPrueba2 = lista1List ++ lista2List
    for(x<- 0 to listaPrueba2.size-1){print(listaPrueba2(x) + ", ")}
    println()

    val listaConcat = Lista.toList(Lista.concatenar(lista1,lista2))
    val listConcat = lista1List ++ lista2List
    listaConcat ?= listConcat
  }
  property("Suma enteros FR")={
    println(Lista.sumaFoldRight(lista1)+" "+Lista.sumaFoldRight(Lista.crearLista(lista1List)))
    Lista.sumaFoldRight(lista1) ?= Lista.sumaFoldRight(Lista.crearLista(lista1List))
  }
  property("Producto enteros FR")={
    println(Lista.productoFoldRight(lista1)+" "+Lista.productoFoldRight(Lista.crearLista(lista1List)))
    Lista.productoFoldRight(lista1) ?= Lista.productoFoldRight(Lista.crearLista(lista1List))
  }
  property("Asignar cabeza") ={
    val listaPrueba = lista1
    Lista.escribe(Lista.asignarCabeza(listaPrueba,0))
    val listaPrueba2 = lista1List
    Lista.escribe(Lista.asignarCabeza(Lista.crearLista(listaPrueba2),0))
    Lista.asignarCabeza(lista1,0) ?= Lista.asignarCabeza(Lista.crearLista(lista1List),0)
  }
  property("Tail") ={
    val listaPrueba = lista1
    Lista.escribe(Lista.tail(listaPrueba))
    val listaPrueba2 = lista1List
    Lista.escribe(Lista.tail(Lista.crearLista(listaPrueba2)))
    Lista.tail(lista1) ?= Lista.crearLista(lista1List.tail)
  }
  property("Eliminar") ={
    val listaPrueba = lista1
    Lista.escribe(Lista.eliminar(listaPrueba,2))
    val listaPrueba2 = lista1List
    Lista.escribe(Lista.eliminar(Lista.crearLista(listaPrueba2),2))
    Lista.eliminar(lista1,2) ?= Lista.eliminar(Lista.crearLista(lista1List),2)
  }
  property("Eliminar mientras") ={
    val listaPrueba = lista1
    Lista.escribe(Lista.eliminarMientras(listaPrueba,(x:Int)=> x+1 <= 4))
    val listaPrueba2 = lista1List
    Lista.escribe(Lista.eliminarMientras(Lista.crearLista(listaPrueba2),(x:Int)=> x+1 <= 4))
    Lista.eliminarMientras(lista1,(x:Int)=> x+1 <= 4) ?= Lista.eliminarMientras(Lista.crearLista(listaPrueba2),(x:Int)=> x+1 <= 4)
  }
  property("Eliminar ultimo") = {
    val listaPrueba = lista1
    Lista.escribe(Lista.eliminarUltimo(listaPrueba))
    val listaPrueba2 = lista1List
    Lista.escribe(Lista.eliminarUltimo(Lista.crearLista(listaPrueba2)))
    Lista.eliminarUltimo(lista1) ?= Lista.eliminarUltimo(Lista.crearLista(lista1List))
  }
  property("FoldLeft")={
    println(Lista.foldLeft(lista1,0)(_ + _) +" "+Lista.foldLeft(Lista.crearLista(lista1List),0)(_+_))
    Lista.foldLeft(lista1,0)(_ + _) ?= Lista.foldLeft(Lista.crearLista(lista1List),0)(_+_)
  }
}
