package Gestion;

//clase para implementar una pantalla de visualizacion de datos IRT
public class MonitorSistema implements Runnable {

    //variable
    private final CentroDespacho centro;

    //constructor
    public MonitorSistema(CentroDespacho centro) {
        this.centro = centro;
    }

    @Override
    public void run() {
        System.out.println("\n---------------------------------\nMONITOREO EN TIEMPO REAL DEL SISTEMA\n---------------------------------");
        int pendientes = centro.getTotalEmergenciasPendientes();
        System.out.println("Emergencias Pendientes: " + pendientes);
        int ambulanciasDisponibles = centro.getRecursoDisponibles(Recurso.Tipo.AMBULANCIA);
        System.out.println("Ambulancias Disponibles: "+ ambulanciasDisponibles);

        System.out.println("-------------------------------------");


    }
}
