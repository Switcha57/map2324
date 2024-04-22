public class Dendrogram {
    private ClusterSet [] tree;

    public Dendrogram(int length){
        tree = new ClusterSet[length];
    }

    void setClusterSet(ClusterSet c, int level){
        tree[level] = c;
    }

    ClusterSet getClusterSet(int level){
        return tree[level];
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
