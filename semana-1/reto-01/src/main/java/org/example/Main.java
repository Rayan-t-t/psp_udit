package org.example;
import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
// toda la logica de  negocio tirada al Main
        //los diamantes <> obliga a que la lista solo acpete episodios
        System.out.println("---  INICIANDO MULTIVERSO");
        ArrayList<Episodio> catalogo = new ArrayList();
        catalogo.add(new Episodio("Diseño 2D", 40));
        catalogo.add(new Episodio("Animación",10));
        catalogo.add(new Episodio("Paciente",90));
        //2.- medicion de tiempo
        //Usamos 'long' porque los miligseg son desde 1970 forman un numero tan gigantesco que no entra en la memoria int normal
        long inicio =System.currentTimeMillis();
        for(Episodio ep : catalogo){
            ep.procesar();//aqui ocurre el bloque de 2 seg que esat en episodio procesar

        }
        long fin = System.currentTimeMillis();
        long totalSegundos = ((fin-inicio)/1000);

        System.out.println("Total de segundos: "+ totalSegundos+" segundos de bloqueos");
    }
}