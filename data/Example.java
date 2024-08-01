package data;
import java.util.Arrays;
import static java.lang.Math.pow;

public class Example {
    private int length=0;
    private Double [] example;

    Example(int length)throws IllegalArgumentException{
        if(length<0){
            throw new IllegalArgumentException("Lunghezza deve essere maggiore di zero");
        }else{
            this.length = length;
            example = new Double[length];
        }
    }
    Example(){
        example = new Double[length];
    }
    double getExample(int index) throws ArrayIndexOutOfBoundsException{
        if(index<0 || index>length-1){
            throw new ArrayIndexOutOfBoundsException("Indice deve essere compreso tra 0 e " + (length-1));
        }else{
            return example[index];
        }

    }
    int getLength(){
        return length;
    }
    void setExample(int index , double v) throws ArrayIndexOutOfBoundsException{
        if(index<0 || index>length-1){
            throw new ArrayIndexOutOfBoundsException("Indice deve essere compreso tra 0 e " + (length-1));
        }else{
            example[index] = v;
        }

    }
    void setLength(int length) throws IllegalArgumentException {
        if(length<0){
            throw new IllegalArgumentException("Lunghezza deve essere maggiore di zero");
        }else{
            this.length = length;
        }

    }

    public double distance(Example newE) throws InvalidSizeException {
        int d=0;
        if(this.length == newE.getLength()){
            for(int i=0; i<length; i++){
                d+= pow(this.example[i]-newE.getExample(i),2);
            }

        }else{
            throw new InvalidSizeException("Dimensione dei due array diversa");
        }
        return d;
    }

    public String toString(){
        return Arrays.toString(example);
    }
}
