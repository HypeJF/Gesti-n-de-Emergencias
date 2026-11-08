package Gestion;

//clase para gestionar los recursos y las emergencias, asignando y liberando
public class AtencionEmergencia implements Runnable {

    //variables
    private final CentroDespacho centro;
    private final Emergencia emergencia;
    private final Recurso recurso;

    //constructor
    public AtencionEmergencia(CentroDespacho centro, Emergencia emergencia, Recurso recurso) {
        this.centro = centro;
        this.emergencia = emergencia;
        this.recurso = recurso;
    }

    @Override
    public void run(){
        try {
            //simulacion traslado fisico
            System.out.println("[EN CAMINO] " + recurso + " se dirige a "+ emergencia);
            Thread.sleep(emergencia.getDistanciaGeografica()*100);
            //simulacion atencion medica
            recurso.setEstado(Recurso.Estado.OCUPADO);
            System.out.println("[ATENDIENDO] "+ recurso + "está atendiendo "+ emergencia);
            Thread.sleep(3000);
            //liberacion
            centro.liberarRecurso(recurso);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[ERROR] Gestion de Atencion Interrumpida en " + emergencia);
        }

    }
}
