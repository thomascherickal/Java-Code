import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/** <applet code = "ColorFonts.java" width = 300 height = 300>
   </applet>
*/

public class ColorFonts extends Applet
{
    int next; 
    Font f;
    StringBuffer msg;
    public void init()
    {
        next = 0;
        f = new Font("Dialog", Font.PLAIN, 14);
        msg = new StringBuffer("Dialog");
        setFont(f);
     }
    public void paint(Graphics g)
    {
    
         g.setColor(new Color(20 ,200 ,0 ));
        g.drawString(msg.toString(),  4, 20 );
    }
}

