package org.example;

import java.io.IOException;

public class CodigoSalida {
 public static void main(String[] args) {
     System.out.println("=================");
     System.out.println("         COMPROBACIÓN DE SERVIDOR");
     System.out.println("=================");
     try{
         // Preparamos el proceso externo
         //vamos a ejecutar el comando "ping"
         // En Windows  "ping","-n","1","8.8.8.8"
         //-n -> realiza una sola comprobacion
         // 8.8.8.8 -> direccion
         ProcessBuilder pb = new ProcessBuilder(
                 "ping","-n","1",
                 "8.8.8.8"
         );
         //2. Lanzar el proceso
         //start() ejecuta el proceso externo
         //El resulatdo de start() es un objeto process
         Process proceso = pb.start();
         //3. Obtnenemos el PID
         //el pid nos permite conocer el identificador
         System.out.println("PID: "+ proceso.pid() );
         //4. ESPERMAOS AL QUE PROCESO TERMINE
         // waitfor() detiene nuestro programa java
         // hasta que el proceso termine
         //Ademas devuelve un numero entero
         int codigoSalida = proceso.waitFor();
         //MOSTRAMOS EL CODIGO DE SALIDA
         System.out.println("Codigo Salida: "+ codigoSalida);
         //6.-INTERPRETAMOS EL RESULTADO
         //Codigo = 0 resultado satisfactoro !=0 no satisfactorio
         if(codigoSalida==0){
             System.out.println("Estado: ACTIVO");
         }
         else{
             System.out.println("Estado: CAIDO");
         }
     } catch (IOException e) {
         System.out.printf("Error al abrir el proceso: ");

     }
     catch (InterruptedException e) {
         System.out.printf("La espera del proceso fue interrumpida: ");
     }
     System.out.println("==========================");
     System.out.println("  FINAL DE LA COMPROBACIÓN");
     System.out.println("==========================");
 }
}
