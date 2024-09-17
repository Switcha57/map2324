import clustering.HierachicalClusterMiner;
import clustering.InvalidDepthException;
import data.Data;
import data.NoDataException;
import database.DatabaseConnectionException;
import database.DbAccess;
import distance.AverageLinkDistance;
import distance.ClusterDistance;
import distance.SingleLinkDistance;
import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

/**
 * Classe si occupa della comunicazione tra client e server.
 */
public class ServerOneClient extends Thread{
    private Socket s;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    /**
     * Costruttore della classe ServerOneClient.
     * @param socket socket di comunicazione.
     * @throws Exception
     */
    public ServerOneClient(Socket socket) throws Exception {
        s = socket;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        start();
    }

    /**
     * Metodo che istanzia un thread di comunicazione tra server e client.
     */
    public void run() {
        try {
            int controllo = -1;
            String tablename = "";
            boolean flag = true;
            DbAccess db = new DbAccess();
            Connection c = db.getConnection();
            while (flag) {
                int opzione = (Integer) in.readObject();
                String msg;
                Data data=null;
                if (opzione == 1) {
                    System.out.println("opzione: "+opzione);
                    msg = createTable(c);
                    controllo = (Integer) in.readObject();
                    tablename = msg;
                    if (controllo == 0) {
                        data = new Data(tablename);
                        System.out.println("Tabella accettata\n");
                    } else {
                        out.writeObject("Errore tabella inesitente");
                        System.out.println("Tabella non accettata\n");
                        continue;
                    }
                    System.out.println("Coso: "+controllo);
                    System.out.println("tablename: "+tablename);
                } else if (opzione == 2) {
                    System.out.println("opzione: "+opzione);
                    ArrayList<String> tb = getTables(c);
                    out.writeObject(tb);
                    controllo = (Integer) in.readObject();
                    tablename = (String) in.readObject();
                    if (controllo == 0) {
                        data = new Data(tablename);
                        out.writeObject("OK");
                        System.out.println("Tabella accettata\n");
                    }else {
                        out.writeObject("Errore tabella inesitente");
                        System.out.println("Tabella non accettata\n");
                        continue;
                    }
                }
                System.out.println("In attesa della scelta (file o database)");
                int scelta = (Integer) in.readObject();
                System.out.println("Scelta: " + scelta);
                String tempfile,filename;
                HierachicalClusterMiner clustering = null;
                switch (scelta) {
                    case 1:
                        ArrayList<String> list = showFile(tablename);
                        out.writeObject(list);
                        filename = tablename+"\\"+(String) in.readObject();
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
                            tempfile = tablename+"\\"+(String) in.readObject();
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

                        ArrayList<Object> ob = ClusterDistance(scelta, data, clustering);
                        out.writeObject(ob.getLast());
                        filename =  (String) in.readObject();
                        clustering = (HierachicalClusterMiner) ob.getFirst();
                        clustering.salva(tablename, filename);
                        flag = false;
                        break;

                }
            }
            in.close();
            out.close();
        }catch (IOException | ClassNotFoundException | DatabaseConnectionException | SQLException | NoDataException |
                InvalidDepthException e) {
            System.out.println("Il client ha interrotto la connessione con l'errore:" + e);
            try {
                out.writeObject(e.toString());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } finally {
            try {
                s.close();
            }catch (IOException e) {
                System.out.println("Socket non chiuso");
            }
        }
    }

    /**
     * Metodo che controlla l'esistenza di una tabella nel database.
     * @param conn oggetto connessione del server.
     * @param table nome della tabella.
     * @return vero se esiste, falso altrimenti.
     * @throws SQLException
     */
    private static boolean existTable (Connection conn, String table) throws SQLException {
        DatabaseMetaData md = conn.getMetaData();
        ResultSet rs = md.getTables(null, null, table, new String[] {"TABLE"});
        return rs.next();
    }

    /**
     * Metodo che restituisce un ArrayList contenente i nomi delle tabelle.
     * @param conn oggetto di connessione al server.
     * @return ArrayList contenente i nomi delle tabelle.
     * @throws SQLException errore nella query
     */
    private static ArrayList<String> getTables (Connection conn) throws SQLException {
        ArrayList<String> tables = new ArrayList<>();
        DatabaseMetaData md = conn.getMetaData();
        ResultSet rs = md.getTables("MapDb", null, null, new String[] {"TABLE"});
        while (rs.next()) {
            tables.add(rs.getString(3));
        }
        return tables;
    }

    /**
     * Metodo che controlla l'esistenza di un file.
     * @param filename nome del file.
     * @return vero se esiste, falso altrimenti.
     */
    private static boolean existFile (String filename) {
        File f = new File(".\\res\\" + filename);
        return f.exists() && !f.isDirectory();
    }

    /**
     * Metodo che restituisce un ArrayList contenente i nomi dei file.
     * @param Tablename directory contenente i file.
     * @return ArrayList contenente i nomi dei file.
     */
    private static ArrayList<String> showFile (String Tablename){
        ArrayList<String> list = new ArrayList<>();
        String dirpath = ".\\res\\"+ Tablename+"\\";
        File dir = new File(dirpath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                list.add(file.getName());
            }
        }
        return list;
    }

    /**
     * Metodo che, in base alla scelta, effettua il mine su un dendogramma e salva il dendogramma da minare su file.
     * @param scelta scelta dell'utente rappresentate la distanza da utilizzare (Single-Link Distance o Average-Link Distance)
     * @param data dati del dendogramma.
     * @param clustering oggetto su cui effettuare il mine.
     * @return ArrayList contenente sia il dendogramma normale che minato.
     * @throws InvalidDepthException
     */
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

    /**
     * Metodo che crea una tabella nel database e la popola.
     * @param conn oggetto di connessione al db.
     * @return nome della tabella.
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private String createTable(Connection conn) throws IOException, ClassNotFoundException, SQLException {
        Statement stmt = conn.createStatement();
        String tablename = (String) in.readObject();
        String query1 = (String) in.readObject();
        String query2 = (String) in.readObject();
        try {
            stmt.executeUpdate(query1);
            stmt.executeUpdate(query2);
            out.writeObject("OK");
        } catch (SQLException e) {
            out.writeObject(e);
        }
        return tablename;
    }
}