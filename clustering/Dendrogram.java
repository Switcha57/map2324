package clustering;
import data.*;

import java.io.Serializable;

public class Dendrogram implements Serializable {
    private ClusterSet [] tree;

    /**
     * Costruttore della classe Dendogram.
     * @param length lunghezza del dendogramma.
     * @throws InvalidDepthException
     */
    Dendrogram(int length) throws InvalidDepthException{
        if(length > Data.getNumberOfExamples()){
            throw new InvalidDepthException("la profondità dell'albero è superiore al numero di esempi del dataset "+Data.getNumberOfExamples());
        }else{
            tree = new ClusterSet[length];
        }

    }

    /**
     * Metodo setter che inserisce un ClusterSet nel dendogramma.
     * @param c ClusterSet da aggiungere.
     * @param level livello a cui aggiungere un dendogramma.
     */
    void setClusterSet(ClusterSet c, int level){
        if(level<0 || level>tree.length-1){
            throw new ArrayIndexOutOfBoundsException("Indice deve essere compreso tra 0 e " + (tree.length-1));
        }else{
            tree[level] = c;
        }

    }

    /**
     * Metodo getter che restituisce un ClusterSet ad un determinato livello.
     * @param level livello da cui recuperare il ClusterSet.
     * @return ClusterSet al livello level.
     */
    ClusterSet getClusterSet(int level){
        if(level<0 || level>tree.length-1){
            throw new ArrayIndexOutOfBoundsException("Indice deve essere compreso tra 0 e " + (tree.length-1));
        }else{
            return tree[level];
        }
    }

    /**
     * Metodo getter che ritorna la profondità del dendogramma.
     * @return profondità del dendogramma.
     */
    int getDepth(){
        return tree.length;
    }

    /**
     * Metodo che traduce il dendogramma in stringa nascondendo i dati.
     * @return stringa rappresentante il dendogramma.
     */
    public String toString() {
        String v="";
        for (int i=0;i<tree.length;i++)
            v+=("level"+i+":\n"+tree[i]+"\n");
        return v;
    }
    /**
     * Metodo che traduce il dendogramma in stringa mostrando i dati.
     * @return stringa rappresentante il dendogramma.
     */
    public String toString(Data data) throws InvalidDepthException {
        String v="";
        if (tree.length > data.getNumberOfExamples()) {
            throw new InvalidDepthException("la profondità dell'albero è superiore al numero di esempi del dataset "+Data.getNumberOfExamples());
        }
        for (int i=0;i<tree.length;i++)
            v+=("level"+i+":\n"+tree[i].toString(data)+"\n");
        return v;
    }
}
