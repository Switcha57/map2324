package distance;
import data.*;
import clustering.*;

import java.util.Iterator;

public class SingleLinkDistance implements ClusterDistance {

    /**
     * Distanza tra cluster calcolata in average-link
     * @param c1 cluster 1
     * @param c2 cluster 2
     * @param d distanza tra i due cluster
     * @return distanza tra due cluster.
     */
    public  double distance(Cluster c1, Cluster c2, Data d) {

        double min=Double.MAX_VALUE;

        Iterator<Integer> it1 = c1.iterator();
        while(it1.hasNext()) {
            Example e1=d.getExample(it1.next());
            Iterator<Integer> it2 = c2.iterator();
            while(it2.hasNext()) {
                double distance = 0;
                try {
                    distance = e1.distance(d.getExample(it2.next()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (distance < min)
                    min = distance;
            }
        }
        return min;
    }
}