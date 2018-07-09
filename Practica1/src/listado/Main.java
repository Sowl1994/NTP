package listado;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String args[]) throws IOException {
        ListadoEmpleados le = new ListadoEmpleados("./data/datos.txt");
        System.out.println("Archivo cargado. Comprobacion de DNIs repetidos: "+le.hayDnisRepetidosArchivo());
        //System.out.println(le.obtenerNumeroEmpleadosArchivo());
        //System.out.println(le.contarEmpleadosDnisRepetidos());
        le.repararDnisRepetidos(le.obtenerDnisRepetidosArchivo());
        System.out.println("Tras la reparación, ¿hay DNIs repetidos?: " + le.hayDnisRepetidosArchivo());
        System.out.println("¿Hay correos repetidos?: "+le.hayCorreosRepetidosArchivo());
        System.out.println("Numero de correos repetidos: " + le.contarEmpleadosCorreosRepetidos());
        le.repararCorreosRepetidos(le.obtenerCorreosRepetidosArchivo());
        le.validarListaArchivo();
        //le.listarListado();

        le.cargarArchivoAsignacionSector("./data/asignacionSECTOR1.txt");
        le.cargarArchivoAsignacionSector("./data/asignacionSECTOR2.txt");
        le.cargarArchivoAsignacionRuta("./data/asignacionRUTA1.txt");
        le.cargarArchivoAsignacionRuta("./data/asignacionRUTA2.txt");
        le.cargarArchivoAsignacionRuta("./data/asignacionRUTA3.txt");

        Map<Ruta,Long> dicc = le.obtenerContadoresRuta(Sector.NOSECTOR);
        dicc.forEach((ruta, along) -> {
            //System.out.println("Ruta: " + ruta + " Contador: " + along);
        });

        /*int res1, res2, res3, res4;
        res1=le.buscarEmpleadosSinSector(Ruta.NORUTA).size();
        res2=le.buscarEmpleadosSinSector(Ruta.RUTA1).size();
        res3=le.buscarEmpleadosSinSector(Ruta.RUTA2).size();
        res4=le.buscarEmpleadosSinSector(Ruta.RUTA3).size();

        System.out.println("res1: "+res1+" res2: "+res2+ " res3: "+res3+ " res4: "+res4);*/

        List<Empleado> listaEmp = le.buscarEmpleadosSinSectorSinRuta();//le.buscarEmpleadosSinRuta(Sector.SECTOR1);
       // System.out.println(listaEmp.size());
        //listaEmp.forEach(System.out::println);

        Map<Sector, Map<Ruta,Long>> dicci = le.obtenerContadoresSectorRuta();
        /*dicci.forEach((sector, rutaLongMap) -> {
            System.out.println("Sector: " + sector + " Ruta: " + rutaLongMap);
        });*/
        System.out.println(le.obtenerContadoresSectores());
        //le.listarListado();
    }
}
