package org.example.udit.flix;

/**
 * UDITflix · Motor concurrente (pre-reto / base v0 del Reto 3)
 *
 * Simula la transcodificación de un vídeo. Implementa Runnable:
 * esta clase define QUÉ hace el hilo; el Thread define CÓMO se ejecuta.
 */

public class TareaTranscodificacion implements Runnable {
    private void procesador() {
        long inicio = System.currentTimeMillis();
        long contador = 0;

        while (System.currentTimeMillis() - inicio < 2000) {
            contador++;
        }
    
        System.out.println(
                Thread.currentThread().getName() +
                        " - Procesamiento CPU terminado. Contador: " + contador
        );
    }


    private final String nombreVideo;
    private final int duracionSimuladaMs;

    public TareaTranscodificacion(String nombreVideo, int duracionSimuladaMs) {
        this.nombreVideo = nombreVideo;
        this.duracionSimuladaMs = duracionSimuladaMs;
    }

    @Override
    public void run() {
        String hiloActual = Thread.currentThread().getName();
        int hiloActualPrio = Thread.currentThread().getPriority();
        System.out.println("--> [" + hiloActual + "] Iniciando: " + nombreVideo
                + " (" + duracionSimuladaMs + " ms) Prioridad: "+hiloActualPrio );

        try {
            // Simulamos el trabajo. Mientras duerme, el hilo está en TIMED_WAITING.
            Thread.sleep(duracionSimuladaMs);
        } catch (InterruptedException e) {
            System.err.println("!--> [" + hiloActual + "] " + nombreVideo + " CANCELADO (interrumpido).");
            Thread.currentThread().interrupt(); // restaurar el aviso de interrupción
            return; // IMPORTANTE: sin este return se imprimiría "COMPLETADO" aunque se cancelara
        }
       
        //Procesamiento (consume CPU)
        System.out.println("[" + hiloActual + "] Procesando CPU: " + nombreVideo);
        procesador();
        // Finalización
        System.out.println("<-- [" + hiloActual + "] ¡COMPLETADO! " + nombreVideo
                + " Prioridad: " + hiloActualPrio);


    }

    public String getNombreVideo() {
        return nombreVideo;
    }

    public int getDuracionSimuladaMs() {
        return duracionSimuladaMs;
    }
}
