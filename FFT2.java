import  Include.Complex;

public class FFT
{                     
   double a[];
   Complex w[];
   Complex W[][];
   static Complex y[];
   static int n, lgn, itr;
   volatile int count = 0;
   private class Parellel implements Runnable
   {
      class NetThread
      {
          public Thread t;
         int i, j, k;
         boolean mutex = false;
         boolean set;
         public void mysuspend()
         {
            set = true;
         }
         public synchronized void myresume()
         {
            set = false;
            notify();
          }
      }
      volatile int thrd;
      NetThread nt[];
      Parellel()
      {
         nt = new NetThread[n];
         int a;
         thrd = 0;
         for (a = 0; a < n; a++)  {
            nt[a] = new NetThread();
            nt[a].i = a;
            nt[a].t = new Thread(this, "" + a);
            nt[a].t.start();

         }

      }
      public void run()
      {
         int a = Integer.parseInt(Thread.currentThread().getName());
         int r;
         nt[a].j = 0;
         nt[a].k = lgn;

         try {

         while (nt[a].j < lgn)
         {
            synchronized (this)
            {

                System.out.println("Number = "+ thrd);
                while (a != thrd)
                     wait();
                  System.out.println("Inside thread  "+ nt[a].i);

                thrd = (thrd + 1)  % n;
                ComputeFFT(a);
                Operate(a);
                notifyAll();

            }


         }
         } catch( InterruptedException e) { }
              
            
      }
      void Operate(int a) throws InterruptedException
      {
            synchronized(this)
            {
            nt[a].j *= 2;
            nt[a].k /= 2;

            if (nt[a].j == 0) nt[a].j = 1;
            nt[a].mutex = true;
            count++;
               if (count < n) {
            System.out.println("Thread " + nt[a].i);
            System.out.println("State  " + nt[a].j);
            System.out.println("k      " + nt[a].k);
            System.out.println("Count  " + count);
            System.out.println("Bfly   " + ((nt[a].i + nt[a].k) >= n ? nt[a].i - nt[a].k : nt[a].i + nt[a].k) );
            System.out.println("Waiting thread " + nt[a].i);
            notifyAll();
            wait();
            }        
            else  if(CheckFinished())
            {

            System.out.println("Thread " + nt[a].i);
            System.out.println("State  " + nt[a].j);
            System.out.println("k      " + nt[a].k);
            System.out.println("Count  " + count);
            System.out.println("Bfly   " + ((nt[a].i + nt[a].k) >= n ? nt[a].i - nt[a].k : nt[a].i + nt[a].k) );

               notifyAll();

               System.out.println("All threads woken up");

               System.out.println("Stage over");
               count = 0;
               nt[0].mutex = false;
               nt[1].mutex = false;
               nt[2].mutex = false;
               nt[3].mutex = false;

            }
         }
         
      }   
      synchronized void ComputeFFT(int a)
      {
            butterfly(nt[a].i , (nt[a].i + nt[a].k) >= n ? nt[a].i - nt[a].k : nt[a].i + nt[a].k, nt[a].j, nt[a].k, a);
            System.out.println("thread " + nt[a].i + "stage " + nt[a].j  + " " +  count);
      }            
       void butterfly(int a, int b, int j,int k, int c)
       {
         if (b - a != k)  {
            y[a] = y[a].add(W[j][(int)(nt[c].i/2)].multiply(y[b]));
            System.out.println(y[a] + " add");
         }
         else  {
            y[a] = y[a].subtract(W[j][(int)(nt[c].i/2)].multiply(y[b]));
            System.out.println(y[a] + "sub");
          }
       }
   }
   static Parellel p;
   private boolean CheckFinished()
   {
      boolean m = true;
         int it;
         for ( it = 0; it < n; it++)
            m = m && p.nt[it].mutex;
      return m;
   }
   FFT()
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
     p = new Parellel();
    }
    public static void main(String args[])
    {
      new FFT();
      try {
         for(itr = 0; itr < n; itr++)
            p.nt[itr].t.join();
      } catch (InterruptedException e) { }
      for (itr = 0; itr < n; itr++)
         System.out.println(y[itr] + " ");
    }
}

                          
