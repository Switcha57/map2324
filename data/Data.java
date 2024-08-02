package data;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<Example>  data= new ArrayList<>();
    static int numberOfExamples=0;

    public Data(){
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

        numberOfExamples=5;
    }

    /**
     * Getter numero di esempi
     * @return numero di esempi
     */
    static public int getNumberOfExamples(){
        return numberOfExamples;
    }

    public Example getExample(int exampleIndex)throws ArrayIndexOutOfBoundsException{
        if(exampleIndex<0 || exampleIndex>numberOfExamples-1){
            throw new ArrayIndexOutOfBoundsException("indice deve essere compreso tra 0 e"+ (numberOfExamples-1));
        }else{
            return data[exampleIndex];
        }
    }

    public double [][] distance() throws InvalidSizeException {
        double [][] dis = new double[numberOfExamples][numberOfExamples];
        for(int i=0;i<numberOfExamples;i++){
            for(int j=i;j<numberOfExamples;j++){
                dis[i][j]=data[i].distance(data[j]);
            }

        }
        return dis;
    }

    public String toString(){
        StringBuilder s= new StringBuilder();
        for(int i=0;i<numberOfExamples;i++){
            s.append(Integer.toString(i)).append(":").append("[");
            for(int j=0;j<data[0].getLength();j++){
                s.append(Double.toString(data[i].getExample(j)));
                if (j<data[0].getLength()-1){
                    s.append(",");
                }
            }
            s.append("]").append("\n");
        }
        return s.toString();
    }
    public static void main(String [] args) throws Exception {
        Data trainingSet=new Data();
        System.out.println(trainingSet);
        double [][] distancematrix = trainingSet.distance();
        System.out.println("Distance matrix:\n");
        for(int i=0;i<distancematrix.length;i++) {
            for(int j=0;j<distancematrix.length;j++)
                System.out.print(distancematrix[i][j]+"\t");
            System.out.println("");
        }
    }
}