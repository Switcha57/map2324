import data.*;
import clustering.*;
import distance.*;

import java.util.InputMismatchException;

public class MainTest {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        Data data =new Data();
        System.out.println(data);
        HierachicalClusterMiner clustering=null;
        boolean invalidInput = false;
        do {
            invalidInput= false;
            System.out.print("Inserire la profondità del dendrogramma: ");
            int k = Keyboard.readInt();//5
            try{
                clustering=new HierachicalClusterMiner(k);
            }catch (InvalidDepthException e){
                System.out.println(e);
                invalidInput = true;
            }

        }while(invalidInput);

        int choice = 0;
        do{
            System.out.println("Che distanza vuoi:\n 1) Single Link Distance\n2) Average Link Distance");
            choice = Keyboard.readInt();
            if (choice != 1 && choice != 2){
                invalidInput = true;
            }else {
                invalidInput=false;
            }
            }while(invalidInput);
        switch(choice) {
            case 1:
                System.out.println("Single link distance");
                ClusterDistance distance=new SingleLinkDistance();

                double [][] distancematrix=data.distance();
                System.out.println("Distance matrix:\n");
                for(int i=0;i<distancematrix.length;i++) {
                    for(int j=0;j<distancematrix.length;j++)
                        System.out.print(distancematrix[i][j]+"\t");
                    System.out.println("");
                }

                clustering.mine(data,distance);
                System.out.println(clustering);
                System.out.println(clustering.toString(data));
            case 2:
                System.out.println("Average link distance");
                distance=new AverageLinkDistance();
                clustering.mine(data,distance);
                System.out.println(clustering);
                System.out.println(clustering.toString(data));
        }



    }

}
