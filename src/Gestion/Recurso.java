package Gestion;

/*Esta clase gestiona los recursos del Hospital
(ambulancia, medico, equipamiento).
Además, define como se administrarán las asignaciones
en los hilos de la aplicación
 */

public class Recurso {
    public enum Tipo {
        AMBULANCIA, MEDICO, EQUIPAMIENTO
    }

    public enum Estado {
        DISPONIBLE, EN_CAMINO, OCUPADO
    }

    //variables inmutables
    private final int id;
    private final Tipo tipo;
    private volatile  Estado estado;  //para que se garantice la visibilidad inmediata

    //constructor
    public Recurso(int id, Tipo tipo) {
        this.id = id;
        this.tipo = tipo;
        this.estado = Estado.DISPONIBLE; //DISPONIBLE por default
    }

    //getters
    public int getId() { return id; }
    public Tipo getTipo() { return tipo; }
    public Estado getEstado() { return estado; }

    //synchronized ordena la ejecución, un hilo a la vez
    public synchronized void setEstado(Estado estado) { this.estado = estado; }


    @Override
    public String toString() {
        return tipo + " #" + id + " ("+ estado + ")";
    }
}
