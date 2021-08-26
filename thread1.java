/////////////////////////////////////////////////////////
//  Multithreading and exception handling experiment   //
/////////////////////////////////////////////////////////

class RThread implements Runnable
{
    Thread t;
    RThread()
    {
        t = new Thread(this, "New Runnable");
        t.start();
    }
    public void run()
    {
        try {
       
            for (int i = 1; i < 10; i++)
                System.out.println("RThread "+ i);
            t.sleep(10);
        }  catch (InterruptedException e) {
            System.out.println("Thread interrupted");
      }
    }

}

class TThread extends Thread
{
    TThread()
    {
        super("New Thread");
        start();
    }
    public void run()
    {
        try {
            for (int i = 1; i < 10; i++)
                System.out.println("TThread "  + i);
            sleep(10);
        }  catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }
}

class thread1 {
    public static void main(String args[]) throws InterruptedException
    {
        System.out.println("      THREADS AND EXCEPTION HANDLING");
        System.out.println("      ******************************");

        Thread cur = Thread.currentThread();
        TThread t = new TThread();

        RThread r = new RThread();
        cur.sleep(1000);

    }


}



