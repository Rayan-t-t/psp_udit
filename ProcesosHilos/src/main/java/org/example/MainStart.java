package org.example;

public class MainStart {
    public static void main(String[] args) {
        //Creamos un objeto de tipo Thread que ejecutara la tarea HILO-A
        Thread t1 = new Thread(new TareaPrueba("Hilo-A"),
                "Hilo-A");

        Thread t2 = new Thread(new TareaPrueba("Hilo-B"),
                "Hilo-B");
        //Inicimaos el primer hilo
        t1.start();
        //Inicimaos el segundo hilo
        //Los dos hilos puede progresar concurrentemente
        t2.start();
        System.out.println("Fin del metodo main ("+ Thread.currentThread().getName()+")");
    }
}
