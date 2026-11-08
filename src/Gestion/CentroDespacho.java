package Gestion;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;

public class CentroDespacho {

    //variables inmutables
    //Cola Thread-safe para ordenar las emergencias
    private final PriorityBlockingQueue<Emergencia> emergenciasPendientes;
    //Mapa para ver los recursos ordenados en cola por tipo
    private final Map<Recurso.Tipo, ConcurrentLinkedQueue<Recurso>> recursosDisponibles;


    //constructor
    public CentroDespacho(){
        this.emergenciasPendientes = new PriorityBlockingQueue<>();
        this.recursosDisponibles = new ConcurrentHashMap<>();

        //una cola para cada tipo de recurso
        for (Recurso.Tipo tipo : Recurso.Tipo.values()) {
            recursosDisponibles.put(tipo, new ConcurrentLinkedQueue<>());
        }
    }

    //METODOS

    //los hilos del operador registrarán llamadas sin bloquear nada.
    public void registrarEmergencia(Emergencia emergencia){
        emergenciasPendientes.put(emergencia); //put es thread-safe
        System.out.println("[OP] Llamada registrada: " + emergencia);
    }

    //metodo para obtener siguiente emergencia
    public Emergencia obtenerProximaEmergencia() throws InterruptedException{
        return emergenciasPendientes.take(); //take bloquea el hilo si la cola está vacia
    }

    //agregar recursos del hospital
    public void agregarRecursoInventario(Recurso recurso){
        recursosDisponibles.get(recurso.getTipo()).add(recurso);
    }

    //cambia el estado del recurso
    public Recurso asignarRecurso(Recurso.Tipo tipo){
        Recurso recurso = recursosDisponibles.get(tipo).poll();
        /* poll() extrae y elimina de la cola el recurso para que
        no haya condicion de carrera
         */

        if(recurso != null){
            recurso.setEstado(Recurso.Estado.EN_CAMINO);
        }
        return recurso;
    }

    //cambia el estado a disponible y lo reasigna en la lista
    public void liberarRecurso(Recurso recurso){
        recurso.setEstado(Recurso.Estado.DISPONIBLE);
        recursosDisponibles.get(recurso.getTipo()).add(recurso);
        System.out.println("[WARN] Recurso liberado: " + recurso);
    }

    //ver lista de emergencias pendientes
    public int getTotalEmergenciasPendientes(){
        return emergenciasPendientes.size();
    }

    //ver lista de recursos disponibles
    public int getRecursoDisponibles(Recurso.Tipo tipo){
        return recursosDisponibles.get(tipo).size();
    }

}
