package database;
/**
 * Classe indicante un eccezione personalizzata rappresentante un set vuoto.
 */
public class EmptySetException extends Exception {
    /**
     * Costruttore della classe EmptySetException.
     * @param message messaggio da mostrare.
     */
    public EmptySetException(String message) {
        super(message);
    }
}
