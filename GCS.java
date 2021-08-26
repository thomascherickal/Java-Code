import java.applet.*;
import java.awt.*;

/*
 <applet = "GCS.java" width = 400 height = 400>
 </applet>
 */
public class GCS extends Applet
{
   public void paint(Graphics g)
   {
       g.drawLine(10, 20, 20, 10);
       g.setColor(new Color(200, 0, 0 ));
       g.drawRect(30, 20, 120, 100);
       g.setColor(new Color(0, 0,200));
       g.fillOval(50 , 50 , 50, 50);
       g.setColor(new Color(200, 0, 100));
       g.fillRect(100, 100, 300, 200);
   }
}
