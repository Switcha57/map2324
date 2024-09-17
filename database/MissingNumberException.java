package database;
/**
 * Classe indicante un eccezione personalizzata rappresentante la presenza di attributi non numerici.
 */
public class MissingNumberException extends Exception {
    /**
     * Costruttore della classe MissingNumberException.
     * @param message messaggio da mostrare.
     */
    public MissingNumberException(String message) {
        super(message);
    }
}
