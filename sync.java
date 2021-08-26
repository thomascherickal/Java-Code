 class Callme
  {
     synchronized void Call(String msg)
      {
       System.out.println("["+msg+"]");
       try
        {
         Thread.sleep(1000);
        }
        catch(InterruptedException c)
        {
         System.out.println("Interrupted");
	}
      }
  }
  class Caller implements Runnable
  {
   String msg;
   Callme target;
   Thread t;
    public Caller(Callme targ,String s)
     {
      target = targ;
      msg=s;
      t=new Thread(this);
      t.start();
     }
    public void run()
    {
      target.Call(msg);
    }
  }
    class Sync
    {
       public static void main(String args[])
       {
         Callme target = new Callme();
         new Caller(target,"Hello");
         new Caller(target,"Sync");
         new Caller(target,"World");
       }
    }


