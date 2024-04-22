class ClusterSet {

    private Cluster C[];
    private int lastClusterIndex = 0;

    ClusterSet(int k) {
        C = new Cluster[k];
    }

    void add(Cluster c) {
        for (int j = 0; j < lastClusterIndex; j++)
            if (c == C[j]) // to avoid duplicates
                return;
        C[lastClusterIndex] = c;
        lastClusterIndex++;
    }

    Cluster get(int i) {
        return C[i];
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < C.length; i++) {
            if (C[i] != null) {
                str += "cluster" + i + ":" + C[i] + "\n";

            }
        }
        return str;

    }

    /*
     * Input: distance: oggetto per il calcolo della distanza tra cluster; data:
     * oggetto istanza
     * che rappresenta il dataset in cui si sta calcolando l’oggetto istanza di
     * ClusterSet
     * Output: nuova istanza di ClusterSet
     * Comportamento: determina la coppia di cluster più simili (usando il metodo
     * distance
     * di ClusterDistance e li fonde in unico cluster; crea una nuova istanza di
     * ClusterSet
     * che contiene tutti i cluster dell’oggetto this a meno dei due cluster fusi al
     * posto dei
     * quali inserisce il cluster risultante dalla fusione (nota bene l’oggetto
     * ClusterSet
     * risultante memorizza un numero di cluster che è pari al numero di cluster
     * memorizzato nell’oggetto this meno 1).
     */
    public ClusterSet mergeClosestClusters(ClusterDistance distance, Data data) {
        ClusterSet newCSet = new ClusterSet(this.C.length - 1);

        double currMin = Double.MAX_VALUE;
        int c1 = 0, c2 = 0;
        // ALl pairs
        for (int i = 0; i < this.C.length - 1; i++) {
            for (int j = i + 1; j < C.length; j++) {
                double dis = distance.distance(C[i], C[j], data);
                if (dis < currMin) {
                    currMin = dis;
                    c1 = i;
                    c2 = j;
                }
            }
        }
        // add all other clusters
        for (int i = 0; i < C.length; i++) {
            if (i == c1 || i == c2)
                continue;
            newCSet.add(C[i]);
        }
        newCSet.add(C[c1].mergeCluster(C[c2]));
        return newCSet;
    }

    String toString(Data data) {
        String str = "";
        for (int i = 0; i < C.length; i++) {
            if (C[i] != null) {
                str += "cluster" + i + ":" + C[i].toString(data) + "\n";

            }
        }
        return str;

    }

}