package org.example;

import java.io.IOException;

public class PildoraPararelismo {
    public static void main(String[] args) {
        System.out.println("===================");
        System.out.println("🚀 Pildora Técnica: Secuencial vs Paralelo");
        System.out.println("===================\n");
        try{
            System.out.println("Iniciando ejecucion secuencial ....");
            long inicioSecuencial = System.currentTimeMillis();

            System.out.println("      ->Lanzando proceso 1 (y esperando que muera)");
            Process p1 = new ProcessBuilder("ping","-n","2","127.0.0.1").start();
            p1.waitFor();//cuidado que java se congela aquí. el proceso 2 aun no existe
            //una vez que el proceso uno termnina porfin lazanamos el segundo proceso
            System.out.println("      ->Lanzando proceso 2 (y esperando que muera)");
            Process p2 = new ProcessBuilder("ping","-n","2","8.8.8.8").start();
            p2.waitFor();//aqui java se vuelve a congelar


            long fimSecuencial = System.currentTimeMillis();
            System.out.println("🕛 Tiempo total secuencial: "+(fimSecuencial - inicioSecuencial)+"ms\n");
            System.out.println("===================\n");
            //2.- El camino paralelo(ejecución solapada)

            System.out.println("iniciando ejecución paralela...");
            //reseteo del cronometro
            long inicioParalela = System.currentTimeMillis();
            //PASO A: apretamos todos los gatillos primero
            System.out.println("      ->Lanzando proceso 3 (!No esperamos¡)");
            Process p3 = new ProcessBuilder("ping","-n","2","8.8.8.8").start();
            System.out.println("      ->Lanzando proceso 4 (!No esperamos¡)");
            Process p4 = new ProcessBuilder("ping","-n","2","127.0.0.1").start();

            //PASO B: ahora si le decimos a java que recoja los resultados
            // como ya estan corriendo simultaneamente en el SO el tiempo de espera se solapa
            System.out.println("Bloqueando java para recojer resultados");
            p3.waitFor();
            p4.waitFor();
            long finParalela = System.currentTimeMillis();
            System.out.println("🕛 Tiempo total paralelo: "+(finParalela-inicioParalela)+"ms\n");


        }catch(IOException e){
            System.out.println("Error no se pudo lanzar el proceso");
        }
        catch (InterruptedException e){
            System.out.println("Error: La espera fue interrumpida de forma inesperada");
        }
    }
}
