package mx.alura.screenmatch.modelos;

import mx.alura.screenmatch.calculos.Clasificable;

public class Pelicula extends Titulo implements Clasificable {
    private String director;

    public Pelicula(String Nombre, int fechaDeLanzamiento){
        super(Nombre, fechaDeLanzamiento);
    }
    
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public int getClasificacion() {
        return (int) calculaMediaEvaluaciones() / 2;
    }
    /*
    //Actualizamos el llamado del método de impresión a String de la Class Título
    @Override
    public String toString() {
        return "Pelicula: " +this.getNombre()+" ("+getFechaDeLanzamiento()+")";
    }
    */
}
