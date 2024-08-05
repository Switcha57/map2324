package data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Data {
    private List<Example> data = new ArrayList<>();
    private static int numberOfExamples=0;
    public Data() {
        Example e = new Example();
        e.addExample(1.0);
        e.addExample(2.0);
        e.addExample(0.0);
        data.add(e);

        e = new Example();
        e.addExample(0.0);
        e.addExample(1.0);
        e.addExample(-1.0);
        data.add(e);

        e = new Example();
        e.addExample(1.0);
        e.addExample(3.0);
        e.addExample(5.0);
        data.add(e);

        e = new Example();
        e.addExample(1.0);
        e.addExample(3.0);
        e.addExample(4.0);
        data.add(e);

        e = new Example();
        e.addExample(2.0);
        e.addExample(2.0);
        e.addExample(0.0);
        data.add(e);

        numberOfExamples=data.size();
    }

    /**
     * Getter numero di esempi
     *
     * @return numero di esempi
     */
    static public int getNumberOfExamples() {
        return numberOfExamples;
    }

    public Example getExample(int exampleIndex) throws ArrayIndexOutOfBoundsException {
        return data.get(exampleIndex);
    }

    public double[][] distance() throws InvalidSizeException {
        double[][] dis = new double[numberOfExamples][numberOfExamples];
        for (int i = 0; i < numberOfExamples; i++) {
            for (int j = i; j < numberOfExamples; j++) {
                dis[i][j] = data.get(i).distance(data.get(j));
            }

        }
        return dis;
    }

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

    public static void main(String[] args) throws Exception {
        Data trainingSet = new Data();
        System.out.println(trainingSet);
        double[][] distancematrix = trainingSet.distance();
        System.out.println("Distance matrix:\n");
        for (int i = 0; i < distancematrix.length; i++) {
            for (int j = 0; j < distancematrix.length; j++)
                System.out.print(distancematrix[i][j] + "\t");
            System.out.println("");
        }
    }
}