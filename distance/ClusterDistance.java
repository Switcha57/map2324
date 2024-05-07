package distance;
import data.*;
import clustering.*;
public interface ClusterDistance {
     double distance(Cluster c1, Cluster c2, Data d);
}
