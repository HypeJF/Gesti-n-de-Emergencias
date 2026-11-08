package Gestion;
import java.time.Instant;

/* Esta clase gestiona las emergencias y las ordena por prioridad
* Usaré la interfaz Comparable<T>*/

public class Emergencia implements Comparable<Emergencia>{
    //este enum posee los 4 niveles de gravedad ordenados
    public enum NivelGravedad {
        CRITICO(4), GRAVE(3), MODERADO(2), LEVE(1);

        private final int peso;
        NivelGravedad(int peso) { this.peso = peso; }
        public int getPeso() { return peso; }
    }

    //variables inmutables
    private final int id;
    private final NivelGravedad gravedad;
    private final long tiempoLlamada;
    private final int distanciaGeografica;

    //constructor
    public Emergencia(int id, NivelGravedad gravedad, int distanciaGeografica) {
        this.id = id;
        this.gravedad = gravedad;
        this.distanciaGeografica = distanciaGeografica;
        this.tiempoLlamada = Instant.now().toEpochMilli(); //esto captura el tiempo que dure la llamada en milisegundos
    }

    //getters
    public int getId() { return id; }
    public NivelGravedad getGravedad() { return gravedad; }
    public long getTiempoLlamada() { return tiempoLlamada; }
    public int getDistanciaGeografica() { return distanciaGeografica; }

    @Override
    //metodo principal de la interfaz Comparable<T>
    public int compareTo(Emergencia otra) {
        //como primera opción: compara por gravedad
        int comparacionGravedad = Integer.compare(otra.gravedad.getPeso(), this.gravedad.getPeso());
        if(comparacionGravedad != 0) {
            return comparacionGravedad;}

        //compara por tiempo, segunda opción
        int comparacionTiempo = Long.compare(otra.tiempoLlamada, this.tiempoLlamada);
        if(comparacionTiempo != 0) {
            return comparacionTiempo;
        }

        //como última opción, compara por cercanía
        return Integer.compare(this.distanciaGeografica, this.distanciaGeografica);
    }

    @Override
    public String toString() {
        return "Emergencia #"+ id + "[" + gravedad + "]";
    }





}
