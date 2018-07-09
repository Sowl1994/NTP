
import listado.Empleado;
import listado.Ruta;
import listado.Sector;
import org.junit.BeforeClass;
import org.junit.Test;

import listado.ListadoEmpleados;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;

/**
 * Práctica 1 NTP
 */
public class ListadoTestP2 {
   private static ListadoEmpleados listado;

   /**
    * Codigo a ejecutar antes de realizar las llamadas a los métodos
    * de la clase; incluso antes de la propia instanciación de la
    * clase. Por eso el método debe ser estatico
    */
   @BeforeClass
   public static void inicializacion() {
      System.out.println("Metodo inicializacion conjunto pruebas");
      // Se genera el listado de empleados
      try {
         listado = new ListadoEmpleados("./data/datos.txt");
      } catch (IOException e) {
         System.out.println("Error en lectura de archivo de datos");
      }

      // Se reparan los problemas y se pasan los datos al datos miembro
      // listado
      Map<String, List<Empleado>> dnisRepetidos=listado.obtenerDnisRepetidosArchivo();
      listado.repararDnisRepetidos(dnisRepetidos);
      Map<String, List<Empleado>> correosRepetidos = listado.obtenerCorreosRepetidosArchivo();
      listado.repararCorreosRepetidos(correosRepetidos);
      listado.validarListaArchivo();

      // Se leen ahora los archivos de asignaciones de sectores y rutas
      try{
         long errores;
         listado.cargarArchivoAsignacionSector("./data/asignacionSECTOR1.txt");
         listado.cargarArchivoAsignacionSector("./data/asignacionSECTOR2.txt");
         listado.cargarArchivoAsignacionRuta("./data/asignacionRUTA1.txt");
         listado.cargarArchivoAsignacionRuta("./data/asignacionRUTA2.txt");
         listado.cargarArchivoAsignacionRuta("./data/asignacionRUTA3.txt");
      } catch(IOException e){
         System.out.println("Problema lectura datos asignacion");
         System.exit(0);
      }
   }

   /**
    * Test del procedimiento de asignacion de grupos procesando
    * los archivos de asignacion. Tambien implica la prueba de
    * busqueda de empleados sin ruta asignada en algun sector
    *
    * @throws Exception
    */
   @Test
   public void testBusquedaEmpleadosSinRuta() throws Exception {
      // Se obtienen los empleados no asignados a ruta por cada sector
      // y se comprueba su valor
      int res1, res2, res3;
      res1=listado.buscarEmpleadosSinRuta(Sector.NOSECTOR).size();
      res2=listado.buscarEmpleadosSinRuta(Sector.SECTOR1).size();
      res3=listado.buscarEmpleadosSinRuta(Sector.SECTOR2).size();
      System.out.println("res1: "+res1+" res2: "+res2+ " res3: "+res3);
      assert (res1 == 418);
      assert (res2 == 432);
      assert (res3 == 399);
   }

    /**
     * Test del procedimiento de asignacion de grupos procesando
     * los archivos de asignacion. Tambien implica la prueba de
     * busqueda de empleados sin sector asignada en alguna ruta
     *
     * @throws Exception
     */
    @Test
    public void testBusquedaEmpleadosSinSector() throws Exception {
        // Se obtienen los empleados no asignados a sector por cada ruta
        // y se comprueba su valor
        int res1, res2, res3, res4;
        res1=listado.buscarEmpleadosSinSector(Ruta.NORUTA).size();
        res2=listado.buscarEmpleadosSinSector(Ruta.RUTA1).size();
        res3=listado.buscarEmpleadosSinSector(Ruta.RUTA2).size();
        res4=listado.buscarEmpleadosSinSector(Ruta.RUTA3).size();
        System.out.println("res1: "+res1+" res2: "+res2+ " res3: "+res3+ " res4: "+res4);
        assert (res1 == 418);
        assert (res2 == 446);
        assert (res3 == 414);
        assert (res4 == 409);
    }

    /**
     * Test para comprobar el numero de empleados sin sector asignado
     */
    @Test
    public void testComprobarTotalEmpleadosSinSector() {
        assert (listado.buscarEmpleadosSinSectorConRuta().size() == 1687);
    }

    /**
     * Test para comprobar el numero de empleados sin ruta asignada
     */
    @Test
    public void testComprobarTotalEmpleadosSinRuta() {
        assert (listado.buscarEmpleadosConSectorSinRuta().size() == 1249);
    }

