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
        port = Integer.parseInt(args[0]);
        server = new ServerSocket(port);
        Socket socket = server.accept();
        ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        boolean flag = true;
        while (flag) {
            System.out.println("In attesa di una richiesta del client sulla porta " + port);
            String tablename = (String) in.readObject();
            if (existTable(db.getConnection(), tablename )) {
                out.writeObject("OK");
                data = new Data(tablename);
            }else {
                out.writeObject("Errore tabella inesitente");
            }
            int scelta = (Integer) in.readObject();
            String filename = (String) in.readObject();
            switch (scelta) {
                case 1:
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
                    clustering = new HierachicalClusterMiner(depth);
                    scelta = (Integer) in.readObject();
                    out.writeObject("OK"); // <- non ho capito il senso di questo "OK"
                    ob = ClusterDistance(scelta,data,clustering);
                    out.writeObject(ob.getFirst()); //non ho capito che dendogramma vuole (la struttura oppure il dendogramma con i dati) quindi li ho fatti entrambi, nel caso si cancella
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
        File f = new File("D:\\git-workspace\\MAP\\src\\" + filename); //percorso assoluto tanto per, dopo lo cambio
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
                    sb.append("");
                }
                ob.add(sb.toString());
                clustering.mine(data,distance);
                String clustering1 = clustering.toString();
                String clustering2 = clustering.toString(data);
                ob.add(clustering1); //<- da cancellare nel caso
                ob.add(clustering2); //<- da cancellare nel caso
                break;
            case 2:
                distance = new AverageLinkDistance();
                clustering.mine(data, distance);
                clustering1 = clustering.toString();
                clustering2 = clustering.toString(data);
                ob.add(clustering1); //<- da cancellare nel caso
                ob.add(clustering2); //<- da cancellare nel caso
                break;
        }
        return ob;
    }
}