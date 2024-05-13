package clustering;
import data.*;
public class Dendrogram {
    private ClusterSet [] tree;

    Dendrogram(int length) throws InvalidDepthException{
        if(length > Data.getNumberOfExamples()){
            throw new InvalidDepthException("la profondità dell'albero è superiore al numero di esempi del dataset "+Data.getNumberOfExamples());
        }else{
            tree = new ClusterSet[length];
        }

    }

    void setClusterSet(ClusterSet c, int level){
        if(level<0 || level>tree.length-1){
            throw new ArrayIndexOutOfBoundsException("Indice deve essere compreso tra 0 e " + (tree.length-1));
        }else{
            tree[level] = c;
        }

    }

    ClusterSet getClusterSet(int level){
        if(level<0 || level>tree.length-1){
            throw new ArrayIndexOutOfBoundsException("Indice deve essere compreso tra 0 e " + (tree.length-1));
        }else{
            return tree[level];
        }
    }

    int getDepth(){
        return tree.length;
    }

    public String toString() {
        String v="";
        for (int i=0;i<tree.length;i++)
            v+=("level"+i+":\n"+tree[i]+"\n");
        return v;
    }

    String toString(Data data) {
        String v="";
        for (int i=0;i<tree.length;i++)
            v+=("level"+i+":\n"+tree[i].toString(data)+"\n");
        return v;
    }
}
