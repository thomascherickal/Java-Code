import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChatServer extends JFrame {
    private JTextField message;
    private JTextArea display;
    ObjectInputStream input;
    ObjectOutputStream output;
       
    public ChatServer() {
        super("ChatServer");
        Container c = getContentPane();
        message = new JTextField();
        message.setEnabled(false);
        message.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sendData(e.getActionCommand() );
            }
        }
        );
        c.add(message, BorderLayout.SOUTH);
        display = new JTextArea();
        c.add(new JScrollPane( display ), BorderLayout.CENTER );
        setSize(300, 150);
        show();
    }
   private void sendData(String s)
   {
       try {
           output.writeObject("SERVER>>> " + s);
           output.flush();
           display.append(" \nSERVER>>> " + s);
       } catch (IOException ioex) {
           display.append("\nError writing object");
       }
   }
   public void runServer()
   {
       ServerSocket server;
       Socket connection;
       int counter = 1;
       try {
           server = new ServerSocket(1000, 2);
           while (true) {
               display.setText("\nWaiting for connection");
               connection = server.accept();
               display.append("\nConnection " + counter + "recieved from " + 
                       connection.getInetAddress().getHostName());
               output = new ObjectOutputStream(connection.getOutputStream());
               output.flush();
               input = new ObjectInputStream(connection.getInputStream());
               display.append("\nGot I/O Streams");
               String msg = "\nSERVER >>> Connection successful";
               output.writeObject(msg);
               output.flush();
               message.setEnabled(true);
               do {
                   try {
                       msg = (String) input.readObject();
                       display.append("\n" + msg);
                       display.setCaretPosition(display.getText().length());
            } catch (ClassNotFoundException cnfex ) {
                display.append("\nUnknown Object Type Recieved");
            }
       } while (!msg.equals("CLIENT >>> TERMINATE"));
                  
         display.append("\nUser terminated connection");
         display.setEnabled(false);
         output.close();
         input.close();
         connection.close();
         ++counter;
       }
       }
           catch (EOFException eofex) {
               System.out.println("\nClient terminated connection");
           }
           catch(IOException ioex) {
               ioex.printStackTrace();
           }
     }
    public static void main(String[] args) {
        ChatServer app  = new ChatServer();
        app.addWindowListener (
                new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
 
        }
        );       
        app.runServer();
    }
}
