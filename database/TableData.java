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
    public TableData(DbAccess db) {
        this.db = db;
    }

    public List<Example> getDistinctTransazioni(String table) throws SQLException,
            EmptySetException,MissingNumberException {
        List<Example> distinctExamples = new ArrayList<>();
        try{
            Statement s = db.getConnection().createStatement();

            // codice SQL: può generare l’eccezione SQLException

            ResultSet r = s.executeQuery(
                    "select *" + " from "+ table);

            while(r.next()) {
                // Capitalization doesn't matter:
                // In alternativa: accesso posizionale
                /*System.out.println(
                        r.getString(2) + ", "
                                + r.getString(1)
                                + ": " + r.getString(3) );
                 */
                Example e = new Example();
                ResultSetMetaData rsmd = r.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                for (int i = 1; i <= columnsNumber; i++) {
                    if (rsmd.getColumnType(i) != java.sql.Types.FLOAT) {
                        throw new MissingNumberException("e' presente una colonna non float");
                    }
                }
                for (int i = 1; i <= columnsNumber; i++) {
                    e.addExample(r.getDouble(i));
                }
                distinctExamples.add(e);
            }
            if(distinctExamples.size() == 0) {
                throw new EmptySetException("La tabella è vuota");
            }
            r.close();
            s.close(); // Also closes ResultSet

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        } catch (DatabaseConnectionException e) {
            throw new RuntimeException(e);
        }
        return distinctExamples;
    }

}
