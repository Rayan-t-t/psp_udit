package org.example;

import java.io.IOException;

public class LanzadorProcesos {
    public static void main(String[] args) {
        System.out.println("Solicitando al SO la creacion de un proceso nativo ...");
        //1.- ProcessBuilder es el puete entre java y el sistema operativo
        // Preparamos el comando en en windows el notepad.exe el bloc de notas
        ProcessBuilder pb = new ProcessBuilder("notepad.exe");
        try {
            // start() es el gatillo. pide al planificador del sistema opertaivo que cree el proceso
            Process process = pb.start();
            System.out.println("Proceso en ejecución. PID: "+ process.pid());
            //waitFor() pausa nuestro hilo de java hasta que el usario cierre el blog de notas
            int salida = process.waitFor();
            // o significa  cierre limpio. Cualquier otro numero indica error ao cierre forzoso
            System.out.println("El proceso externo finalizo con codigo: " + salida);
        }catch (IOException e){
            System.out.println("Error el sistema operativo no encuentra el programa:");
        }catch (InterruptedException e){
            System.out.println("Error : El proceso fue interrumpido repentinamnete");
        }
    }
}
