package clustering;

/**
 * Classe indicante eccezione personalizzata rappresentante una profondità non valida.
 */
public class InvalidDepthException extends Exception {
    /**
     * Costruttore della classe InvalidDepthException.
     * @param message messaggio da mostrare.
     */
    public InvalidDepthException(String message) {
        super(message);
    }
}
