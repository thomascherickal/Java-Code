    import java.math.*;


class MultiProcessor implements Runnable
{
	Thread t[];
	//shared memory//
	volatile Complex s[];
	double a[];
	Complex W[][];
	volatile int j[];
	volatile int i[];
   int valueSet0, valueSet1, valueSet2, valueSet3;
   	int n; 
   	volatile boolean check, finished[][], stages[][];
   MultiProcessor(double a[], Complex W[][], int n)
	{
		int k, l;
      t = new Thread[n];
      s = new Complex[n];
      this.a = new double[n];
            for (k = 0; k < n; k++) {
                
			t[k] = new Thread(this, Integer.toString(k));
			this.a[k] = a[k];
         s[k] = new Complex(a[k], 0);
      }
      this.W = new Complex[(int)(Math.log(n)/Math.log(2))][n];
      for (k = 0; k < (int)(Math.log(n)/Math.log(2)) ; k++) {
         for(l = 0; l < n/2; l++)  {
            this.W[k][l] = new Complex(W[k][l]);
         }
      }
      
      finished = new boolean[n/2][n];;
      stages = new boolean[(int)(Math.log(n)/Math.log(2)) ][n];
      this.n = n;
	i = new int[n];
	j = new int[n];
      System.out.println("Starting threads");
   valueSet0 = valueSet1 = valueSet2 = 0;
	for (k = 0; k < n; k++) {
		t[k].start();
        }
	
			
	}
   synchronized private void Butterfly(int i0, int i1,Complex w)
	{
      s[i0] = s[i0].add(w.multiply(s[i1]));
      s[i1] = s[i0].subtract(w.multiply(s[i1]));
	System.out.println(s[i0] + " " + s[i1]);

   }

	private boolean CheckFinished(int s)
   {
      int i;
      boolean check = true;
      for (i = 0; i < n; i++)  {
         check = check && finished[s][i];
      }
      return check;
   }

   private boolean CheckStageOver(int s)
   {
      int i;
      boolean check = true;
      for (i = 0; i < n; i++)  {
         check = check && stages[s][i];
      }
      return check;
   }
    void Compute(int k) throws InterruptedException
    {
         
		int l = 0;
		
		while(true)
		{
			if (k != valueSet0)
				wait();
			else
				break;
		}
		System.out.println(valueSet0);

	     	valueSet0 = (valueSet0 + 1) % n;
		notifyAll();
		while (i[k] < (int)(Math.log(n) / Math.log(2)))
		{
			Butterfly(i[k], i[k] + (int)(n / Math.pow(2, j[k] + 1)), W[i[k]][l]);
			finished[i[k]][k] = true;
			if(!CheckFinished(i[k]))
      	       wait();
			else
			  notifyAll(); 
      		l++;
    	         
    	    }

	    System.out.println("Exiting Compute " + k);
	}	   
            
	public void run() 
	{
		int k;
		try {
		
		k = Integer.parseInt(Thread.currentThread().getName());
				
						
		i[k] = 0;
		j[k] = 0;
		
      	
		Compute(k);
		
     	     	System.out.println("Exiting thread " + k);
		} catch(InterruptedException ie) {
		}
	}
}
		

	
class ParallelFFT
{
	Complex w[];
	Complex W[][];
	double a[];
	int n;
	MultiProcessor multip;
	
   ParallelFFT(double a[], int n) throws InterruptedException
	{
      Complex w0 = new Complex(1, 0);                 
      int i, j, k, l = 1;             
      this.a = new double[n];
      w = new Complex[n];
	 	for (i = 0; i < n; i++) {
         		w[i]  =  w0.rootOfUnity(1, i + 1);

	   System.out.println("Root "  + i  + " " + w[i]);
			this.a[i] = a[i];
		}
		this.n = n;
/*
      
// W[i][j] stores the ith power of the jth root of 1
      for (i = 0; i < (int)(Math.log(n)/Math.log(2)) ; i++) {
         for (j = 0; j < n / 2; j++) {
      W[i][j] = new Complex(1, 0);
      for (k = 0; k < j % Math.pow(2, i); k++)
               W[i][j] = W[i][j].multiply(w[(int)Math.pow(2, i + 1) - 1]);
            System.out.println(i + " " + (j + 1) + " " + W[i][j]);
*/
		W = new Complex[(int)(Math.log(n)/Math.log(2))][n];
		W[0][0] = new Complex(1.0, 0.0);
		W[0][1] = new Complex(1.0, 0.0);
		W[0][2] = new Complex(1.0, 0.0);
		W[0][3] = new Complex(1.0, 0.0);
        	W[1][0] = new Complex(1.0, 0.0);
		W[1][1] = new Complex(1.0, 0.0).polar(1, Math.PI/4).multiply(new Complex(1.0, 0.0).polar(1, Math.PI/4));
		W[1][2] = new Complex(1.0, 0.0);
		W[1][3] = new Complex(1, 0).polar(1, Math.PI/4).multiply(new Complex(1.0, 0.0).polar(1, Math.PI/4));
		W[2][0] = new Complex(1.0, 0.0);
		W[2][1] = new Complex(1.0, 0.0).polar(1, Math.PI/8).multiply(new Complex(1.0, 0.0).polar(1, Math.PI/8)) ;
		W[2][2] = new Complex(1.0, 0.0).polar(1, Math.PI/8).multiply(new Complex(1.0, 0.0).polar(1, Math.PI/8));
		W[2][3] = new Complex(1.0, 0.0).polar(1, Math.PI/8).multiply(new Complex(1.0, 0.0).polar(1, Math.PI/8).multiply(new Complex(1.0, 0.0).polar(1, Math.PI/8)));

      multip = new MultiProcessor(a, W, n);
      for (i = 0; i < n; i++)
         multip.t[i].join();
      System.out.println("The FFT is ");
		for(i = 0; i < n; i++)
         System.out.println(multip.s[i]);
	}
        public static void main(String args[]) throws InterruptedException
        {	
            double a[] = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
            ParallelFFT p = new ParallelFFT(a, a.length);
	}
}		
