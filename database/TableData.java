package database;

import data.Example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che si occupa recupare e descrivere gli elementi di un database.
 */
public class TableData {
    private DbAccess db;

    /**
     * Costruttore della classe TableData.
     * @param db oggetto rappresentante l'accesso al db.
     */
    public TableData(DbAccess db) {
        this.db = db;
    }

    /**
     * Metodo che interroga la tabella con nome table nel database e restituisce la
     * lista di Example memorizzata nella tabella.
     * @param table nome della tabella nel database.
     * @return lista di Example.
     */
    public List<Example> getDistinctTransazioni(String table) throws EmptySetException, MissingNumberException, SQLException {
        List<Example> distinctExamples = new ArrayList<>();
        try{
            Statement s = db.getConnection().createStatement();
            Statement s1 = db.getConnection().createStatement();
            ResultSet r = s.executeQuery("select *" + " from "+ table);
            ResultSet rs = s1.executeQuery("select COUNT(*) from " + table);
            int cont = 0;
            while (rs.next()) {
                cont = rs.getInt("COUNT(*)");
                System.out.println("contatore: "+cont);
                if (cont == 0){
                    throw new EmptySetException("Non sono  presenti dati nella tabella");
                }
            }
            rs.close();
            while(r.next()) {
                Example e = new Example();
                ResultSetMetaData rsmd = r.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                for (int i = 1; i <= columnsNumber; i++) {
                    if (rsmd.getColumnType(i) != Types.DOUBLE) {
                        throw new MissingNumberException("Sono presenti una o più colonne non contenenti valori reali");
                    }
                    e.addExample(r.getDouble(i));
                }
                distinctExamples.add(e);
            }
            r.close();
            s.close();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            throw ex;
        } catch (DatabaseConnectionException e) {
            throw new RuntimeException(e);
        }
        return distinctExamples;
    }

}
