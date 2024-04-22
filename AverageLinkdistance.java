public class AverageLinkdistance implements ClusterDistance {
    public double distance(Cluster c1, Cluster c2, Data d) {
        double TotDistance = 0;

        for (int i = 0; i < c1.getSize(); i++) {
            Example e1 = d.getExample(c1.getElement(i));
            for (int j = 0; j < c2.getSize(); j++) {
                TotDistance += e1.distance(d.getExample(c2.getElement(j)));
            }
        }
        return TotDistance / (c1.getSize() * c2.getSize());
    }
}