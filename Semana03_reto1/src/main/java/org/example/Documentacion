package org.example;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;

public class PildoraMonitor {
    public static void main(String[] args) {
        System.out.println("==== Monitor UDIFLIX ====");
        System.out.println("Comprobando servicios ... ");
        //TRY / CATCH
        //lanzar un program externo o esperar a que termnie puede FALLAR
        try {
            //PASO 1: PREPAR EL PROCESO(todavia no se ejecuta)
            //Proces builder es el encargado que prepara la orden
            // que le daremos al sitema operativo
            //es como rellenar un formulario
            //"ping" -> el program que queremos ejecutar
            //"-n" -> es la opcion de windows numero de intentos(n veces)
            // "1" -> haz solo un intento(asi termina mas rapido)
            //"127.0.0.1" es a quein hacemos ping : nuestro propio ordenador
            //(siempre responde,simula un servicio ACTIVO)
            //OJO "n" solo vale en windos. en Linux y Mac "-c"
            ProcessBuilder pb = new ProcessBuilder(
                    "ping","-n","1","127.0.0.1");



            //PASO 2: UNIR LOS DOS CANALES DE SALIDA
            //TODO  programa tiene dos canales por los que "habla"
            // -salida normal (lo que funciona bien)
            // -salida de error (los mensajes de error)
            // pb.redirectErrorStream(true); lo juntamos en uno solo
            //asi leyendo un unico canal leemos todo lo que el proceso diga
            // sea un resultado normal o error
            pb.redirectErrorStream(true);


            //PASO 3:Lanzar el proceso
            //start() es el boton de enviar.Ahora si el SO crea un programa nuevo "ping"
            // con su propia memoria , separando de nuestro java.
            //proces es el botom con el que controlamos ese programa
            Process proceso =pb.start();


            //PASO 4: MOSTAR EL PID
            //PID : Process IDentifier. El dni del proceso
            System.out.println("PID: "+proceso.pid());

            //PASO 5: Preapar la lectura de lo que el proceso  dice

            //El proceso ping escribe su propia consola, que java no lo ve
            // Para escucharlo nos conectamos a su sailda con una cadena
            //proceso.getInputStream() -> es la "tuberia" por la que sale
            // el texto del proceso(en Bytes)
            //new InputStreamerReader(..) -> traduce esos bytes a letras.
            // new Buffer reader(...) -> nos deja leer lina a linea
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()));
            //variable donde guardamos cada linea que vayamos leyendo
            //todavia esta vacia
            String line;

            //PASO 6 : leer todo lo que el proceso va escribiendo
            while ((line = br.readLine()) != null){
                System.out.println(line);
            };
            //paso 7 : espera a que el proceso termine
            // wait for es lo que nos garantiza el codigo de salida
            int codigo = proceso.exitValue();
            //PASO 8 : interpretar el resultado
            if(codigo==0){
                System.out.println("ESTADO: Servicio activo");
            }
            else {
                System.out.println("ESTADO: Servicio no disponible");}

        }catch (IIOException e){
            System.out.println("No se pudo lanzar");
        }
        catch (InterruptedIOException e){
            System.out.println("La ejecucuion fue interrumpida");
        }
    }
}
