package clustering;
import data.*;
import distance.*;

import java.io.*;

public class HierachicalClusterMiner implements Serializable {

    private Dendrogram dendrogram;

    public HierachicalClusterMiner(int depth) throws InvalidDepthException {
        dendrogram= new Dendrogram(depth);
    }

    public String toString() {
        return dendrogram.toString();
    }

    public String toString(Data data) throws InvalidDepthException {
        return dendrogram.toString(data);
    }

    public void mine(Data data, ClusterDistance distance){
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

    public static HierachicalClusterMiner loadHierachicalClusterMiner(String fileName)
            throws FileNotFoundException, IOException,ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(".\\res\\" + fileName));
        HierachicalClusterMiner h=(HierachicalClusterMiner) in.readObject();
        in.close();
        return h;
    }

    public void salva(String dir,String fileName)throws FileNotFoundException, IOException {
        new File(".\\res\\" + dir).mkdirs();
        File file = new File(".\\res\\" + dir+"\\"+fileName);
        file.createNewFile();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(".\\res\\" + dir+"\\"+fileName,false));
        out.writeObject(this);
        out.close();
    }


}
