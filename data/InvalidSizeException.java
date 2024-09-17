package data;
/**
 * Classe indicante un eccezione personalizzata rappresentante una grandezza non valida.
 */
public class InvalidSizeException extends Exception {
    /**
     * Costruttore della classe InvalidSizeException.
     * @param s messaggio da mostrare.
     */
    public InvalidSizeException(String s) {
        super(s);
    }
}
