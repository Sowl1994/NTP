package listado;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListadoEmpleados {
    /**
    * Dato miembro para almacenar a los empleados tal y como se encuentran
    * en el archivo de datos.txt
    */
    private List<Empleado> listadoArchivo;

    /**
    * Dato miembro para almacenar a los empleados como mapa con pares
    * (una vez reparados los datos leidos del archivo)
    * <dni - empleado>
    */
    private Map<String, Empleado> listado;

    /**
    *   Constructor de la clase
    * */
    public ListadoEmpleados(String ruta) throws IOException {
        //Recorremos el fichero linea a linea
        Stream<String> lineas = Files.lines(Paths.get(ruta), StandardCharsets.ISO_8859_1);
        //Por cada linea, llamamos a crearEmpleado
        listadoArchivo = lineas.map(this::crearEmpleado).collect(Collectors.toList());
    }

    /**
     *   Función encargada de crear empleados, para ello recibira por parametro
     *   una linea del fichero y aplicará el patrón correspondiente, que nos dará acceso
     *   a los datos individuales de cada empleado, los cuales guardaremos en una lista
     *   @return Empleado
     * */
    private Empleado crearEmpleado(String linea){
        //Obtenemos el patron para analizar el fichero
        Pattern patron = Pattern.compile(",+");
        //Creamos una lista con los datos de un empleado
        List<String> listaEmpleados = Arrays.stream(linea.split(patron.toString())).collect(Collectors.toList());
        //Devolvemos un Empleado
        return new Empleado(listaEmpleados.get(0),listaEmpleados.get(1),listaEmpleados.get(2),listaEmpleados.get(3));
    }

    /**
     *   Nos devuelve el tamaño de la lista
     *   @return int
     * */
    public int obtenerNumeroEmpleadosArchivo(){
        return listadoArchivo.size();
    }

    /**
     *   Funcion que nos dice si en la lista de DNIs hay alguno repetido
     *   @return boolean
     * */
    public boolean hayDnisRepetidosArchivo(){
        Stream<String> noreps = listadoArchivo.stream().map(Empleado::obtenerDni).distinct();
        /*if(obtenerNumeroEmpleadosArchivo() == noreps.count()) return false; else return true;*/
        return obtenerNumeroEmpleadosArchivo() != noreps.count();
    }

    /**
     *   Obtenemos un Map con los empleados agrupados por DNI
     *   @return Map<String, List<Empleado>>
     * */
    public Map<String, List<Empleado>> obtenerDnisRepetidosArchivo(){
        Map<String, List<Empleado>> diccionario = listadoArchivo.stream()
                .collect(Collectors.groupingBy(Empleado::obtenerDni));
        return diccionario;
    }

    /**
     *   Conseguimos el numero de empleados con el DNI repetidos
     *   @return int
     * */
    public int contarEmpleadosDnisRepetidos(){
       /* obtenerDnisRepetidosArchivo().forEach(
                (key,value)->{System.out.println(key+": "+value+" ");}
        );*/
        /*Obtenemos a los empleados con DNI repetidos*/
        //obtenerDnisRepetidosArchivo().values().stream().filter(value -> value.size()>1).forEach(System.out::println);

        //Obtenemos el flujo la lista de valores, a la que le ponemos un filtro para que solo coja los que tienen
        //mas de 1 valores
        Stream<List<Empleado>> listStream = obtenerDnisRepetidosArchivo().values()
                .stream()
                .filter(value -> value.size() > 1);

        return listStream.mapToInt(List::size).sum();
    }

    /**
     *   Método para reparar los DNIs repetidos
     * */
    public void repararDnisRepetidos(Map<String, List<Empleado>> listaRepeticion){
        //Sacamos directamente los DNIs repetidos
        Stream<List<Empleado>> listStream = obtenerDnisRepetidosArchivo().values()
                .stream()
                .filter(value -> value.size() > 1);
        //Recorremos las listas asignadas a un DNI con mas de 1 empleado y a cada empleado de esa lista se le asigna un nuevo DNI
        listStream.forEach(listasEmpleado->{
            listasEmpleado.stream().forEach(empleado->{
                empleado.asignarDniAleatorio();
            });
            //listasEmpleado.get(0).asignarDniAleatorio();

            //System.out.println("Tras la reparación, ¿hay DNIs repetidos? -> " + hayDnisRepetidosArchivo());
        });
    }

    /**
     *   Devuelve un boolean que será true si hay correos repetidos y false si es lo contrario
     *   @return boolean
     * */
    public boolean hayCorreosRepetidosArchivo(){
        Stream<String> noreps = listadoArchivo.stream().map(Empleado::obtenerCorreo).distinct();
        return obtenerNumeroEmpleadosArchivo() != noreps.count();
    }

    /**
     *   Método para obtener los correos repetidos
     *   @return Map<String, List<Empleado>>
     * */
    public Map<String, List<Empleado>> obtenerCorreosRepetidosArchivo(){
        Map<String, List<Empleado>> diccionario = listadoArchivo.stream()
                .collect(Collectors.groupingBy(Empleado::obtenerCorreo));
        return diccionario;
    }

    /**
     *   Método para contar los empleados con correos repetidos
     *   @return int
     * */
    public int contarEmpleadosCorreosRepetidos(){
        Stream<List<Empleado>> listStream = obtenerCorreosRepetidosArchivo().values()
                .stream()
                .filter(value -> value.size() > 1);

        return listStream.mapToInt(List::size).sum();
    }

    /**
     *   Método para reparar los correos repetidos
     * */
    public void repararCorreosRepetidos(Map<String, List<Empleado>> listaRepeticiones) {
        Stream<List<Empleado>> listStream = obtenerCorreosRepetidosArchivo().values()
                .stream()
                .filter(value -> value.size() > 1);
        //Recorremos las listas asignadas a un correo con mas de 1 empleado
        //y a cada empleado de esa lista se le asigna un nuevo correo
        listStream.forEach(listasEmpleado -> {
            listasEmpleado.stream().forEach(empleado -> {
                empleado.generarCorreoCompleto();
            });
        });

        System.out.println("Tras la reparación, ¿hay correos repetidos?: "+hayCorreosRepetidosArchivo());
    }

    /**
     *   Método para pasar el listadoArchivo al listado definitivo
     * */
    public void validarListaArchivo(){
        listado = new HashMap<>();
        listadoArchivo.stream().forEach(empleado -> {
            listado.put(empleado.obtenerDni(),empleado);
        });
    }

    /**
     *   Método para listar el listado final
     * */
    public void listarListado(){
        listado.forEach(
                (key,value)->{System.out.println(key+": "+value+" ");}
        );
    }

    /**
     *   Método para cargar un fichero de sectores y realizar la asignacion a los empleados
     * */
    public void cargarArchivoAsignacionSector(String ruta) throws IOException {
        Sector sector  = Sector.NOSECTOR;
        if (ruta.indexOf("SECTOR") != -1) {
            if (ruta.indexOf("1") != -1) {
                sector = Sector.SECTOR1;
            }else if (ruta.indexOf("2") != -1) {
                sector = Sector.SECTOR2;
            }
        }

        //Recorremos el fichero linea a linea
        Stream<String> lineas = Files.lines(Paths.get(ruta), StandardCharsets.ISO_8859_1);
        //Obtenemos el patron para analizar el fichero
        Pattern patron = Pattern.compile(".*[A-Z].*");

        //Por cada linea, llamamos a procesarAsignacionSector
        Sector finalSector = sector;
        lineas.forEach(linea -> procesarAsignacionSector(linea,patron, finalSector));
    }

    /**
     *   Método para cargar un fichero de rutas y realizar la asignacion a los empleados
     * */
    public void cargarArchivoAsignacionRuta(String ruta) throws IOException {
        Ruta rutaN  = Ruta.NORUTA;
        if (ruta.indexOf("RUTA") != -1) {
            if (ruta.indexOf("1") != -1) {
                rutaN = Ruta.RUTA1;
            }else if (ruta.indexOf("2") != -1) {
                rutaN = Ruta.RUTA2;
            }else if (ruta.indexOf("3") != -1) {
                rutaN = Ruta.RUTA3;
            }
        }

        //Recorremos el fichero linea a linea
        Stream<String> lineas = Files.lines(Paths.get(ruta), StandardCharsets.ISO_8859_1);
        //Obtenemos el patron para analizar el fichero
        Pattern patron = Pattern.compile(".*[A-Z].*");

        //Por cada linea, llamamos a procesarAsignacionSector
        Ruta finalRuta = rutaN;
        lineas.forEach(linea -> procesarAsignacionRuta(linea,patron, finalRuta));
    }

    /**
     *   Realiza el procesamiento de cada linea del fichero de ruta
     *   @return boolean
     * */
    private boolean procesarAsignacionRuta(String linea, Pattern patron, Ruta ruta){
        //Aplicamos el patron que pasamos por parametro
        List<String> lineaArreglada = Arrays.stream(linea.split(patron.toString())).collect(Collectors.toList());
        //Para cada DNI que obtenemos, si pertenece al listado, le asignamos un Sector
        lineaArreglada.forEach(dni -> {
            if (listado.containsKey(dni)){
                listado.get(dni).asignarRuta(ruta);
            }else{}
        });
        return listado.containsKey(linea);
    }

    /**
     *   Realiza el procesamiento de cada linea del fichero de sectores
     *   @return boolean
     * */
    private boolean procesarAsignacionSector(String linea, Pattern patron, Sector sector){
        //Aplicamos el patron que pasamos por parametro
        List<String> lineaArreglada = Arrays.stream(linea.split(patron.toString())).collect(Collectors.toList());
        //Para cada DNI que obtenemos, si pertenece al listado, le asignamos un Sector
        lineaArreglada.forEach(dni -> {
            if (listado.containsKey(dni)){
                listado.get(dni).asignarSector(sector);
            }else{}
        });
        return listado.containsKey(linea);
    }

    /**
     *   Obtiene los contadores de cada sectore
     *   @return Map<Sector, Map<Ruta,Long>>
     * */
    public Map<Sector, Map<Ruta,Long>> obtenerContadoresSectorRuta(){
        Map<Sector, Map<Ruta, Long>> contadores = Arrays.stream(Sector.values())
                .collect(Collectors.toMap(Function.identity(), sector -> obtenerContadoresRuta(sector)));

        return contadores;
    }

    /**
     *   Realiza los contadores de cada ruta
     *   @return Map<Ruta, Long>
     * */
    public Map<Ruta, Long> obtenerContadoresRuta(Sector sector){
        Map<Ruta,Long> contadores = listado.values().stream()
                .filter(empleado -> empleado.obtenerSector() == sector)
                .map(empleado -> empleado.obtenerRuta())
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.groupingBy(Function.identity(),TreeMap::new,Collectors.counting()));
        return contadores;
    }

    /**
     *   Obtiene los datos de sectores y realiza la suma de cada uno para saber el total de empleados totales por sector
     *   @return ArrayList<Long>
     * */
    public ArrayList<Long> obtenerContadoresSectores(){
        Map<Sector, Map<Ruta, Long>> contadores = obtenerContadoresSectorRuta();
        long sum1 = contadores.values().stream().mapToLong(value -> value.get(Ruta.RUTA1)).sum();
        long sum2 = contadores.values().stream().mapToLong(value -> value.get(Ruta.RUTA2)).sum();
        long sum3 = contadores.values().stream().mapToLong(value -> value.get(Ruta.RUTA3)).sum();
        long sumNR = contadores.values().stream().mapToLong(value -> value.get(Ruta.NORUTA)).sum();

        ArrayList<Long> lista = new ArrayList<>();
        lista.add(sum1);
        lista.add(sum2);
        lista.add(sum3);
        lista.add(sumNR);

        return lista;
    }

    /**
     *   Obtiene los datos de empleados que no tengan asignados ni sectores ni rutas
     *   @return List<Empleado>
     * */
    public List<Empleado> buscarEmpleadosSinSectorSinRuta(){
        List<Empleado> listaNoSR = listado.values().stream()
                .filter(empleado -> empleado.obtenerSector() == Sector.NOSECTOR)
                .filter(empleado -> empleado.obtenerRuta() == Ruta.NORUTA)
                .collect(Collectors.toList());
        return listaNoSR;
    }

    /**
     *   Obtiene los datos de empleados que no tengan asignados rutas dependiendo del sector
     *   @return List<Empleado>
     * */
    public List<Empleado> buscarEmpleadosSinRuta(Sector sector){
        List<Empleado> listaNoR  = listado.values().stream()
                .filter(empleado -> empleado.obtenerSector() == sector)
                .filter(empleado -> empleado.obtenerRuta() == Ruta.NORUTA)
                .collect(Collectors.toList());
        return listaNoR;
    }

    /**
     *   Obtiene los datos de empleados que no tengan asignados rutas de todos los sectores
     *   @return List<Empleado>
     * */
    public List<Empleado> buscarEmpleadosConSectorSinRuta() {
        List<Empleado> listaNoR = Arrays.stream(Sector.values())
                .map(sector -> {
                    return buscarEmpleadosSinRuta(sector);
                })
                .flatMap(empleados -> empleados.stream())
                .collect(Collectors.toList());

        return listaNoR;
    }

    /**
     *   Obtiene los datos de empleados que no tengan asignados sectores de la ruta pasada por parametros
     *   @return List<Empleado>
     * */
    public List<Empleado> buscarEmpleadosSinSector(Ruta ruta){
        List<Empleado> listaNoS  = listado.values().stream()
                .filter(empleado -> empleado.obtenerRuta() == ruta)
                .filter(empleado -> empleado.obtenerSector() == Sector.NOSECTOR)
                .collect(Collectors.toList());
        return listaNoS;
    }

    /**
     *   Obtiene los datos de empleados que no tengan asignados sectores de todas las rutas
     *   @return List<Empleado>
     * */
    public List<Empleado> buscarEmpleadosSinSectorConRuta(){
        List<Empleado> listaNoS = Arrays.stream(Ruta.values())
                .map(ruta -> {
                    return buscarEmpleadosSinSector(ruta);
                })
                .flatMap(empleados -> empleados.stream())
                .collect(Collectors.toList());
        return listaNoS;
    }
}
