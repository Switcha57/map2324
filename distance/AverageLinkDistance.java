package distance;
import data.*;
import clustering.*;
public class AverageLinkDistance implements ClusterDistance{

    public  double distance(Cluster c1, Cluster c2, Data d) {
        int dist=0;
        int avgdist=0;
        for(int i=0;i<c1.getSize();i++){
            Example e1 = d.getExample(c1.getElement(i));
            for(int j=0;j<c2.getSize();j++){
                try {
                    dist+=e1.distance(d.getExample(c2.getElement(j)));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            avgdist = dist/(c1.getSize()*c2.getSize());
        }
        return avgdist;
    }
}
