class Buffer
{
   boolean empty;
   boolean full;
   int item;
   Buffer()
   {
      empty = true;
      full = false;
      item = -1;
   }
   public void View()
   {
      System.out.println("Empty = " + empty);
      System.out.println("Full  = " + full);
      System.out.println("Item  = " + item);
   }


   synchronized void put(int i)
   {
     try {
      while(full)
         wait();
      item = i;
      full = true;
      empty = false;
      notify();
      } catch (InterruptedException ie) { }
   }
   synchronized int get()
   {
      int y;
      try {
      while(empty)
         wait();
      y = item;
      item = -1;
      empty = true;
      full = false;
      notify();
      return y;
      } catch (InterruptedException ie) {
         return -1;
      }
   }

}

class PC
{
   Buffer b;
   PC()
   {
      b = new Buffer();
   }
   class Producer implements Runnable
   {
      Thread t;
      Producer()
      {
         t = new Thread(this, "Producer");
         t.start();
      }
      public void run()
      {

         int i = 0;
         while(i < 10) {
            b.put(i++);
            b.View();
            System.out.println("Put + " + i);
         }
      }
   }
   class Consumer implements Runnable
   {
      Thread t;
      Consumer()
      {
         t = new Thread(this, "Consumer");
         t.start();
      }
      public void run()
      {
         int i  = -1;
         while(i < 10) {
            i = b.get();
            b.View();
            System.out.println("Got " + i);
         }
      }
  }
  void Execute()
  {
      new Producer();
      new Consumer();
  }
}

class prodcons
{
   public static void main(String args[])
   {
      new PC().Execute()
      ;
   }
}
 
