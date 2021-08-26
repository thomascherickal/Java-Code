
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

/**
 <applet code= "MenuF.java" width = 300 height = 300>
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

public class MenuF extends Applet
{        
    Frame f;
    FileDialog fd;
    MenuF()
    {
        MenuBar mbar = new MenuBar();
        f = new Frame("Menu and Dialog");
        f.setMenuBar(mbar);
        fd = new FileDialog(f);
        fd.setVisible(true);
        Menu File = new Menu("File");
        String p = fd.getDirectory() + fd.getFile() ;
        System.out.println(p);
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
