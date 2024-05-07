package data;

public class Data {
    Example [] data;
    int numberOfExamples=0;

    public Data(){
        data=new Example[5];
        Example e = new Example(3);
        e.setExample(0,1.0);
        e.setExample(1,2.0);
        e.setExample(2,0.0);
        data[0]=e;

        e = new Example(3);
        e.setExample(0,0.0);
        e.setExample(1,1.0);
        e.setExample(2,-1.0);
        data[1]=e;

        e = new Example(3);
        e.setExample(0,1.0);
        e.setExample(1,3.0);
        e.setExample(2,5.0);
        data[2]=e;

        e = new Example(3);
        e.setExample(0,1.0);
        e.setExample(1,3.0);
        e.setExample(2,4.0);
        data[3]=e;

        e = new Example(3);
        e.setExample(0,2.0);
        e.setExample(1,2.0);
        e.setExample(2,0.0);
        data[4]=e;

        numberOfExamples=5;
    }

    public int getNumberOfExamples(){
        return numberOfExamples;
    }

    public Example getExample(int exampleIndex){
        return data[exampleIndex];
    }

    public double [][] distance() throws Exception {
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