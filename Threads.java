import java.io.*;
public class Threads
{    
      public static void main (String args[]) throws IOException
      {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        Thread t = Thread.currentThread();
        System.out.println("Thread = " + t);
        t.setName("My Thread");
        try {
            
             for( int n = 5; n > 0; n--) {
                System.out.println(n);
                Thread.sleep(1000);
            }
        }
            
            catch (InterruptedException e) { 
            System.out.println("Main thread interrupted");
     }
    }
 }
    

