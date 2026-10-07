package org.example;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("==== UDIFLIX CATALOGO ====");
        System.out.println("Comprobando servicios ... ");
     //   ArrayList<Catalogo> catalogos = new ArrayList<>();
     //   catalogos.add(new Catalogo("Serie","127.0.0."));
      //  catalogos.add(new Catalogo("Anime","127.0.0.1"));
       // catalogos.add(new Catalogo("Terror","127.0.0."));
       // catalogos.add(new Catalogo("Romance","127.0.0.1"));
       // catalogos.add(new Catalogo("Slasher","127.0.0."));
       // System.out.println(Catalogo.pb);

        String [] [] contenidos= {
                {"Serie", "127.0.0."},
                {"Slasher", "127.0.0.1"},
                {"Terror", "127.0.0."},
                {"Comedio", "127.0.0.1"},
                {"Anime", "127.0.0."},
        };
        for (int i = 0; i < contenidos.length; i++){
            String nombre = contenidos[i][0];
            String direccion = contenidos[i][1];
            System.out.println("[CONTENIDO] "+nombre);



        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "ping","-n","1",direccion);
            pb.redirectErrorStream(true);
            Process proceso =pb.start();


            System.out.println("PID: "+proceso.pid());

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()));

            String line;
            while ((line = br.readLine()) != null){
                //System.out.println(line);
            };
            int codigo = proceso.exitValue();
            if(codigo==0){
                System.out.println("ESTADO: Servicio activo");
            }
            else {
                System.out.println("ESTADO: Servicio no disponible");}

        } catch (InterruptedIOException e){
            System.out.println("La ejecucuion fue interrumpida");
        } catch (IOException e){
            System.out.println("No se pudo lanzar");
        }}


    }
}