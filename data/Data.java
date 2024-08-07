package data;

import database.DbAccess;
import database.EmptySetException;
import database.MissingNumberException;
import database.TableData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Costruttore di data
 */
public class Data {
    private List<Example> data;
    private static int numberOfExamples=0;
    public Data(String tableName) throws NoDataException, SQLException, EmptySetException, MissingNumberException {
        TableData td = new TableData(new DbAccess());
        data = td.getDistinctTransazioni(tableName);
        numberOfExamples = data.size();
        if (numberOfExamples==0) {
            throw new NoDataException("Nessun valore trovato");
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

    /*public static void main(String[] args) throws Exception {
        Data trainingSet = new Data();
        System.out.println(trainingSet);
        double[][] distancematrix = trainingSet.distance();
        System.out.println("Distance matrix:\n");
        for (int i = 0; i < distancematrix.length; i++) {
            for (int j = 0; j < distancematrix.length; j++)
                System.out.print(distancematrix[i][j] + "\t");
            System.out.println("");
        }
    }*/
}