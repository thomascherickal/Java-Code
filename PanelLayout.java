import java.awt.*;
import java.awt.event.*;
import java.applet.*;

/*
 <applet code = "PanelLayout.java" width = 400 height = 300>
 </applet>
 */
public class PanelLayout extends Applet implements ActionListener
{
   Panel p;
    Button b1;
   TextArea txt;
    public void init() {
    
        Panel p = new Panel();
        b1 = new Button("Click Me");
        txt = new TextArea(4, 30);
        super.setSize(400, 300);
        p.add(b1);
        p.add(txt);
        super.add(p);
        HandleGUIEvents();        
    }
    private void HandleGUIEvents()
    {
        b1.addActionListener(this);
       
    }
    public void actionPerformed(ActionEvent ae)
    {
        String str;
        if ((str = ae.getActionCommand()).equals("Click Me"))
            txt.setText("Button 1 clicked");
    }
    
}
