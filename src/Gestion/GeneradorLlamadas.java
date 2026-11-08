package Gestion;

import java.util.Random;

//clase para ejecutar llamadas de forma aleatoria en un hilo único
public class GeneradorLlamadas implements Runnable{

    //variables
    private final CentroDespacho centro;
    private final Random random;
    private int contadorId = 1;
    private volatile boolean activo = true;

    //constructor
    public GeneradorLlamadas(CentroDespacho centro) {
        this.centro = centro;
        this.random = new Random();
    }

    public void detener() {
        this.activo = false;
    }

    public void run(){
        while(activo){
            try {
                //aleatorizar el tiempo con un thread sleep
                Thread.sleep(random.nextInt(3000)+1000);
                //aleatorizar el nivel de emergencia
                Emergencia.NivelGravedad gravedad = Emergencia.NivelGravedad.values()[random.nextInt(Emergencia.NivelGravedad.values().length)];
                //aleatorizar la distancia entre 1 y 20km
                int distancia = random.nextInt(20)+1;

                //creando la emergencia
                Emergencia nuevaEmergencia = new Emergencia(contadorId++, gravedad, distancia);
                centro.registrarEmergencia(nuevaEmergencia);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[SYST] Generador de llamadas interrumpido");
            }
        }

    }



}
