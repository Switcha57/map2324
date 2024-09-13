package distance;
import data.*;
import clustering.*;

/**
 * Interfaccia contenente l'operazione distance da implementare.
 */
public interface ClusterDistance {
     /**
      * Metodo che calcola la distanza tra due cluster.
      * @param c1 primo cluster.
      * @param c2 secondo cluster.
      * @param d dati del cluster.
      * @return distanza tra due cluster.
      */
     double distance(Cluster c1, Cluster c2, Data d);
}
