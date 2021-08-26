import java.awt.*;
import java.applet.*;
import java.awt.event.*;
/**<applet code = "marklist.java" width = 640 height = 100>
  *</applet>
  */

public class marklist extends Applet implements ActionListener
{
   Frame f;
   List list;
   TextField t[];
   Label l[];
   CheckboxGroup cbg;
   Checkbox cb[], cb1[];
   Button b[];
   StringBuffer markList[];
   int ptr;
   public void init()
   {
      String labels[] = { "Name", "College", "C", "C++", "Java", "Total", "Average"};
      String cbLabels[] = { "Print Name", "Print College", "Print Statistics", "Print Course" };
      String cbgLabels[] = {"BCA", "BSc", "BISM", "B.E" };
      //construct controls
      l = new Label[7];
      t = new TextField[7];
      cbg = new CheckboxGroup();
      cb = new Checkbox[4];
      cb1 = new Checkbox[4];
      b = new Button[2];
      markList = new StringBuffer[10];
      ptr = 0;
      // initialize GUI
      int i;
      for (i = 0; i < 7; i++) {
         l[i] = new Label(labels[i]);
         t[i] = new TextField(10);
      }
      for (i = 0; i < 4; i++)
      {
         cb[i] = new Checkbox(cbLabels[i]);
         cb1[i] = new Checkbox(cbgLabels[i], cbg, false);
      }
      b[0] = new Button("Add");
      b[1] = new Button("Generate Mark List");
      //register Event Listeners
      t[4].addActionListener(this);
      b[0].addActionListener(this);
      b[1].addActionListener(this);
      //output frame
      f = new Frame("Mark List");
      f.setSize(400, 200);
      f.setVisible(false);
      list =  new List();
      // add Controls to applet
      for (i = 0; i < 7; i++) {
          add(l[i]);
          add(t[i]);
      }
      for ( i = 0 ; i < 4; i++) {
         add(cb1[i]);
       }
      for ( i = 0 ; i < 4; i++) {
         cb[i].setState(true);
         add(cb[i]);
       }
       add(b[0]);
       add(b[1]);

   }
   public void actionPerformed(ActionEvent ae)
   {
      int i;
      if (ae.getActionCommand().equals("Add"))
      {

         StringBuffer s = new StringBuffer();
         boolean c[] = new boolean[4];
         
         for (i = 0; i < 4; i++) {
            c[i] = cb[i].getState();
            cb[i].setState(false);
         }

         for (i = 0; i < 7; i++) {
            if ((i == 0) && c[0] == false)
               continue;
            else if ((i == 1) && (c[1] == false))
               continue;
            else if ((i == 5) && (c[2] == false))
               continue;
            else if ((i == 6) && (c[2] == false))
               continue;

            s.append( new StringBuffer(l[i].getText() + " " + t[i].getText() + " "));
          }
          for (i = 0; i < 7; i++)
            t[i].setText("");
         if (c[3] == false)
            s.append(new StringBuffer(cbg.getSelectedCheckbox().getLabel() + " "));
         markList[ptr] = new StringBuffer();
         markList[ptr] = s;
         ptr++;
         for ( i = 0 ; i < 4; i++) {
         cb[i].setState(true);
       }


      }
      else if (ae.getActionCommand().equals("Generate Mark List"))
      {
         for (i = 0; i < ptr;i++)
            list.add(markList[i].toString());
         f.add(list);
         f.setVisible(true);
         f.addWindowListener(
            new WindowAdapter() {
               public void windowClosing(WindowEvent e)
               {
                  list.clear();
                  f.setVisible(false);
               }
            }
         );
      }
      else if (ae.getSource() == t[4])
      {
         t[5].setText(Integer.toString(Integer.parseInt(t[2].getText()) +
                        Integer.parseInt(t[3].getText()) +
                        Integer.parseInt(t[4].getText())));
         t[6].setText(Integer.toString(Integer.parseInt(t[5].getText()) / 3));
      }         
   }
}
