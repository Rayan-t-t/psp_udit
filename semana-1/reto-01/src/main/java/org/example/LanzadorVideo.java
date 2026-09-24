package org.example;

import java.io.IOException;

public class LanzadorVideo {
    public static void main(String[] args) {
        System.out.println("Solicitando al SO la creacion de un proceso nativo ...");
        //1.- ProcessBuilder es el puete entre java y el sistema operativo
        // Preparamos el comando en en windows el notepad.exe el bloc de notas https://www.youtube.com/watch?v=2dFmSGU0FpA
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start","https://www.youtube.com/watch?v=2dFmSGU0FpA");
        try {
            // start() es el gatillo. pide al planificador del sistema opertaivo que cree el proceso
            Process process = pb.start();
            System.out.println("Proceso en ejecución. PID: " + process.pid());
           

        }
    catch (IOException e) {
        e.printStackTrace();}
    }}
