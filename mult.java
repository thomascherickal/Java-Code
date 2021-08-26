class Mthread implements Runnable
{
   int m, n;
   Mthread(int m, int n)
   {
   this.m = m;
   this.n = n;
   }
   public void run()
   {
      int i, j;
      for (i = 1; i < m; i++)
      {
         for (j = 1; j < n; j++)
            System.out.println(i + " " + j + " " + (i * j));
      }
   }
}

public static void main(String args[])
{

