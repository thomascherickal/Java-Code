/*
 * WhileCounter.java
 *
 * Created on August 8, 2005, 5:22 PM
 */
import java.awt.Graphics;
import javax.swing.JApplet;
/**
 *
 * @author Thomas
 */
public class WhileCounter extends JApplet {
    
    /** Initialization method that will be called after the applet is loaded
     *  into the browser.
     */
    public void init() {
        // TODO start asynchronous download of heavy resources
    }
    public void paint(Graphics g)
    {
        int counter = 1;
        
        while (counter <= 10)
        {
            g.drawLine(10, 10, 250, counter * 10);
            counter += 0.5;
        }
    }
    // TODO overwrite start(), stop() and destroy() methods
}
