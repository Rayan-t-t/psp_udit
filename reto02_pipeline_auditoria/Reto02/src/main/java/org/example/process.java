package org.example;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class process {
    public static void main(String[] args) {
        try {
        System.out.println("iniciando ejecución paralela...");

        long inicioParalela = System.currentTimeMillis();
        //ArrayList<Process> processes = new ArrayList<Process>();

            System.out.println("Bloqueando java para recojer resultados");
          /*  int intento=0;
            int error=0;
            while(intento < 3){
                processes.add(new ProcessBuilder("ping","-n","1","-w","3000","127.0.0.1").start());
                processes.add(new ProcessBuilder("ping","-n","1","-w","3000","127.0.0.1").start());
                processes.add(new ProcessBuilder("ping","-n","1","-w","3000","192.0.2.1").start());
               for (Process p : processes){
                   p.waitFor();
                   System.out.println(p);
                   if(p.exitValue()!=0){
                       intento ++;

                       System.out.println("Reintento: "+intento+"/3");
                       error++;

                   }
                   if(error==3){
                       Process calc = new ProcessBuilder("calc.exe").start();
                   }
                   Thread.sleep(1000);
               }


            }
            if(error!=3){
                {Process note = new ProcessBuilder("notepad.exe").start();}
            }*/

            System.out.println("              -->Lanzando proceso 1");
            Process pb1 = new ProcessBuilder("ping","-n","1","-w","3000","127.0.0.1").start();
            System.out.println("              -->Lanzando proceso 2");
            Process pb2= new ProcessBuilder("ping","-n","1","-w","3000","192.0.2.1").start();
            int try1=0;
            int try2=0;
            pb1.waitFor();
            pb2.waitFor();
            while (pb1.exitValue() != 0) {
                try1++;
                System.out.println("Intento B" + try2);
                if (try1 == 3) {
                    break;
                }
                Thread.sleep(1000);
                pb1 = new ProcessBuilder("ping", "-n", "1", "-w", "3000", "127.0.0.1").start();
                pb1.waitFor();
            }
            while (pb2.exitValue() != 0) {
                try2++;
                System.out.println("Intento B" + try2);
                if (try2 == 3) {
                    break;
                }
                Thread.sleep(1000);
                pb2 = new ProcessBuilder("ping", "-n", "1", "-w", "3000", "192.0.2.1").start();
                pb2.waitFor();
            }

            if(try1==3 || try2==3){
                Process note = new ProcessBuilder("calc.exe").start();
            }
            else {
                Process calc = new ProcessBuilder("notepad.exe").start();

            }
        /*if(p3.exitValue() == 0 && p4.exitValue()==0  ){
            Process note = new ProcessBuilder("notepad.exe").start();
        }
        else //if (p3.exitValue() != 0 || p4.exitValue() != 0)
        {
            Process calc = new ProcessBuilder("calc.exe").start();
            }*/

        long finParalela = System.currentTimeMillis();
        System.out.println("🕛 Tiempo total paralelo: "+(finParalela-inicioParalela)+"ms\n");
    }catch (IOException e){
            System.out.println("Error: "+e.getMessage());

        }
        catch (InterruptedException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
}
