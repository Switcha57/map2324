import clustering.Cluster;
import clustering.HierachicalClusterMiner;
import clustering.InvalidDepthException;
import data.Data;
import data.InvalidSizeException;
import data.NoDataException;
import database.DatabaseConnectionException;
import database.DbAccess;
import database.EmptySetException;
import database.MissingNumberException;
import distance.AverageLinkDistance;
import distance.ClusterDistance;
import distance.SingleLinkDistance;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerOneClient extends Thread{
    private static Socket s;
    private static DbAccess db = new DbAccess();
    private static Data data;
    private static HierachicalClusterMiner clustering = null;
    private static ArrayList<Object> ob;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;


    public ServerOneClient(Socket socket) throws Exception {
        s = socket;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        start();
    }

    public void run() {
        try {
            boolean flag = true;
            while (flag) {
                int coso = (Integer) in.readObject(); //la prof ha mandato questo 0 al server, immagino serva per capire se è avvenuta effettivamente la connessione
                String tablename = (String) in.readObject();
                if (existTable(db.getConnection(), tablename ) && coso == 0) {
                    out.writeObject("OK");
                    data = new Data(tablename);
                }else {
                    out.writeObject("Errore tabella inesitente");
                }
                System.out.println("Tabella accettata\n");
                System.out.println("In attesa della scelta (file o database)");
                int scelta = (Integer) in.readObject();
                System.out.println("Scelta: " + scelta);
                switch (scelta) {
                    case 1:
                        String filename = (String) in.readObject();
                        if (existFile(filename)){
                            out.writeObject("OK");
                            clustering = HierachicalClusterMiner.loadHierachicalClusterMiner(filename);
                            out.writeObject(clustering);
                        }else {
                            out.writeObject("file inesistente");
                        }
                        flag = false;
                        break;
                    case 2:
                        int depth = (Integer) in.readObject();
                        System.out.println("Profondità: " + depth);
                        clustering = new HierachicalClusterMiner(depth);
                        scelta = (Integer) in.readObject();
                        out.writeObject("OK"); // <- non ho capito il senso di questo "OK"
                        ob = ClusterDistance(scelta,data,clustering);
                        out.writeObject(ob.getLast());
                        filename = (String) in.readObject();
                        clustering = (HierachicalClusterMiner) ob.getFirst();
                        clustering.salva(filename);
                        flag = false;
                        break;
                }
            }
            in.close();
            out.close();
        }catch (IOException | ClassNotFoundException | DatabaseConnectionException | SQLException | NoDataException |
                EmptySetException | MissingNumberException | InvalidDepthException e) {
            e.printStackTrace();
        } finally {
            try {
                s.close();
            }catch (IOException e) {
                System.out.println("Socket non chiuso");
            }
        }
    }
    private static boolean existTable (Connection conn, String table) throws SQLException {
        DatabaseMetaData md = conn.getMetaData();
        ResultSet rs = md.getTables(null, null, table, new String[] {"TABLE"});
        return rs.next();
    }

    private static boolean existFile (String filename) {
        File f = new File(".\\res\\" + filename); //percorso assoluto tanto per, dopo lo cambio
        if (f.exists() && !f.isDirectory()) {
            return true;
        }
        return false;
    }

    private static ArrayList<Object> ClusterDistance(int scelta, Data data, HierachicalClusterMiner clustering) throws InvalidDepthException {
        ClusterDistance distance = null;
        ArrayList<Object> ob = new ArrayList<>();
        switch (scelta) {
            case 1:
                distance = new SingleLinkDistance();
                clustering.mine(data,distance);
                String clustering1 = clustering.toString(data);
                ob.add(clustering);
                ob.add(clustering1);
                break;
            case 2:
                distance = new AverageLinkDistance();
                clustering.mine(data, distance);
                clustering1 = clustering.toString(data);
                ob.add(clustering);
                ob.add(clustering1);
                break;
        }
        return ob;
    }
}