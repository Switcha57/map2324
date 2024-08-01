package clustering;
import data.*;
import distance.*;
public class HierachicalClusterMiner {

    private Dendrogram dendrogram;

    public HierachicalClusterMiner(int depth) throws InvalidDepthException {
        dendrogram= new Dendrogram(depth);
    }

    public String toString() {
        return dendrogram.toString();
    }

    public String toString(Data data) {
        return dendrogram.toString(data);
    }

    public void mine(Data data, ClusterDistance distance) throws Exception {
        ClusterSet newSet = new ClusterSet(Data.getNumberOfExamples());
        for(int i=0; i<Data.getNumberOfExamples(); i++){
            Cluster newE = new Cluster();
            newE.addData(i);
            newSet.add(newE);
        }
        dendrogram.setClusterSet(newSet,0);

        for(int i=0; i<dendrogram.getDepth()-1; i++){
            dendrogram.setClusterSet(dendrogram.getClusterSet(i).mergeClosestClusters(distance,data),i+1);
        }
    }
}
