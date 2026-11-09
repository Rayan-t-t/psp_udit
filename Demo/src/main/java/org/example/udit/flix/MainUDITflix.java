package org.example.udit.flix;

import java.util.ArrayList;
import java.util.Random;

/**
 * BASE DEL JUEVES (versión que se da al alumnado): 4 hilos creados "a mano".
 *
 * Pasos que se explican en la píldora:
 *   1) Crear las tareas (Runnable).
 *   2) Envolver cada tarea en un Thread con nombre propio.
 *   3) Lanzar con start()   (NUNCA run()).
 *   4) Esperar con join()   (para que main no dé el resultado antes de tiempo).
 *
 * Después de la píldora, el alumnado resuelve los mini-retos A, B y C sobre esta base.
 */
public class    MainUDITflix {
    //funcion para evitar la repeticion de dar prioridad  despues de crearlo en el array
    public static Thread crearHilo(Runnable tarea, String nombre, int prioridad) {
        Thread hilo = new Thread(tarea, nombre);
        hilo.setPriority(prioridad);
        return hilo;
    }
    public static void main(String[] args) throws InterruptedException {

        System.out.println("==================================================");
        System.out.println("   UDITflix BACKEND · MOTOR DE TRANSCODIFICACIÓN  ");
        System.out.println("==================================================");

        Random azar = new Random();
        // Duración aleatoria entre 1000 y 4000 ms: así el orden de llegada cambia cada vez.
        int d1 = 1000 + azar.nextInt(3001);
        int d2 = 1000 + azar.nextInt(3001);
        int d3 = 1000 + azar.nextInt(3001);
        int d4 = 1000 + azar.nextInt(3001);

        long inicio = System.currentTimeMillis();



        // 1) Tareas
        Runnable tarea1 = new TareaTranscodificacion("catalogo-metadatos ", d1);
        Runnable tarea2 = new TareaTranscodificacion("sesion-y-perfil ", d2);
        Runnable tarea3 = new TareaTranscodificacion("portadas-assets", d3);
        Runnable tarea4 = new TareaTranscodificacion("analiticas", d4);


        //ArrayList
        ArrayList<Thread> threads = new ArrayList<>();
        threads.add(crearHilo(tarea1, "DL-catalogo",Thread.MAX_PRIORITY));
        threads.add(crearHilo(tarea2, "DL-sesion",Thread.MAX_PRIORITY));
        threads.add(crearHilo(tarea3, "DL-portadas",Thread.NORM_PRIORITY));
        threads.add(crearHilo(tarea4, "DL-analitica",Thread.MIN_PRIORITY));

        //SE crea el Daemon
        Thread carga = new Thread(() -> {
            while (true) {
                System.out.println("Cargando...");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        carga.setDaemon(true);

        /* 2) Hilos con nombre
        Thread hilo1 = new Thread(tarea1, "WORKER-1");
        Thread hilo2 = new Thread(tarea2, "WORKER-2");
        Thread hilo3 = new Thread(tarea3, "WORKER-3");
        Thread hilo4 = new Thread(tarea4, "WORKER-4");
            */
        /*Asignación de prioridades Obsoleto
        hilo1.setPriority(Thread.MAX_PRIORITY);
        hilo2.setPriority(Thread.MAX_PRIORITY);
        hilo3.setPriority(Thread.NORM_PRIORITY);
        hilo4.setPriority(Thread.MIN_PRIORITY);*/

        // 3) Lanzamiento concurrente


        // BUCLE para lanzar
        System.out.println("[MAIN] Lanzando workers...\n");
        for(Thread t : threads){
            t.start();
        }
        //inicias el daemon
        carga.start();

        //Bucle para con join
        for(Thread t : threads){
            t.join();
        }
        System.out.println("TODO COMPLETADO ");
        /*
        System.out.println("[MAIN] Lanzando workers...\n");
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();

        // 4) Espera: main se bloquea hasta que cada hilo termina
        hilo1.join();
        hilo2.join();
        hilo3.join();
        hilo4.join();
         */


        long total = System.currentTimeMillis() - inicio;
        int masLento = Math.max(Math.max(d1, d2), Math.max(d3, d4));
        int suma = d1 + d2 + d3 + d4;

        System.out.println("\n==================================================");
        System.out.println(" [MAIN] Todos los módulos listos -> LOGIN HABILITADO");
        System.out.println(" Tiempo total real:          " + total + " ms");
        System.out.println(" Duración del más lento:     " + masLento + " ms");
        System.out.println(" Suma de todas (secuencial): " + suma + " ms");
        System.out.println("==================================================");
    }
}
