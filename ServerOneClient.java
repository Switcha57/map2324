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
import java.util.List;

public class ServerOneClient extends Thread{
    private Socket s;
    private DbAccess db = new DbAccess();
    private Data data;
    private HierachicalClusterMiner clustering = null;
    private ArrayList<Object> ob;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String tempfile;


    public ServerOneClient(Socket socket) throws Exception {
        s = socket;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        start();
    }

    public void run() {
        try {
            boolean flag = true;
            Connection c = db.getConnection();
            while (flag) {
                ArrayList<String> tb = getTables(c);
                out.writeObject(tb);
                int coso = (Integer) in.readObject(); //la prof ha mandato questo 0 al server, immagino serva per capire se è avvenuta effettivamente la connessione
                String tablename = (String) in.readObject();
                if (existTable(c, tablename ) && coso == 0) {
                    out.writeObject("OK");
                    data = new Data(tablename);
                    System.out.println("Tabella accettata\n");
                }else {
                    out.writeObject("Errore tabella inesitente");
                    System.out.println("Tabella non accettata\n");
                    continue;
                }
                System.out.println("In attesa della scelta (file o database)");

                int scelta = (Integer) in.readObject();
                System.out.println("Scelta: " + scelta);
                switch (scelta) {
                    case 1:
                        ArrayList<String> list = showFile();
                        out.writeObject(list);
                        String filename = (String) in.readObject();
                        System.out.println(filename);
                        if (existFile(filename)){
                            out.writeObject("OK");
                            clustering = HierachicalClusterMiner.loadHierachicalClusterMiner(filename);
                            out.writeObject(clustering.toString(data));
                            db.closeConnection();
                        }else {
                            out.writeObject("file inesistente");
                        }
                        flag = false;
                        break;
                    case 2:
                        do {
                            tempfile = (String) in.readObject();
                            if (existFile(tempfile)){
                                out.writeObject("File gia presente");
                            }else {
                                out.writeObject("File non esiste");
                            }
                        }while (existFile(tempfile));
                        String mess = "OK";
                        do {
                            int depth = (Integer) in.readObject();
                            System.out.println("Profondità: " + depth);
                            try {
                                clustering = new HierachicalClusterMiner(depth);
                            } catch (InvalidDepthException e) {
                                mess = e.getMessage();
                            }
                            scelta = (Integer) in.readObject();
                            out.writeObject(mess);
                        }while (!mess.equals("OK"));

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
    private static ArrayList<String> getTables (Connection conn) throws SQLException {
        ArrayList<String> tables = new ArrayList<>();
        DatabaseMetaData md = conn.getMetaData();
        ResultSet rs = md.getTables(null, null, null, new String[] {"TABLE"});
        while (rs.next()) {
            tables.add(rs.getString(3));
        }
        return tables;
    }

    private static boolean existFile (String filename) {
        File f = new File(".\\res\\" + filename); //percorso assoluto tanto per, dopo lo cambio
        if (f.exists() && !f.isDirectory()) {
            return true;
        }
        return false;
    }

    private static ArrayList<String> showFile (){
        ArrayList<String> list = new ArrayList<>();
        String dirpath = ".\\res\\";
        File dir = new File(dirpath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                list.add(file.getName());
            }
        }
        return list;
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