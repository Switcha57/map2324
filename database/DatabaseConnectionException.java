package database;
/**
 * Classe indicante un eccezione personalizzata rappresentante una connessione non avvenuta con successo.
 */
public class DatabaseConnectionException extends Exception {
    /**
     * Costruttore della classe DatabaseConnectionException.
     * @param message messaggio da mostrare.
     */
    public DatabaseConnectionException(String message) {
        super(message);
    }
}
