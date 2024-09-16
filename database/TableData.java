package database;

import data.Example;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
    public List<Example> getDistinctTransazioni(String table) {
        List<Example> distinctExamples = new ArrayList<>();
        try{
            Statement s = db.getConnection().createStatement();
            ResultSet r = s.executeQuery("select *" + " from "+ table);
            while(r.next()) {

                Example e = new Example();
                ResultSetMetaData rsmd = r.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                for (int i = 1; i <= columnsNumber; i++) {
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
        } catch (DatabaseConnectionException e) {
            throw new RuntimeException(e);
        }
        return distinctExamples;
    }

}
