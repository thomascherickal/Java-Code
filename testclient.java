import java.rmi.*;
import java.rmi.server.*;

interface ServerStub
{
  public void run();
}

public class testclient implements Runnable, ServerStub
{
  Thread t;
  testclient()
  {
    t = new Thread(this, "test");
  }
  public void run()
  {
    System.out.println("Thread operational");
  }
}