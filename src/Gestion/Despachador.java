package Gestion;

/* Esta clase es un clasificador y organizador...
en caso de que no hayan recursos disponibles, no descarta el
caso sino que lo pasa a la cola de espera
 */
public class Despachador implements Runnable {

    //variables
    private final CentroDespacho centro;
    private volatile boolean activo = true;

    //constructor
    public Despachador(CentroDespacho centro) {
        this.centro = centro;
    }

    public void detener() {
        this.activo = false;
    }

    @Override
    public void run() {
        while (activo) {
            try{
                //toma la emergencia en cola más proxima y bloquea el resto
                Emergencia emergenciaActual = centro.obtenerProximaEmergencia();
                //asignar ambulancia a la emergencia sin errores
                Recurso ambulancia = centro.asignarRecurso(Recurso.Tipo.AMBULANCIA);
                if (ambulancia != null) {
                    System.out.println("[DESP] Asignando" + ambulancia + "a "+ emergenciaActual);
                    //Aqui se desplaza la emergencia a otro hilo para no bloquear el despacho
                    Thread hiloAtencion = new Thread(new AtencionEmergencia(centro, emergenciaActual, ambulancia));
                    hiloAtencion.start();
                } else  {
                    //en caso de que no hayan ambulancias, pasa a la cola
                    System.out.println("[WAIT] Sin recursos para "+ emergenciaActual + ". En espera...");
                    centro.registrarEmergencia(emergenciaActual);
                    Thread.sleep(500);
                }
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println("[SYS] Despachador interrumpido");
            }
        }

    }
}
