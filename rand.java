/////////////////////////////////////////////////////////
//	Random number class experiment                 //
/////////////////////////////////////////////////////////
import java.util.*;

public class rand
{
    public static void main(String args[])
    {
        System.out.println("         Random Number Generation");
        System.out.println("         ************************");

        double a[];
        final int NUM = 20;
        a = new double[NUM];
        int i;
        for (i = 0; i < NUM; i++)
            a[i]= 10 * Math.random();
        Arrays.sort(a);
        for (i = 0; i < NUM; i++)
            System.out.println(a[i]);
        System.exit(0);
    }
}

