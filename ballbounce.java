import java.awt.*;
import java.applet.*;

/**
  * <applet code = "ballbounce.java" width = 400 height = 400>
  * </applet>
  */

public class ballbounce extends Applet implements Runnable
{
   Thread t;
   double x, y;
   int x_factor, y_factor;
   public void init()
   {
      x = y = 0.1;
      x_factor = y_factor = 1;
      t = new Thread(this);
      t.start();

   }
   public void run()
   {
      while (true)
      {
         double theta = Math.random() *  Math.PI / 2;  
         if (x >= 100 || x <= -100) {
            x_factor *= -1;
            theta = theta + Math.PI / 2;
         }
         else if (y >= 100 || y <= -100) {
            y_factor *= -1;
            theta = theta - Math.PI / 2;
         }
         x+= x_factor * Math.sin(theta);
         y+= y_factor * Math.cos(theta);
         try { 
         t.sleep(10);
         } catch(InterruptedException ie) { }
         repaint();
      }
   }
   public void paint(Graphics g)
   {
      g.setColor(new Color(0, 200, 0));
      g.fillOval((int) (x) + 100, (int)(y) + 100, 100, 100);
   }
}



