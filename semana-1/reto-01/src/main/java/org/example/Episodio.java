package org.example;

public class Episodio {
    //Atributos
    private String titulo;
    private int duracion;

    //CONSTRUCTOR : el molde que ejecuta al hacer un new Episodio
    public Episodio(String titulo, int duracion) {
        this.titulo = titulo;
        this.duracion = duracion;
    }

    //METODO :
    public void procesar(){
        System.out.printf("Iniciando procesamineto de: " + this.titulo +" ...");
        try{
            Thread.sleep(2000);//pausa la ejecucion en 2 seg=2000 milseg
        }catch(InterruptedException e){
            System.out.printf("Error al procesar el episodio");

        }
        System.out.println("Completado: "+ this.titulo );
    }
}
