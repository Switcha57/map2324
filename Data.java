
public class Data {

    public static void main(String args[]) {
        Data trainingSet = new Data();
        System.out.println(trainingSet);
        Double[][] distancematrix = trainingSet.distance();
        System.out.println("Distance matrix:\n");
        for (int i = 0; i < distancematrix.length; i++) {
            for (int j = 0; j < distancematrix.length; j++)
                System.out.print(distancematrix[i][j] + "\t");
            System.out.println("");
        }

    }

    private Example[] data;

    private int numberOfExamples;

    Data() {

        // data

        data = new Example[5];
        Example e = new Example(3);
        e.set(0, 1.0);
        e.set(1, 2.0);
        e.set(2, 0.0);
        data[0] = e;

        e = new Example(3);
        e.set(0, 0.0);
        e.set(1, 1.0);
        e.set(2, -1.0);
        data[1] = e;

        e = new Example(3);
        e.set(0, 1.0);
        e.set(1, 3.0);
        e.set(2, 5.0);
        data[2] = e;

        e = new Example(3);
        e.set(0, 1.0);
        e.set(1, 3.0);
        e.set(2, 4.0);
        data[3] = e;

        e = new Example(3);
        e.set(0, 2.0);
        e.set(1, 2.0);
        e.set(2, 0.0);
        data[4] = e;

        // numberOfExamples
        numberOfExamples = 5;

    }

    public Example[] getData() {
        return data;
    }

    public int getNumberOfExamples() {
        return numberOfExamples;
    }

    /*
     * Input: indice di un esempio memorizzato in data
     * Output: l’esempio memorizzato in data[exampleIndex]
     * Comportamento: restituisce data[exampleIndex]
     */
    public Example getExample(int index) {
        return data[index];
    }

    /*
     * Input: //
     * Output: matrice triangolare superiore delle distanze Euclidee calcolate tra
     * gli
     * esempi memorizzati in data. Tale matrice va avvalorata usando il metodo
     * distance di
     * Example
     * Comportamento: restituisce la matrice triangolare superiore delle distanze
     */
    public Double[][] distance() {
        Double[][] distancematrix = new Double[numberOfExamples][numberOfExamples];
        for (int i = 0; i < numberOfExamples; i++) {
            for (int j = i; j < numberOfExamples; j++) {
                distancematrix[i][j] = data[i].distance(data[j]);
            }
        }
        return distancematrix;
    }

    public String toString() {
        String outp = "";
        for (int i = 0; i < numberOfExamples; i++) {
            outp += "%d: %s\n".formatted(i, data[i]);
        }
        return outp;
    }

}