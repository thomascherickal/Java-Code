import  Include.Complex;

public class FFT1
{                     
   double a[];
   Complex w[];
   Complex W[][];
   static Complex y[];
   static int n, lgn, itr;
   volatile int count = 0;
   private class Parellel implements Runnable
   {
      public Thread t;
      int i;
      boolean mutex = false;
      Parellel(int i)
      {
         t = new Thread(this, "" + i);
         this.i = i;
         t.start();
      }
      public void run()
      {
         System.out.println("Inside thread  "+ i);
         
         synchronized(this) {
            ComputeFFT();
         }
      }

      synchronized void ComputeFFT()
      {
         int j = 0, k = lgn;
         try {
         while(j < lgn)
         {
            k /= 2;
            butterfly(i , (i + k) >= n ? i - k : i + k, j);
            System.out.println("thread " + i + "stage " + j  + " " +  count);

            j *= 2;
            if (j == 0) j = 1;

            count++;
               if (count < n) {
            System.out.println("Thread " + i);
            System.out.println("State  " + j);
            System.out.println("k      " + k);
            System.out.println("Count  " + count);
            System.out.println("Bfly   " + ((i + k) >= n ? i - k : i + k) );
            System.out.println("Waiting thread " + i);
            mutex = true;
            wait();
            }
            else  if(CheckFinished())
            {

               notifyAll();
               System.out.println("All threads woken up");
               System.out.println("Stage over");
               count = 0;
               mutex = false;
            }
            else {
            mutex = true;
             
            }
              
         }

         } catch( InterruptedException e) { }
       }            
       void butterfly(int a, int b, int j)
       {
         if (b - a != j)
            y[a] = y[a].add(W[j][(int)(i/2)].multiply(y[b]));
         else
            y[a] = y[a].subtract(W[j][(int)(i/2)].multiply(y[b]));
       }
   }
   static Parellel p[];
   private boolean CheckFinished()
   {
      boolean m = true;
         int it;
         for ( it = 0; it < n; it++)
            m = m && p[it].mutex;
      return m;
   }
   FFT1()
   {
      int i, j, k;
      n = 4;
      lgn = 2;
      a = new double[n];
      y = new Complex[n];
      w = new Complex[n];
      w[0] = new Complex((double)1, 0);
      W = new Complex[lgn][n];
      for (i = 0; i < n; i++) {
			//root(i, j) returns the jth complex root of i //
         w[i] = new Complex(w[0].rootOfUnity(1, i+1));
         a[i] = 1;
         y[i] = new Complex(1, 0);
		}
      for (i = 0; i < lgn ; i++) {
         for (j = 0; j < n / 2; j++) {
             W[i][j] = new Complex(1, 0);
                for (k = 0; k < j % Math.pow(2, i); k++)
                     W[i][j] = W[i][j].multiply(w[(int)Math.pow(2, i + 1) - 1]);
                   System.out.println(i + " " + j + " " + W[i][j]);
        }
     }
     p = new Parellel[n];
     for (i = 0; i < n; i++)
         p[i] = new Parellel(i);
    }
    public static void main(String args[])
    {
      new FFT1();
      try {
         for(itr = 0; itr < n; itr++)
            p[itr].t.join();
      } catch (InterruptedException e) { }
      for (itr = 0; itr < n; itr++)
         System.out.println(y[itr] + " ");
    }
}

                          
