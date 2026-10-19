package org.example;

//Nombre que identifica la tarea, ejm HILO-A

public class TareaPrueba implements Runnable {
    private final String nombre;
    public TareaPrueba(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        //Repetimos el bloque 3 veces
        for (int i = 1; i <= 3; i++) {
            //Mostrar el nombre de la tarea 3 veces
            //el hilo que ejecuta esta intruccion y el paso
            //con start() podremos ver el HILO-A o HILO-B
            //con run() llamado desde main, veremos"main".
            System.out.println(
                    nombre + "["+ Thread.currentThread().getName()+"] - Paso: "+ i
            );
            try {
                //Detiene temporalteme el hilo actual durante 100 ms
                Thread.sleep(100);

            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
