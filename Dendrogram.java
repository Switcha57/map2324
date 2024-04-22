public class Dendrogram {
    private ClusterSet[] tree;

    public Dendrogram(int depth) {
        this.tree = new ClusterSet[depth];
    }

    public void setClusterSet(int index, ClusterSet v) {
        this.tree[index] = v;
    }

    public ClusterSet getClusterSet(int level) {
        return this.tree[level];
    }

    public int getDepth() {
        return this.tree.length;
    }

    public String toString() {
        String v = "";
        for (int i = 0; i < tree.length; i++)
            v += ("level" + i + ":\n" + tree[i] + "\n");
        return v;
    }

    public String toString(Data data) {
        String v = "";
        for (int i = 0; i < tree.length; i++)
            v += ("level" + i + ":\n" + tree[i].toString(data) + "\n");
        return v;
    }

}
