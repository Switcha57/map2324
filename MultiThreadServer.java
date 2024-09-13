import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer {
    static private int port;

    /**
     * Metodo main che inizilizza un ServerSocket e attende una connessione dal client;
     * alla connesione procede con l'inizilizzazione di un oggetto ServerOneClient.
     * @param args porta di connessione.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //port = Integer.parseInt(args[0]);
        port = 1234;
        ServerSocket s = new ServerSocket(port);
        try {
            while (true) {
                Socket client = s.accept();
                try {
                    new ServerOneClient(client);
                } catch (Exception e) {
                    client.close();
                }
            }
        } finally {
            s.close();
        }
    }
}
