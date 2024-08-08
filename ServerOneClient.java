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

public class ServerOneClient {
    private static ServerSocket server;
    private static int port;
    private static DbAccess db = new DbAccess();
    private static Data data;
    private static HierachicalClusterMiner clustering = null;
    private static ArrayList<Object> ob;


    public static void main(String[] args) throws Exception {
        //port = Integer.parseInt(args[0]);
        port = 1234;
        server = new ServerSocket(port);
        Socket socket = server.accept();
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
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
                    out.writeObject(ob.getLast()); //non ho capito che dendogramma vuole (la struttura oppure il dendogramma con i dati) nel dubbio ho fatto quello completa
                    filename = (String) in.readObject();
                    clustering = (HierachicalClusterMiner) ob.getFirst();
                    clustering.salva(filename);
                    flag = false;
                    break;
            }
        }
        in.close();
        out.close();
    }

    private static boolean existTable (Connection conn, String table) throws SQLException {
        DatabaseMetaData md = conn.getMetaData();
        ResultSet rs = md.getTables(null, null, table, new String[] {"TABLE"});
        return rs.next();
    }

    private static boolean existFile (String filename) {
        File f = new File("D:\\git-workspace\\MAP\\" + filename); //percorso assoluto tanto per, dopo lo cambio
        if (f.exists() && !f.isDirectory()) {
            return true;
        }
        return false;
    }

    private static ArrayList<Object> ClusterDistance(int scelta, Data data, HierachicalClusterMiner clustering) throws Exception {
        ClusterDistance distance = null;
        ArrayList<Object> ob = new ArrayList<>();
        switch (scelta) {
            case 1:
                distance = new SingleLinkDistance();
                double [][] distancematrix=data.distance();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < distancematrix.length; i++) {
                    for (int j = 0; j < distancematrix[i].length; j++) {
                        sb.append(distancematrix[i][j]).append("\t");
                    }
                    sb.append("").append("\n");
                }
                sb.append("\n\n\n");
                clustering.mine(data,distance);
                String clustering1 = clustering.toString(data);
                sb.append(clustering1);
                ob.add(clustering);
                ob.add(sb.toString());
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