import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class MultiThreadServer {
    static private int port;
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
