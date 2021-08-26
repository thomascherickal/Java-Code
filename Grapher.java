import java.applet.*;
import java.awt.*;
import java.awt.event.*;




public class Grapher extends Applet
{
    private final double L1 = 2;
    
    private double phi = 180 * Math.PI / 180;
   
    private final int maxX = 10;
    
    private final int maxY = 10;
    
    private final int maxZ = 10;
    
    private final double xIncr = 0.05;
    
    private final double yIncr = 0.05;
    
    private final double zIncr = 0.05;  
        
    private final int screenX = 350;
    
    private final int screenY = 350;
    
    private final int screenZ = 350;
    
    double Clipper[][];
    
    private double z(double x, double y)
    {
        return 0;
    }
    
    private double y(double z, double x)
    {
        return -x;
    }
    
    private double x(double y, double z)
    {
        return 0;
    }
    
    private double xTransform(double x, double z)
    {
        return x  + z * L1 * Math.cos(phi);
    }
     private double yTransform(double y, double z)
     {
         return  y + z  * L1 * Math.sin(phi);
     }
     

     private void PlotAxes()
     {
        double x, y, z;
        
        try {
        Color b = Color.BLACK;
         for(x = -maxX; x < maxX; x+= xIncr)
             PlotPoint(x, 0, 0, b);
        
        Thread.currentThread().sleep(100);
                 
        for (y = -maxY; y < maxY; y+= yIncr)
            PlotPoint(0, y, 0, b);
        
         Thread.currentThread().sleep(100);
        for (z = -maxZ; z < maxZ; z+=zIncr)
            PlotPoint(0, 0, z, b);
          Thread.currentThread().sleep(100);
        }
        catch(InterruptedException e) 
        {
            
        }
     }
     
     private void PlotFunction()
     {
         double x, y, z, Old;
         Color c;

         for (x = -maxX; x < maxX; x+= xIncr)
         {
             for (y = -maxY; y < maxY; y+= yIncr) {
                 z = z(x, y);     
                 Old = y;
                 y = y(z, x);
                c = new Color((int)x & 0111, (int)y & 0111, (int)z & 0111);
                
                PlotPoint(x, y, z, c);
                y = Old;
             }
             
         }
         
         
     }
     
     private void PlotPoint(double x, double y, double z, Color color)
     {
         Graphics g = getGraphics();
         boolean paint = false;
       
         int xPlot = (int)((xTransform(x, z) + maxX) * screenX / maxX);
         int yPlot = (int)((yTransform(y, z) + maxY) * screenY / maxY);
         g.setColor(color);
         
      g.drawLine(xPlot, yPlot, xPlot, yPlot);
         
     }
     
    public void init()
    {

        int i, j;
        

        
        addMouseListener(
                new MouseAdapter() {
            public void mouseClicked(MouseEvent me)
            {
                phi = Math.atan(me.getX() / me.getY());
                   repaint();
            }
            
    } );
    }
     public void paint(Graphics g)
     {
         
         
        PlotFunction();
            PlotAxes();
      
     }
     
}


