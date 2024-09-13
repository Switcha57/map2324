package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




public class TableSchema {
    private DbAccess db;

    /**
     * Inner Classe rappresentante l'oggetto colonna della tabella.
     */
    public class Column{
        private String name;
        private String type;

        /**
         * Costruttore della classe Column.
         * @param name nome della colonna.
         * @param type tipo della colonna
         */
        Column(String name,String type){
            this.name=name;
            this.type=type;
        }

        /**
         * Metodo getter che restituisce il nome della colonna.
         * @return nome della colonna.
         */
        public String getColumnName(){
            return name;
        }

        /**
         * Metodo che controlla se il tipo è numerico.
         * @return true se numerico, false altrimenti.
         */
        public boolean isNumber(){
            return type.equals("number");
        }

        /**
         * Metodo che restituisce il nome e il tipo della colonna.
         * @return stringa indicante il nome e il tipo della colonna.
         */
        public String toString(){
            return name+":"+type;
        }
    }
    List<Column> tableSchema=new ArrayList<>();

    /**
     * Costruttore della classe TableSchema.
     * @param db parametro rappresentante l'accesso al db.
     * @param tableName nome della tabella.
     * @throws SQLException
     * @throws DatabaseConnectionException
     */
    public TableSchema(DbAccess db, String tableName) throws SQLException, DatabaseConnectionException{
        this.db=db;
        HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
        //http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
        mapSQL_JAVATypes.put("CHAR","string");
        mapSQL_JAVATypes.put("VARCHAR","string");
        mapSQL_JAVATypes.put("LONGVARCHAR","string");
        mapSQL_JAVATypes.put("BIT","string");
        mapSQL_JAVATypes.put("SHORT","number");
        mapSQL_JAVATypes.put("INT","number");
        mapSQL_JAVATypes.put("LONG","number");
        mapSQL_JAVATypes.put("FLOAT","number");
        mapSQL_JAVATypes.put("DOUBLE","number");



        Connection con=db.getConnection();
        DatabaseMetaData meta = con.getMetaData();
        ResultSet res = meta.getColumns(null, null, tableName, null);

        while (res.next()) {

            if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
                tableSchema.add(new Column(
                        res.getString("COLUMN_NAME"),
                        mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
                );



        }
        res.close();



    }

    /**
     * Metodo che restituisce il numero di attributi.
     * @return numero di attributi.
     */
    public int getNumberOfAttributes(){
        return tableSchema.size();
    }

    /**
     * Metodo che restituisce la colonna di una tabella.
     * @param index indice della colonna.
     * @return colonna della tabella.
     */
    public Column getColumn(int index){
        return tableSchema.get(index);
    }


}




