package data;
/**
 * Classe indicante eccezione personalizzata rappresentante l'assenza di dati..
 */
public class NoDataException extends Exception {
    /**
     * Costruttore della classe InvalidDepthException.
     * @param s messaggio da mostrare.
     */
    public NoDataException(String s) {
        super(s);
    }
}
