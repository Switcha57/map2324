import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe che si occupa di gestire le comunicazioni multiple tra client e server attraverso i thread.
 */
public class MultiThreadServer {
    static private int port;

    /**
     * Metodo main che inizializza un ServerSocket e attende una connessione dal client;
     * alla connesione procede con l'inizializzazione di un oggetto ServerOneClient.
     * @param args porta di connessione.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ServerSocket s = null;
        try {
            port = Integer.parseInt(args[0]);
            s = new ServerSocket(port);
        }catch ( NumberFormatException e ) {
            System.err.println("\""+args[0]+"\"" + " non è un valore valido di porta");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println("Server non avviabile sulla porta: " + port);
            System.exit(1);
        }
        try {
            while (true) {
                Socket client = s.accept();
                try {
                    new ServerOneClient(client);
                    System.out.println("Client accettato: "+ client.getInetAddress().getHostAddress()+ ":"+client.getPort());
                } catch (Exception e) {
                    client.close();
                }
            }
        } finally {
            s.close();
        }
    }
}
