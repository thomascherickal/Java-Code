
//Electricity Bill Generation

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.*;


public class elec implements ActionListener
{
   Label l[];
   TextField t[];
   Choice c[];
   Button Add;
   Frame frm;

   Button Clear;
   static FileWriter fw;
   elec()
   {
      //Initialize GUI Controls
      frm = new Frame("Electricity Bill Generation");
      frm.setSize(500, 140);
      String labels[] = {"Name", "Phone No","Hours used", "Rate", "Total Amount" };
      int i, j;
      l = new Label[5];
      frm.setLayout(new FlowLayout());
      t = new TextField[5];
      c = new Choice[2];
      for (i = 0; i < 5; i++) {
         l[i] = new Label(labels[i]);
         t[i] = new TextField(20);
      }
      String list[][] ={
                        { "75%", "80%", "90%" },
                        {"Cash", "Check", "Card" }
                        } ;
      for (i = 0; i < 2; i++) {
         c[i] = new Choice();
         for (j = 0; j < 3; j++)
            c[i].add(list[i][j]);
      //Open the file
      try {
       fw =  new FileWriter("Ebill.dat");
      } catch (IOException ioe) { }
      }
        

      Add = new Button("Add");
      Clear = new Button("Clear");
      for (i = 0; i < 5; i++) {
         frm.add(l[i]);
         frm.add(t[i]);
      }
      frm.add(c[0]);
      frm.add(c[1]);
      frm.add(Add);
      frm.add(Clear);
      //register event listeners
      t[3].addActionListener(this);
      Clear.addActionListener(this);
      Add.addActionListener(this);
      //close the window event
      frm.addWindowListener(
         new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
            try {
               fw.close();
            }
            catch(IOException ioe) { }
               System.exit(0);
            }
         }
      );
      frm.setVisible(true);
   }
   public void actionPerformed(ActionEvent e)
    {
      int i;
      // click Add
      if (e.getActionCommand().equals("Add"))
         addEBData();
      //click Clear
      else if (e.getActionCommand().equals("Clear")) {
         for (i = 0; i < 5; i++)
            t[i].setText("");
         for (i = 0; i < 2; i++)
            c[i].select(0);
       }
     //Press Enter in Rate Field
     else {
         double bill = Double.parseDouble(t[3].getText()) *
                                      Double.parseDouble(t[2].getText());
         t[4].setText(Double.toString(bill));
     }
   }
   private void addEBData() 
   {

      //output to file
      StringBuffer s = new StringBuffer(t[0].getText() + t[1].getText() +t[2].getText() +
                 t[3].getText() + t[4].getText() + c[0].getSelectedItem() +
                 c[1].getSelectedItem());

      try {
      fw.write(s.toString());
      } catch (IOException  ioe) { }
   }
   
   //start the application   
   public static void main(String args[])
   {

      elec e = new elec();
   }
}
             
