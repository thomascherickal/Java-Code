import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
/**
<html>
<applet code = "ChatClient.class" width = 300 height = 200>
</applet>
</html>
*/
  
public class ChatClient extends JApplet implements Runnable{
    
    private JTextField text;
    private JTextArea display;
    private Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Thread messageThread;
    private String message;
    
    public void init()
    {
        text = new JTextField();
        getContentPane().add(text, BorderLayout.SOUTH);
        text.setEnabled(false);
        text.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    sendData(e.getActionCommand());
                }
            }
      );
        display = new JTextArea(100, 35
        );
        display.setEditable(false);
        getContentPane().add(new JScrollPane(display), BorderLayout.NORTH);


     }
     public void start()
     {
        try {
            display.setText("\nAttempting connection");
            connection = new Socket(InetAddress.getLocalHost(), 1000);
            display.append("\nConnected to: " + connection.getInetAddress().getHostName());
            input = new ObjectInputStream(connection.getInputStream());
            output = new ObjectOutputStream(connection.getOutputStream());
            display.append("\nGot I/O Streams");
            text.setEnabled(true);        
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        messageThread = new Thread(this);
        messageThread.start();

        
    }
    
    public void run() {
       try {
           do {
               try {
                message = (String) input.readObject();
                display.append("\n" + message);
                display.setCaretPosition(display.getText().length());
            } catch (ClassNotFoundException cnfex ) {
                display.append("\nUnknown Object Type Recieved");
            }
       } while (!message.equals("SERVER >>> TERMINATE"));
            
        display.append("Closing connection.\n");
        input.close();
        output.close();
        connection.close();
        }
        catch(IOException ioex)
        {
           System.out.println("\nError in closing streams/socket");
           ioex.printStackTrace();
        }
    }

    private void sendData(String s)
    {
        try {
            message = s;
            output.writeObject("\nCLIENT>>> " + s);
            output.flush();
            display.append("\nCLIENT>>> " + s);
        }
        catch (IOException ioex) {
            display.append("\nError writing object");
        }
    }
}
    
    
