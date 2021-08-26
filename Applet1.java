/*
 * Applet1.java
 *
 * Created on June 12, 2005, 6:00 PM
 */

import java.applet.*;
import java.awt.*;

/**
 *
 * @author Thomas
 */
public class Applet1 extends java.applet.Applet implements Runnable{
    
    String msg = "A Simple Moving Banner";
    Thread t = null;
    int state;
    boolean stopflag;
    
    /** Initialization method that will be called after the applet is loaded
     *  into the browser.
     */
    public void init() 
    {
        setBackground(Color.cyan);
        setForeground(Color.red);
    }
    public void start()
    {
        t = new Thread(this);
        stopflag = false;
        t.start();
    }
    public void run()
    {
        char ch;
        for ( ; ; ) {
            try {
                repaint();
                Thread.sleep(250);
                ch = msg.charAt(0);
                msg = msg.substring(1, msg.length());
                msg += ch;
                if (stopflag)
                    break;
            } catch(InterruptedException e) { }
            
        }
    }
    public void stop()
    {
        stopflag = true;
        t = null;
    }
    
    public void paint(Graphics g)
    {
        g.drawString(msg, 50, 30);
    }
}
