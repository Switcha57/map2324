package data;
import java.util.*;

import static java.lang.Math.pow;

/**
 * Classe che modella l'enitità esempio
 */
public class Example implements Iterable<Double>{
    private List<Double> example;

    /**
     * Costruttore della classe Example
     */
    public Example(){
        example = new LinkedList<>();
    }

    /**
     * Metodo che inizializza un iteratore per example
     * @return oggetto iteratore per example
     */
    public Iterator<Double> iterator() {
        return example.iterator();
    }

    /**
     * Metodo che restituisce l'elemento di example ad un determinato indice
     * @param index intero rappresentante l'indice
     * @return elemento di example alla posizione indicata da index
     * @throws ArrayIndexOutOfBoundsException
     */
    public double getExample(int index) throws ArrayIndexOutOfBoundsException{
            return example.get(index);
    }

    /**
     * Metodo che aggiunge un elemento ad example
     * @param v elemento da aggiungere ad example
     */
    public void addExample(double v) {
        example.add(v);

    }

    /**
     * Metodo che restituisce la grandezza di example
     * @return grandezza di example
     */
    public int getSize(){
        return example.size();
    }

    /**
     * Metodo che restituisce la distanza euclidea tra due liste
     * @param newE lista da cui calcolare la distanza euclidea
     * @return distanza euclidea tra due liste
     * @throws InvalidSizeException
     */
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

    /**
     * Metodo per convertire l'oggetto in stringa
     * @return stringa rappresentante l'oggetto
     */
    public String toString(){
        return Arrays.toString(example.toArray());
    }
}
