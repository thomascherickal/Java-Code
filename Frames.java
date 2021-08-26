/*
 * Frames.java
 *
 * Created on September 5, 2005, 2:55 PM
 */

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/*
 <appletcode = "Frames.java" width = 1000 height = 200>
 </applet>
 */
public class Frames extends Applet
{
    private Frame f;
     private Checkbox c;
    StringBuffer msg;
    public Frames() {
        msg = new StringBuffer();
        setLayout(new BorderLayout());
         c = new Checkbox("Sleeping");             
        f = new Frame("AWT Frames");
        f.setSize(300, 300);    
        f.add(c, BorderLayout.CENTER);  
    }
      public void init()
    {
        int i;
        c.addItemListener(
                new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                     if(c.getState() == true)
                        msg = new StringBuffer("Check box selected");
                     else 
                         msg = new StringBuffer("");
                }
        }
      );
      repaint();
        }
 
    public void paint(Graphics g)
    {    
        String s = new String(msg);
        g.drawString(s, 20, 120);
       f.setVisible(true);
    }       
}
