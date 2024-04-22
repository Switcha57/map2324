// default package
import java.lang.Math;
import java.util.Arrays;

public class Example {

    private Double [] example;

    /**
     * @param length dimensione dell'esempio
     * 
     * inizializza il vettore example come vettore di dimensione length
     */
    public Example(int length) {
        example = new Double[length];
    }

    public Double[] getExample() {
        return example;
    }

    public void setExample(Double[] example) {
        this.example = example;
    }

    public void set(int index,Double v){
        this.example[index] =v;
    }

    public Double get(int index){
        return this.example[index];
    }
    /*
     *  Input: newE : istanza di Example
        Output: calcola la distanza euclidea tra this.example e new.example
        Comportamento: restituisce il valore calcolato;

     */
    public Double distance(Example newE){
        
        Double dist =0.0;
        for (int i = 0; i < this.example.length; i++) {
            dist += Math.pow(this.example[i]-newE.example[i],2);
            
        }
        return dist;
    }

    @Override
    public String toString() {
        return Arrays.toString(example);
    }
        

}
