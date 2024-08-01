package data;
import java.util.*;

import static java.lang.Math.pow;

public class Example implements Iterable<Double>{
    private List<Double> example;

    Example(){
        example = new LinkedList<>();
    }
    public Iterator<Double> iterator() {
        return example.iterator();
    }

    double getExample(int index) throws ArrayIndexOutOfBoundsException{
        if(index<0 || index>example.size()-1){
            throw new ArrayIndexOutOfBoundsException("Indice deve essere compreso tra 0 e " + (example.size()-1));
        }else{
            return example.get(index);
        }

    }
    void addExample(double v) {
        example.add(v);

    }

    int getSize(){
        return example.size();
    }

    public double distance(Example newE) throws InvalidSizeException {
        int d=0;
        Iterator<Double> it = newE.iterator();
        Iterator<Double> it2 = example.iterator();
        if(this.getSize() == newE.getSize()){
            while(it.hasNext()){
                d+=pow(it.next()-it2.next(),2.0);
            }
        }else{
            throw new InvalidSizeException("Dimensione dei due array diversa");
        }
        return d;
    }

    public String toString(){
        return Arrays.toString(example.toArray());
    }
}
