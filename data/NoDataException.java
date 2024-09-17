package data;
/**
 * Classe indicante un eccezione personalizzata rappresentante l'assenza di dati.
 */
public class NoDataException extends Exception {
    /**
     * Costruttore della classe NoDataException.
     * @param s messaggio da mostrare.
     */
    public NoDataException(String s) {
        super(s);
    }
}
