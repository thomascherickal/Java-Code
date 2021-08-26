import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.rmi.*;
import java.rmi.server.*;
import java.io.*;


public class Server
{
   // rear end processing members
   String files[];
   String addresses[];
   Object output[];
   final int MAX_CLIENTS = 50;
   int clientId = 0;
   Thread t[];

   private class ServerInterface implements ActionListener
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
         f.getContentPane().setLayout(new FlowLayout());
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
         lst = new JList();
         lst.setVisibleRowCount(7);
         lst.addKeyListener(
            new KeyAdapter() {
               public void keyPressed
         l = new JLabel[2];
         b = new JButton[5];
         jf = new JFileChooser();
       

       //  class JavaFilter extends FileFilter
        // {
          //  public boolean accept(File f)
          //  {
          //     return f.getName().endsWith("java");
          //  }
       //  }
         jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
      //   jf.setFileFilter(new JavaFilter());

         // add selection to JList of source files
         
         String blist[] = { "Source Files..", "Compile All", "Run All", "Stop All", "View Output" };
         String llist[] = { "No of Clients", "Client Files"};

         addresses[clientId] = new String("" + con + 10);
         t = new JTextField(10);

         t.addFocusListener(
            new FocusAdapter() {

               public void focusLost(FocusEvent fe)
               {
                  if (t.getText() != null)
                  {
                     int k;
                     try
                     {
                         String clist[] = new String[MAX_CLIENTS];
                         clist[0] = "All";
                         c.addItem(clist[0]);
                         int l = Integer.parseInt(t.getText());
                         for (k = 0; k < l; k++)
                              c.addItem(clist[k] = new String("" + k));
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

         f.getContentPane().add(l[0]);
         f.getContentPane().add(t);
         f.getContentPane().add(c);

         f.getContentPane().add(l[1]);
         f.getContentPane().add(new JScrollPane(lst));


         for (i = 0; i < 5; i++)
         {
            b[i] = new JButton(blist[i]);
            f.getContentPane().add(b[i]);
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
             int result = jf.showOpenDialog(f);
             if (result == JFileChooser.CANCEL_OPTION)
               return;
             else {
               files[ptr++] = new String(jf.getSelectedFile().getName());
               lst.setListData(files);
             
            }
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
            lst.setListData(compilation);
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
            int k;
            lst.setListData(output);
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

