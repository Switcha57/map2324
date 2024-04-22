
class HierachicalClusterMiner {

    private Dendrogram dendrogram;

    HierachicalClusterMiner(int depth) {
        dendrogram = new Dendrogram(depth);

    }
    /*
     * Comportamento: crea il livello base (livello 0) del dendrogramma che
     * contiene l’istanze di ClusterSet che rappresenta ogni esempio in un cluster
     * separato; per tutti i livelli successivi del dendrogramma (level>=1 e level <
     * dendrogram.getDepth()) costruisce l’istanza di ClusterSet che realizza la
     * fusione dei
     * due cluster più vicini nella istanza del ClusterSet memorizzata al livello
     * level-1 del
     * dendrogramma (usare mergeClosestClusters di ClusterSet); memorizza l’istanza
     * di
     * ClusterSet ottenuta per fusione nel livello level del dendrogramma.
     */

    public void mine(Data data, ClusterDistance distance) {
        // creo livello 0
        ClusterSet level0 = new ClusterSet(data.getNumberOfExamples());
        for (int i = 0; i < data.getNumberOfExamples(); i++) {

            Cluster singleE = new Cluster();
            singleE.addData(i);
            level0.add(singleE);
        }

        this.dendrogram.setClusterSet(0, level0);

        // livelli Successivi
        for (int i = 1; i < dendrogram.getDepth(); i++) {
            dendrogram.setClusterSet(i, dendrogram.getClusterSet(i - 1).mergeClosestClusters(distance, data));
        }

    }

    public String toString() {
        return dendrogram.toString();
    }

    String toString(Data data) {
        return dendrogram.toString(data);
    }

}