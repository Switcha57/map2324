package clustering;
import data.*;
import distance.*;

import java.io.*;

public class HierachicalClusterMiner implements Serializable {

    private Dendrogram dendrogram;

    /**
     * Costruttore della classe HierachicalClusterMiner.
     * @param depth profondità del dendogramma.
     * @throws InvalidDepthException
     */
    public HierachicalClusterMiner(int depth) throws InvalidDepthException {
        dendrogram= new Dendrogram(depth);
    }
    /**
     * Metodo che richiama il metodo toString di Dendogram.
     * @return stringa rappresentante il dendogramma nascondendo i dati.
     */
    public String toString() {
        return dendrogram.toString();
    }
    /**
     * Metodo che richiama il metodo toString di Dendogram.
     * @return stringa rappresentante il dendogramma mostrando i dati.
     */
    public String toString(Data data) throws InvalidDepthException {
        return dendrogram.toString(data);
    }

    /**
     * Metodo che elabora le informazioni del dendogramma.
     * @param data dati all'interno del dendogramma.
     * @param distance distanza (Single-Link o Average-Link distance)sulla quale effettuare il merge dei ClusterSet.
     */
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

    /**
     * Metodo che recupera un oggetto HierachicalClusterMiner da file.
     * @param fileName nome del file da cui recuperare l'oggetto.
     * @return oggetto HierachicalClusterMiner.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static HierachicalClusterMiner loadHierachicalClusterMiner(String fileName)
            throws FileNotFoundException, IOException,ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(".\\res\\" + fileName));
        HierachicalClusterMiner h=(HierachicalClusterMiner) in.readObject();
        in.close();
        return h;
    }

    /**
     * Metodo che salva un oggetto HierachicalClusterMiner su file.
     * @param dir directory in cui salvare il file.
     * @param fileName nome del file da cui salvare l'oggetto.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void salva(String dir,String fileName)throws FileNotFoundException, IOException {
        new File(".\\res\\" + dir).mkdirs();
        File file = new File(".\\res\\" + dir+"\\"+fileName);
        file.createNewFile();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(".\\res\\" + dir+"\\"+fileName,false));
        out.writeObject(this);
        out.close();
    }


}
