package data;

import database.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe che aggrega la classe Example.
 */
public class Data {
    private List<Example> data = new ArrayList<>();
    private static int numberOfExamples=0;
    /**
     * Costruttore di data
     * @param tableName nome della tabella.
     */
    public Data(String tableName) throws NoDataException{
        TableData td = new TableData(new DbAccess());
        try {
            data = td.getDistinctTransazioni(tableName);
            numberOfExamples = data.size();
        } catch (EmptySetException | MissingNumberException | SQLException e) {
            throw new NoDataException("Dati non trovati."+ e.toString().split(":")[1]);
        }
    }
    /**
     * Getter numero di esempi
     *
     * @return numero di esempi
     */
    static public int getNumberOfExamples() {
        return numberOfExamples;
    }
    /**
     * Getter di un example Sapendo l'indice
     * @param exampleIndex indice di example contenuto in uno cluster
     * @return example associato all'indice
     * @throws ArrayIndexOutOfBoundsException elemento non esistente
     */
    public Example getExample(int exampleIndex) throws ArrayIndexOutOfBoundsException {
        return data.get(exampleIndex);
    }
    /**
     * Restituisce la matrice triangolare superiore delle distanze
     * @return matrice triangolare superiore delle distanze Euclidee calcolate tra gli
     * esempi memorizzati in data.
     * @throws InvalidSizeException data non congrui
     */
    public double[][] distance() throws InvalidSizeException {
        double[][] dis = new double[numberOfExamples][numberOfExamples];
        for (int i = 0; i < numberOfExamples; i++) {
            for (int j = i; j < numberOfExamples; j++) {
                dis[i][j] = data.get(i).distance(data.get(j));
            }

        }
        return dis;
    }
    /**
     * Metodo toString
     * @return rappresentazione in stringa di Data
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < numberOfExamples; i++) {
            s.append(i).append(":").append("[");
            Iterator<Double> itExample = data.get(i).iterator();
            while (itExample.hasNext()) {
                s.append(itExample.next());
                if (itExample.hasNext()) {
                    s.append(",");
                }
            }
            s.append("]").append("\n");
        }
        return s.toString();
    }
}