import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

public class ServerInterface implements ActionListener
   {
      JFrame f;
      JLabel l[];
      JList lst;
      JTextField t;
      JButton b[];
      JComboBox c;
      JFileChooser jf;
      int ptr = 0;
      ServerInterface()
      {
         int i;
         int con = 1000;
         f = new JFrame("Parallel Processing Server");
         f.setSize(300, 200);
         getContentPane().setLayout(new FlowLayout());
         f.addWindowListener(
               new WindowAdapter()
               {
                  public void windowClosing(WindowEvent e)
                  {
                     f.setVisible(false);
                     System.exit(0);
                 }
                }
           );

         //initialize controls
         c = new JComboBox();
         String cl[] = { "All" };
         c.add(cl);
         lst = new JList(10);
         l = new JLabel[2];
         b = new JButton[5];
         jf = new JFileChooser();
         jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
         jf.setFileFilter(new FileFilter("*.java"));
         // add selection to JList of source files
         String blist[] = { "Source Files..", "Compile All", "Run All", "Stop All", "View Output" };
         String llist[] = { "No of Clients", "Client Files"};
         addresses[clientId] = new String("" + con + 10);
         t = new JTextField(10);
         t.addFocusListener(
            new FocusAdapter() {
               public void lostFocus(FocusEvent fe)
               {
                  if (t.getText() != null)
                  {
                     int k;
                     try
                     {
                         String clist[] = new String[MAX_CLIENTS];
                         int clientId = Integer.parseInt(t.getText());
                         for (k = 0; k < clientId; k++)
                              clist[k] = new String("" + k);
                         c.add(clist);
                     } catch (NumberFormatException e)
                     {
                        JOptionPane.showMessageDialog(null, "Integer number of clients required", "Error", JOptionPane.ERROR_MESSAGE);
                     }
                  }
               }
            }
         );
         for (i = 0; i < 2; i++)
            l[i] = new JLabel(llist[i]);
         f.add(l[0]);
         f.add(t);
         f.add(l[1]);
         f.add(lst);
         for (i = 0; i < 5; i++)
         {
            b[i] = new JButton(blist[i]);
            f.add(b[i]);
            //register for events
            b[i].addActionListener(this);
         }
         b[2].removeActionListener(this);
         b[3].removeActionListener(this);
         b[4].removeActionListener(this);
         f.setVisible(true);
      }
      public void actionPerformed(ActionEvent ae)
      {
         String compilation[] =  new String[MAX_CLIENTS];
         int i;
         if (ae.getActionCommand().equals("Source Files.."))
         {
             int result = jf.showOpenDialog(this);
             if (result = JFileChooser.CANCEL_OPTION)
               return;
             else
             lst.add(files[ptr++] = new String(jf.getSelectedFile().getName()));
        }

         else if (ae.getActionCommand().equals("Compile All"))
         {
            try {
            for (i = 0; i < clientId + 1; i++)
            {
               Process compiler = Runtime.getRuntime().exec("javac " + files[i]);
               if (compiler.waitFor() != 0)
                  compilation[i] = new String("Error in compiling program " + i);
               else
                  compilation[i] = new String("Sucessfully compiled program " + i);
            }
            } catch(InterruptedException e)
            {
            } catch(IOException e)
            {
            }
            lst.removeAll();
            for (i = 0; i < clientId + 1; i++)
               lst.add(compilation[i]);
            b[2].removeActionListener(this);
         }

         else if(ae.getActionCommand().equals("Run All"))
         {

            //while running
            b[3].addActionListener(this);
           // after running
           b[3].removeActionListener(this);
           b[4].addActionListener(this);
         }
         else if (ae.getActionCommand().equals("Stop All"))
         {
            ;
         }
         else if (ae.getActionCommand().equals("View Output"))
         {
            lst.removeAll();
            for (i = 0; i < clientId + 1; i++)
               lst.add(output[i]+ "");
         }
      }
    }

    Server()
    {
      addresses = new String[MAX_CLIENTS];
      output = new Object[MAX_CLIENTS];
      files = new String[MAX_CLIENTS];
      new ServerInterface();
    }
    public static void main(String args[])
    {
      new Server();
    }
}

