/*
 * Search.java
 *
 * Created on October 1, 2005, 9:38 AM
 */

/**
 * n here must be a power of
 * a power of 2
 */
class MultiProcessor implements Runnable
{
    Thread t[];
   int Shared[][];
    int key;
    int n;
    int place;
    int loc;
    boolean foundFlag;
    MultiProcessor(int A[], int target)
    {
        int i = 0, j = 0;
        n = (int)Math.sqrt(A.length);
        t = new Thread[n];
        Shared = new int[n][n];
            while(i < n ) {
                j = 0;
                t[i] = new Thread(this, Integer.toString(i));
                while (j < n)
                {
                    Shared[i][j] = A[n * i + j];
                    j++;
                }
                i++;
            }
        foundFlag = false;    
        key = target;
        for (i = 0; i < n; i++)
            t[i].start();
    }
    public void run()
    {
       int i = Integer.parseInt(Thread.currentThread().getName());
       System.out.println("In thread " + i);
       try {
          
       synchronized(this)
       {      
           if (key >= Shared[i][0] && key <= Shared[i][n - 1])
           {     
               place = i;
               foundFlag = true;
               notifyAll();
           }
            else if (!foundFlag && i != n - 1)
            {
               wait();
            }
            else
            {
               notifyAll();
            }
       }
       } catch (InterruptedException ie) { }
            
       if (foundFlag)
       {
          if (key == Shared[place][i])
            loc = 4 * place + i;
       }
       else
           loc = -1;
        
    }
        
    
    
}
public class Search {
    
    MultiProcessor m;
    static int loc;
    public Search() throws InterruptedException{
      int A[] = { 1, 3, 4, 5, 8, 9, 12, 14, 17, 18, 20, 21, 24, 25, 27, 30 };
      m = new MultiProcessor(A, 15); 
      for (int j = 0; j < m.n; j++)
           m.t[j].join();
     loc = m.loc;
            
    }
    
    public static void main(String[] args) throws InterruptedException{
      Search s = new Search();   
      System.out.println("Found at " + loc);
    }
    
}
