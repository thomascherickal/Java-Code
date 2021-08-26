class Q
{
  int a = -1;
  boolean valueSet = false;
  synchronized void put(int b)
  {
      try {
      while(valueSet)
         wait();
      a = b;
      valueSet = true;
      notify();
      }
      catch(InterruptedException e ) { }
  }
  synchronized int get()
  {
      int c = -1;
      try {

      while(!valueSet)
         wait();
      valueSet = false;
      c = a;
      a = -1;
      notify();
      }
      catch(InterruptedException e ) { }

      return c;
  }
}
class Producer implements Runnable
{
  Q q;
  Thread t;
  Producer(Q q)
  {
      this.q = q;
      System.out.println("Inside Producer");

      Thread t = new Thread(this);
      t.start();
   }
   public void run()
   {
      int a;
      try {
      while(true) {
        t.sleep(500);
         a = q.get();
         System.out.println("Got " +  a);

       }
       } catch( InterruptedException e) { }
   }
}
class Consumer implements Runnable
{
  Q q;
  Thread t;
  Consumer(Q q)
  {
      System.out.println("Inside Consumer");
      this.q = q;
      Thread t = new Thread(this);
      t.start();
   }
   public void run()
   {
      int i = 0;
      try {
      while(true) {
         q.put(i++);
         System.out.println("Put " +  (i - 1));
         t.sleep(500);
      }
      } catch (InterruptedException e) { }
   }
}
    
         
public class PC
{
   PC()
   {
      Thread t = Thread.currentThread();
      Q q = new Q();
      new Producer(q);
      try {
      t.sleep(500);
      }
      catch (InterruptedException e)
      { }
      new Consumer(q);
   }
   public static void main(String args[])
   {
      new PC();
   }
}
 
