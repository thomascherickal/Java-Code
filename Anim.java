import java.awt.geom.*;
import java.applet.*;
import java.awt.*;

/**
  <applet code = "Anim" width = 300 height = 300>
  </applet>
 */

public class Anim extends Applet implements Runnable
{
      
    static int i = 0, j = 0;
    static int count = 0;
    int dir = 0;
    Thread t;
    public void run()
    {
       while(true)
       {
        try {
        switch(dir)
        {
            case 0:
                i++;
                j++;
                break;
            case 1:
                i--;
                j++;
                break;
            case 2:
                i++;
                j--;
                break;
            case 3:
                i--;
                j--;
                break;
                
        }
        
       count ++;
        if (i < 0) dir = 0;
        else if (j < 0) dir = 1;
        else if (j > 300) dir = 2;
        else if (i  > 300) dir = 3;
        else if (count > 100) {
            dir =  (int)Math.abs(4 * Math.random());
            count = 0;
        }
        Thread.sleep(10);
        repaint();
        } catch(Exception e)
        {
            System.out.println(e + "");
        }
       }
    }
    
     public Anim()
        {
         t = new Thread(this, "Anim");
         t.start();
            
        }
    public void paint(Graphics g)
    {
       
        g.setColor(Color.red);
        g.drawOval(i, j, 100, 100);
        
    }
    
}