    /**
     * Test para comprobar el numero de empleados sin ruta ni sector asignados
     */
    @Test
    public void testComprobarTotalEmpleadosSinSectorSinRuta() {
        assert (listado.buscarEmpleadosSinSectorSinRuta().size() == 418);
    }

   /**
    * Prueba del procedimiento general de obtencion de contadores
    * para todos los sectores
    *
    * @throws Exception
    */
   @Test
   public void testObtenerContadoresSector() throws Exception {
      // Se obtienen los contadores para todas las rutas
      Map<Sector, Map<Ruta, Long>> contadores =
         listado.obtenerContadoresSectorRuta();

      // Se comprueban los valores obtenenidos con los valores por referencia
      Long contadoresReferenciaSector1[] = {401L, 437L, 403L, 432L};
      Long contadoresReferenciaSector2[] = {428L, 425L, 388L, 399L};
      Long contadoresReferenciaNoSector[] = {446L, 414L, 409L, 418L};

      // Se comprueban los resultado del metodo con los de referencia
      Long contadoresCalculados[] = new Long[4];
      assertArrayEquals(contadores.get(Sector.NOSECTOR).values().
         toArray(contadoresCalculados), contadoresReferenciaNoSector);
      assertArrayEquals(contadores.get(Sector.SECTOR1).values().
         toArray(contadoresCalculados), contadoresReferenciaSector1);
      assertArrayEquals(contadores.get(Sector.SECTOR2).values().
         toArray(contadoresCalculados), contadoresReferenciaSector2);
   }

    /**
     * Test que nos muestra el total de empleados por sector
     */
   @Test
   public void testObtenerContadoresSectores(){
       ArrayList<Long> contadores = listado.obtenerContadoresSectores();
       System.out.println(contadores);
       Long contadoresReferencia[] = {1275L, 1276L, 1200L, 1249L};
       Long contadoresCalculados[] = new Long[4];
       assertArrayEquals(contadores.toArray(contadoresCalculados),contadoresReferencia);
   }

   /**
    * Test que nos muestra el total de empleados categorizados por ruta en el sector 1
    */
   @Test
   public void testObtenerContadoresSector1() {
      // Se obtienen los empleados para el sector 1
      Map<Ruta, Long> contadores = listado.obtenerContadoresRuta(Sector.SECTOR1);
      contadores.keySet().stream().forEach(key -> System.out.println(
              key.toString() + "- " + contadores.get(key)));
       //Comprobamos que haya en la RUTA1: 401, RUTA2: 437, RUTA3: 403 y en NORUTA: 432
      Long contadoresReferencia[] = {401L, 437L, 403L, 432L};
      Long contadoresCalculados[] = new Long[4];
      assertArrayEquals(contadores.values().toArray(contadoresCalculados),
              contadoresReferencia);
   }

   /**
    * Test que nos muestra el total de empleados categorizados por ruta en el sector 2
    */
   @Test
   public void testObtenerContadoresSector2() {
       // Se obtienen los empleados para el sector 2
      Map<Ruta, Long> contadores = listado.obtenerContadoresRuta(Sector.SECTOR2);
      contadores.keySet().stream().forEach(key -> System.out.println(
              key.toString() + "- " + contadores.get(key)));
      //Comprobamos que haya en la RUTA1: 428, RUTA2: 425, RUTA3: 388 y en NORUTA: 399
      Long contadoresReferencia[] = {428L, 425L, 388L, 399L};
      Long contadoresCalculados[] = new Long[4];
      assertArrayEquals(contadores.values().toArray(contadoresCalculados),
              contadoresReferencia);
   }

    /**
     * Test que nos muestra el total de empleados categorizados por ruta en el NoSector
     */
    @Test
    public void testObtenerContadoresNoSector() {
        // Se obtienen los empleados para el NOSECTOR
        Map<Ruta, Long> contadores = listado.obtenerContadoresRuta(Sector.NOSECTOR);
        contadores.keySet().stream().forEach(key -> System.out.println(
                key.toString() + "- " + contadores.get(key)));
        //Comprobamos que haya en la RUTA1: 446, RUTA2: 414, RUTA3: 409 y en NORUTA: 418
        Long contadoresReferencia[] = {446L, 414L, 409L, 418L};
        Long contadoresCalculados[] = new Long[4];
        assertArrayEquals(contadores.values().toArray(contadoresCalculados),
                contadoresReferencia);
    }
}