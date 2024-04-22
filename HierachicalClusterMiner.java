public class HierachicalClusterMiner {

    private Dendrogram dendrogram;

    HierachicalClusterMiner(int depth) {
        dendrogram= new Dendrogram(depth);
    }

    public String toString() {
        return dendrogram.toString();
    }

    String toString(Data data) {
        return dendrogram.toString(data);
    }

    void mine(Data data, ClusterDistance distance){
        ClusterSet newSet = new ClusterSet(data.getNumberOfExamples());
        for(int i=0; i<data.getNumberOfExamples(); i++){
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
