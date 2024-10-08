package clustering;
import data.*;
import distance.*;

import java.io.Serializable;

/**
 * Classe che modella un insieme di Cluster.
 */
public class ClusterSet implements Serializable {
    /**
     * Array di oggetti Cluster.
     */
    private Cluster[] C;
    /**
     * Indice dell'ultimo cluster inserito.
     */
    private int lastClusterIndex=0;

    /**
     * Costruttore della classe ClusterSet.
     * @param k grandezza del cluster.
     */
    public ClusterSet(int k){
        if(k<0){
            throw new IllegalArgumentException("la grandezza deve essere maggiore di 0");
        }else{
            C=new Cluster[k];
        }
    }

    /**
     * Metodo che aggiunge un cluster al CluserSet.
     * @param c cluster da aggiungere.
     */
    public void add(Cluster c){
        for(int j=0;j<lastClusterIndex;j++)
            if(c==C[j])
                return;
        C[lastClusterIndex]=c;
        lastClusterIndex++;
    }

    /**
     * Metodo che recupera il cluster in un determinato indice.
     * @param i indice del cluster.
     * @return cluster selezionato.
     */
    public Cluster get(int i){
        if (i<0 || i>C.length-1){
            throw new ArrayIndexOutOfBoundsException("Indice deve essere compreso tra 0 e " + (C.length-1));
        }else{
            return C[i];
        }
    }

    /**
     * Metodo che ritorna l'indice dell'ultimo cluster.
     * @return indice dell'ultimo cluster.
     */
    public int getLastClusterIndex(){
        return lastClusterIndex;
    }

    /**
     * Metodo che converte un oggetto ClusterSet in stringa, nascondendo i dati.
     * @return stringa rappresentante il ClusterSet.
     */
    public String toString(){
        String str="";
        for(int i=0;i<C.length;i++){
            if (C[i]!=null){
                str+="cluster"+i+":"+C[i]+"\n";

            }
        }
        return str;
    }

    /**
     * Metodo che converte un oggetto ClusterSet in stringa, mostrando i dati.
     * @param data dati da mostrare.
     * @return stringa rappresentante il ClusterSet.
     */
    public String toString(Data data){
        String str="";
        for(int i=0;i<C.length;i++){
            if (C[i]!=null){
                str+="cluster"+i+":"+C[i].toString(data)+"\n";

            }
        }
        return str;

    }

    /**
     * Metodo che unisce due Cluster vicini sulla base di Average distance o Single-Link distance.
     * @param distance parametro indicante Single-Link o Average-Link distance.
     * @param data dati contenuti nel cluster.
     * @return ClusterSet unito.
     */
    public ClusterSet mergeClosestClusters(ClusterDistance distance, Data data) {
        double min_dist = Double.MAX_VALUE;
        double dist = 0;
        int c1 = 0;
        int c2 = 0;
        Cluster newC = new Cluster();
        Cluster [] array = new Cluster[2];
        ClusterSet NewSet = new ClusterSet(C.length-1);
        for(int i=0;i<C.length-1;i++){
            for (int j=i+1;j<C.length;j++){
                dist = distance.distance(C[i],C[j],data);
                if (dist<min_dist){
                    min_dist=dist;
                    array[0]=C[i];
                    array[1]=C[j];
                    c1=i;
                    c2=j;
                }
            }
        }

        for(int i=0;i<c1;i++){

            NewSet.add(this.get(i));

        }
        newC = C[c1].mergeCluster(C[c2]);
        NewSet.add(newC);

        for(int i=c1+1;i<C.length;i++){
            if (c2!=i){
                NewSet.add(this.get(i));
            }
        }
        return NewSet;
    }
}
