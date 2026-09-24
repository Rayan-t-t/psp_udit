package org.example;

import java.util.ArrayList;

public class Plataforma {
    private ArrayList<Episodio> catalogo;
    public Plataforma() {
        // MUY importante si no inicializamos la lsta con new  java lanzsara un error
        this.catalogo = new ArrayList<>();

    }
    //metodo agregar exigimos que el parametro sea estrictamente de tipo episodio
    public void agregar_episodio(Episodio e){
        this.catalogo.add(e);
    }
    public void procesar_catalogo(){
        for (Episodio episodio : this.catalogo){
            episodio.procesar();
        }
    }
}
