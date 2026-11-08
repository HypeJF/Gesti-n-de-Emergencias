import Gestion.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        System.out.println("<<<<SISTEMA DE GESTION DE EMERGENCIAS ACTIVADO>>>>");

        //inicializacion de instancia
        CentroDespacho centro = new CentroDespacho();

        //poblar el inventario
        for (int i=1; i<=3; i++) {
            centro.agregarRecursoInventario(new Recurso(i, Recurso.Tipo.AMBULANCIA));
        }
        //acá se puede hacer lo mismo con equipamiento y medicos, pero son irrelevantes para el programa por ahora

        //Thread Pool   (motor de concurrencia)
        /* Esto sirve para dar rienda y control a los hilos que se usarán
        en el programa, como un director de orquesta
        */
        ExecutorService executor = Executors.newFixedThreadPool(3);

        //actores del sistema
        GeneradorLlamadas generador = new GeneradorLlamadas(centro);
        executor.execute(generador);

        Despachador despachador1 = new Despachador(centro);
        Despachador despachador2 = new Despachador(centro);

        executor.execute(despachador1);
        executor.execute(despachador2);

        //hilo ScheduledExcecutor para tareas regurales (monitoreo IRT)
        java.util.concurrent.ScheduledExecutorService monitorExecutor =
                java.util.concurrent.Executors.newSingleThreadScheduledExecutor();

        MonitorSistema monitor = new MonitorSistema(centro);
        //este monitor se puede ejecutar cada 5 segundos de forma constante
        monitorExecutor.scheduleAtFixedRate(monitor, 0, 5, java.util.concurrent.TimeUnit.SECONDS);

        //apagado seguro (el sistema se tiene que apagar o quedará infinitamente)
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n[SYST] Apagando...");
            generador.detener();
            despachador1.detener();
            despachador2.detener();
            monitorExecutor.shutdown();
            executor.shutdown();
        }));
    }
}