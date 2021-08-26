
package colors;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class Main extends JFrame {
    private JButton changeColor;
    private Color color = Color.lightGray;
    private Container c;
    public Main()
    {
        super ("Using JColorChooser");
        c = getContentPane();
        c.setLayout(new FlowLayout());
        changeColor = new JButton("Change Color");
        changeColor.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                color = JColorChooser.showDialog( Main.this, "Choose a color", color);
                if (color == null)
                    color = Color.lightGray;
                c.setBackground(color);
                c.repaint();
            }
        }
    );
    c.add( changeColor);
    
    setSize(400, 130);
    show();
    }  
    public static void main(String[] args) {
        Main app = new Main();
        
        app.addWindowListener(
                new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        }
    );
    
    }        
}
