package clustering;
import data.*;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Cluster implements Iterable<Integer>, Cloneable, Serializable {

    private Set<Integer> clusteredData = new TreeSet<>();

    /**
     * Aggiunge un indice di un Example al cluster.
     * @param id indice dell'Example da aggiungere al cluster.
     */
    public void addData(int id){
        clusteredData.add(id);
    }

    /**
     * Ritorna la dimensione di clusteredData.
     * @return la dimensione di clusteredData.
     */
    public int getSize() {return clusteredData.size();
    }

    /**
     * Override del metodo clone per clonare un cluster
     * @return il cluster clonato
     */
    @Override
    public Object clone() {
        Cluster clone = new Cluster();
        Iterator<Integer> iterator = clusteredData.iterator();
        while(iterator.hasNext()){
            clone.addData(iterator.next());
        }
        return clone;
    }

    /**
     * Crea un nuovo cluster che è la fusione dei due cluster precedenti.
     * @param c il cluster da fondere.
     * @return il cluster che rappresenta la fusione dei cluster precedenti.
     */
    Cluster mergeCluster (Cluster c) {
        Cluster newC = (Cluster) this.clone();
        Iterator<Integer> it = c.iterator();
        while(it.hasNext()){
            newC.addData(it.next());
        }
        return newC;

    }

    /**
     * Rappresentazione in stringa della classe.
     * @return la rappresentazione in stringa.
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        Iterator<Integer> it = clusteredData.iterator();
        while(it.hasNext()){
            str.append(it.next());
            if (it.hasNext()) {
                str.append(",");
            }
        }
        return str.toString();
    }

    /**
     * Rappresentazione in stringa della classe.
     * @param data valori associati agli indici del cluster.
     * @return la rappresentazione in stringa.
     */
    public String toString(Data data){
        StringBuilder str = new StringBuilder();
        Iterator<Integer> it = clusteredData.iterator();
        while(it.hasNext()){
            str.append("<").append(data.getExample(it.next())).append(">");
        }
        return str.toString();
    }

    /**
     * Override del metodo iterator.
     * @return l'iteratore a clusteredData.
     */
    @Override
    public Iterator<Integer> iterator() {
        return clusteredData.iterator();
    }
}
