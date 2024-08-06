package distance;
import data.*;
import clustering.*;

import java.util.Iterator;

public class AverageLinkDistance implements ClusterDistance{
    /**
     * Distanza tra cluster calcolata in average-link
     * @param c1 cluster 1
     * @param c2 cluster 2
     * @param d distanza tra i due cluster
     * @return
     */
    public double distance(Cluster c1, Cluster c2, Data d) {
        double dist = 0;
        double avgdist = 0;
        Iterator<Integer> it1 = c1.iterator();
        while (it1.hasNext()) {
            Example e1 = d.getExample(it1.next());
            Iterator<Integer> it2 = c2.iterator();
            while (it2.hasNext()) {
                try {
                    dist += e1.distance(d.getExample(it2.next()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            avgdist = dist / (c1.getSize() * c2.getSize());
        }
        return avgdist;
    }
}
