package mx.alura.screenmatch.modelos;

import mx.alura.screenmatch.exceptions.ErrorValueDuracionException;
import mx.alura.screenmatch.herramientas.TituloOmdbApi;

public class Titulo implements Comparable<Titulo> {
    /*
     * Manjeo directo de la Class con JSON
     * 
     * import com.google.gson.annotations.SerializedName;
     * 
     * @SerializedName("Title") Agregamos el alias del atributo del valor del JSON
     * private String nombre;
     */
    private String nombre;
    private int fechaDeLanzamiento;
    private boolean incluidoEnElPlan;
    private double sumaDeLasEvaluaciones;
    private int totalDeEvaluaciones;
    private int duracionEnMinutos;

    public Titulo(String nombre, int fechaDeLanzamiento) {
        this.nombre = nombre;
        this.fechaDeLanzamiento = fechaDeLanzamiento;
    }

    public Titulo(TituloOmdbApi titulo) throws ErrorValueDuracionException {
        this.nombre = titulo.title();
        this.fechaDeLanzamiento = Integer.valueOf(titulo.year());
        /*
         * Transformacion de Excepcion
         * Si es el caso de enviar informacion sin importar el dato de duración hacer
         * esta implementación
         * this.duracionEnMinutos = (titulo.runtime().contains("N/A")) ? 0
         * : Integer.parseInt(titulo.runtime().split(" ")[0]);
         */
        // Manejo de Excepciones
        if (titulo.runtime().contains("N/A")) {
            throw new ErrorValueDuracionException(
                    "Contenido del Atributo Duracion contiene N/A," +
                            "por seguimiento del proceso no sepodra mostrar información");
        }
        /*
         * Rescatamos el primer valor antes del espacio (" "), se realizaria de la
         * siguiente manera:
         * 60 min --> split("") --> ["60","min"] --> [0] --> "60" --> int --> 60
         */
        this.duracionEnMinutos = Integer.parseInt(titulo.runtime().split(" ")[0]);
    }

    public String getNombre() {
        return nombre;
    }

    public int getFechaDeLanzamiento() {
        return fechaDeLanzamiento;
    }

    public boolean isIncluidoEnElPlan() {
        return incluidoEnElPlan;
    }

    public int getDuracionEnMinutos() {
        return duracionEnMinutos;
    }

    public int getTotalDeEvaluaciones() {
        return totalDeEvaluaciones;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaDeLanzamiento(int fechaDeLanzamiento) {
        this.fechaDeLanzamiento = fechaDeLanzamiento;
    }

    public void setIncluidoEnElPlan(boolean incluidoEnElPlan) {
        this.incluidoEnElPlan = incluidoEnElPlan;
    }

    public void setDuracionEnMinutos(int duracionEnMinutos) {
        this.duracionEnMinutos = duracionEnMinutos;
    }

    public void muestraFichaTecnica() {
        System.out.println("Nombre de la película: " + nombre);
        System.out.println("Año de lanzamiento: " + fechaDeLanzamiento);
    }

    public void evalua(double nota) {
        sumaDeLasEvaluaciones += nota;
        totalDeEvaluaciones++;
    }

    public double calculaMediaEvaluaciones() {
        return sumaDeLasEvaluaciones / totalDeEvaluaciones;
    }

    @Override
    public String toString() {
        return this.getNombre() + "\t(" + getFechaDeLanzamiento() + ")" +
                ((getDuracionEnMinutos() > 0) ? " Duración : " + getDuracionEnMinutos() + " min" : "");
    }

    @Override
    public int compareTo(Titulo otroTitulo) {
        return this.getNombre().compareTo(otroTitulo.getNombre());
    }
}
