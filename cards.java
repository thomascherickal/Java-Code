import java.awt.*;
import java.awt.event.*;
import java.applet.*;

/**<applet code = "cards.java" width = 300 height = 200>
  *</applet>
  */
public class cards extends Applet implements ActionListener
{
   Panel grid, border, card;
   Button b[];
   Scrollbar s1, s2;
   CardLayout CL;
   public void init()
   {
      grid = new Panel();
      grid.setLayout(new GridLayout(4, 4));
      b = new Button[15];
      int i;
      for (i = 0; i < 15; i++) {
         b[i] = new Button("" + (i + 1));
         b[i].addActionListener(this);
         grid.add(b[i]);
      }
      border = new Panel();
      border.setLayout(new BorderLayout());
      s1 = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 25);
      s2 = new Scrollbar(Scrollbar.VERTICAL, 0, 1, 0, 25);
     border.add(s1, BorderLayout.SOUTH);
     border.add(s2, BorderLayout.WEST);
     card =  new Panel();
     card.setLayout(CL = new CardLayout());
     addMouseListener(
         new MouseAdapter()
         {
            public void mouseClicked(MouseEvent me)
            {
               CL.next(card);
            }
         }
     );             
     card.add(border, "Border");
     card.add(grid, "Grid");
     add(card);
  }
  public void actionPerformed(ActionEvent ae)
  {
  }
}

                   
