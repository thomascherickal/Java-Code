/////////////////////////////////////////////////////////
//	Point manipulation experiment                  //
/////////////////////////////////////////////////////////
import java.awt.*;
import java.applet.*;

/**
<applet code = "Pointm"  width = 300 height = 200>
</applet>
*/ 

public class Pointm extends Applet
{
    public void paint(Graphics g)
    {
        Point p1 = new Point(20, 20);
        Point p2 = new Point(50, 50);
        p1.translate(25, 25);
        g.drawLine(20, 10, 30, 40);

     
    }
}
                

        
