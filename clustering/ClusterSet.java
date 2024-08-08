package clustering;
import data.*;
import distance.*;

import java.io.Serializable;

class ClusterSet implements Serializable {

    private Cluster[] C;
    private int lastClusterIndex=0;

    ClusterSet(int k){
        if(k<0){
            throw new IllegalArgumentException("la grandezza deve essere maggiore di 0");
        }else{
            C=new Cluster[k];
        }
    }

    void add(Cluster c){
        for(int j=0;j<lastClusterIndex;j++)
            if(c==C[j]) // to avoid duplicates
                return;
        C[lastClusterIndex]=c;
        lastClusterIndex++;
    }

    Cluster get(int i){
        if (i<0 || i>C.length-1){
            throw new ArrayIndexOutOfBoundsException("Indice deve essere compreso tra 0 e " + (C.length-1));
        }else{
            return C[i];
        }
    }

    int getLastClusterIndex(){
        return lastClusterIndex;
    }

    public String toString(){
        String str="";
        for(int i=0;i<C.length;i++){
            if (C[i]!=null){
                str+="cluster"+i+":"+C[i]+"\n";

            }
        }
        return str;
    }

    String toString(Data data){
        String str="";
        for(int i=0;i<C.length;i++){
            if (C[i]!=null){
                str+="cluster"+i+":"+C[i].toString(data)+"\n";

            }
        }
        return str;

    }

    ClusterSet mergeClosestClusters(ClusterDistance distance, Data data) throws Exception {
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

        /*for(int i=0;i<array[0].getSize();i++){
            newC.addData(array[0].getElement(i));
        }
        for(int i=0;i<array[1].getSize();i++){
            newC.addData(array[1].getElement(i));
        }*/
        return NewSet;
    }
}
