package mx.alura.screenmatch.exceptions;

public class ErrorValueDuracionException extends RuntimeException {
    
    private String mensaje;
    
    public ErrorValueDuracionException(String string) {
        this.mensaje = string;
    }

    @Override
    public String getMessage() {
        return this.mensaje;
    }

}
