
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

/**
 <applet code= "MenuDialog" width = 300 height = 300>
 </applet>
 */

class SampleDialog extends Dialog
{
    SampleDialog(Frame parent, String title)
    {
        super(parent, title, false);
        setLayout(new BorderLayout());
        setSize(300, 200);
    }
    public void paint(Graphics g)
    {
        g.drawString("In the dialog box", 30, 100);               
    }
}
class MenuF extends Applet
{        
    Frame f;
    MenuF()
    {
        MenuBar mbar = new MenuBar();
        f = new Frame("Menu and Dialog");
        f.setMenuBar(mbar);
        Menu File = new Menu("File");
        MenuItem item1;
        File.add(item1 = new MenuItem("New..."));
        mbar.add(File);

        item1.addActionListener(
                new ActionListener() {
                 public void actionPerformed(ActionEvent ae) {
                  String arg = ae.getActionCommand();
                 if(arg.equals("New..."))
                 {
                SampleDialog d = new SampleDialog(f, "New Dialog Box");
                d.setVisible(true);
                }
             }
        }
       );
    }
}