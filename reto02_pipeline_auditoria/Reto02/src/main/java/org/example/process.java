package org.example;

public class process {
    public static void main(String[] args) {
        try {


        System.out.println("Iniciando ejecucion secuencial ....");
        long inicioSecuencial = System.currentTimeMillis();
        System.out.println("iniciando ejecución paralela...");

        long inicioParalela = System.currentTimeMillis();

        System.out.println("      ->Lanzando proceso 3 (!No esperamos¡)");
        Process p3 = new ProcessBuilder("ping","-n","2","8.8.8.8").start();
        System.out.println("      ->Lanzando proceso 4 (!No esperamos¡)");
        Process p4 = new ProcessBuilder("ping","-n","2","127.0.0.1").start();

        System.out.println("Bloqueando java para recojer resultados");

        p3.waitFor();
        p4.waitFor();
        if(p3.exitValue() == 0 && p4.exitValue()==0  ){
            Process note = new ProcessBuilder("notepad.exe").start();

        }
        else if (p3.exitValue() != 0 || p4.exitValue() != 0){
            Process note = new ProcessBuilder("calc.exe").start();
            }
        long finParalela = System.currentTimeMillis();
        System.out.println("🕛 Tiempo total paralelo: "+(finParalela-inicioParalela)+"ms\n");
    }catch (Exception e){

        }
    }
}
