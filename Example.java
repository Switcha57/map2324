import java.lang.reflect.Array;
import java.util.Arrays;

import static java.lang.Math.pow;

public class Example {
    private int length=0;
    private Double [] example;

    public Example(int length){
        this.length = length;
        example = new Double[length];
    }
    public Example(){
        example = new Double[length];
    }
    public double getExample(int index){
        return example[index];
    }
    public int getLength(){
        return length;
    }
    public void setExample(int index , double v){
        example[index] = v;
    }
    public void setLength(int length){
        this.length = length;
    }

    public double distance(Example newE) throws Exception {
        int d=0;
        if(this.length == newE.getLength()){
            for(int i=0; i<length; i++){
                d+= pow(this.example[i]-newE.getExample(i),2);
            }

        }else{
            throw new Exception("Dimensione dell'array diversa");
        }
        return d;
    }

    public String toString(){
        return Arrays.toString(example);
    }
}
