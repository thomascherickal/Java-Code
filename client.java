/***************************************************************************
 *                                                                         *
 *			CLIENT SERVER DISTRIBUTED PROCESSING - CLIENT            *
 *                                                                         *
 **************************************************************************/
//import statements

import java.net.*;
import java.io.*;
import java.rmi.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;




/********************************************************
 *                 HELPER CLASS                         *
 ********************************************************/    

/**class required to serialize files
  *@author Thomas
  */


class SerialFile extends File implements Serializable
{
   SerialFile(String s) { super(s); }
}




/********************************************************
 *                 PUBLIC Client CLASS                  *
 ********************************************************/   


/**Client class that connects to the Server, gets a source file
  * and executes it
  *@author Thomas
  *@version 1.00
  *@comments Praise the Lord
  */	



public class Client extends JFrame implements Runnable
{

   //GUI
   JTextArea textarea;

   //connection
   Socket sock;
   
   //I/O streams
   ObjectInputStream objinstream;
   ObjectOutputStream objoutstream;
   ObjectInputStream proginstream;
   
   static String msg;
 
  //remote object inteface
   StubInterface server;

   //thread	
   Thread client_thread;

   //output of program
   Object output;
   
   //source file
   SerialFile file;

   



   /**look up  server and start thread
    *@param identification number of client
    *@return constructor function
    */
  
   Client(int id)
   {
   super("Parallel Processing Client " + id);

      InitializeGUI();
	LookUpServer();
      
	client_thread = new Thread(this, id + "");
      client_thread.start();
   }

/********************************************************
 *             USER INTERFACE OPERATIONS                *
 ********************************************************/ 

  /**Initialize the JFrame
    *@param nothing
    *@return void
    */

 
 void InitializeGUI()
  {
     super.setSize(300, 250);
     super.getContentPane().setLayout(new FlowLayout());
     
     textarea = new JTextArea("", 10, 10);
     textarea.setEditable(false);
     
     super.getContentPane().add(new JScrollPane(textarea));
     super.setVisible(true);
  }


/********************************************************
 *         CLIENT BACK-END SYSTEM SIDE OPERATIONS       *
 ********************************************************/ 
  /**look up  server
    *@param nothing
    *@return void
    */

   private void LookUpServer()
   {
	try {
	   //change to a remote machine where required
         server = (StubInterface) Naming.lookup("//localhost" + "/Server");
          
      } catch (MalformedURLException e)
      { System.out.println("FATAL ERROR:Unable to locate host: wrong name");
        System.exit(1); 		 		
      } catch (java.rmi.NotBoundException e)
      {  System.out.println("FATAL ERROR:Server not bound to registry or invalid name");
         System.exit(1);	
	} catch (java.rmi.ConnectException ce)
      {
        System.out.println("FATAL ERROR:Server is unavailable");
	  System.exit(1);	
      } catch(java.rmi.RemoteException e) { 
		System.out.println("FATAL ERROR: Remote Exception during RMI initialization");
		System.exit(1); 
	}
   }




   /**display messages
    *@param String the message to display
    *@return void
    */
  
   private void display(String msg)
   {	
   textarea.setText(textarea.getText() + "\n" + msg);
   super.repaint();
   System.out.println(msg);
   } 
	



  /**connect to server and get streams
    *@param nothing
    *@return void
    */
  
 private void ConnectandGetStreams(int id)
   {
	 try {
      display( "Attempting to connect to server at address " + (5010 + 10 * id));
      sock = new Socket(InetAddress.getByName("localhost"),5010 + 10 * id);
      display( "Connected to " + sock.getInetAddress().getHostName());
      
      
      objinstream = new ObjectInputStream(sock.getInputStream());
      objoutstream = new ObjectOutputStream(sock.getOutputStream());
      display( "Got I/O Streams");
      } catch (IOException e) { System.out.println("FATAL ERROR: Cannot connect to server (IOException)");
					  System.exit(-1);
      }
   }


  /**Close all connections
    *@param nothing
    *@return void
    */
   
    private void CloseStreamsandExit(int id)
    {
        try {
        objinstream.close();
        objoutstream.close();
        sock.close();
        } catch (IOException e) {
         System.out.println("WARNING: Not all connections closed");
       }

       display ("Client " + id + " finished.");

       //change here to auto-exit

       super.addWindowListener(
         new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
              System.exit(0);
            }
         }
      );
    }

  /**get the client file from the server
    *@param nothing
    *@return void
    */  
   private void GetClientFile()
   {
		try
      {
         //deserialization

         file =  (SerialFile) objinstream.readObject();
         display( "Got client file");
      }  catch (IOException e)  { System.out.println("FATAL ERROR: Cannot read source file (IOException)");
					  System.exit(-1); }
         catch (ClassNotFoundException e) {System.out.println("FATAL ERROR: Invalid Object, cannot downcast to SerialFile");
					  System.exit(-1);;  }
   }



  /**execute the client source file
    *@param nothing
    *@return void
    */
   private void ExecuteFile(int id)
   {

      try {
	display( "Executing client file");
      
	Process prog = Runtime.getRuntime().exec( "java " + file.getName().substring(0, file.getName().indexOf(".class")));
      
	proginstream = new ObjectInputStream(prog.getInputStream());
      output = proginstream.readObject();
      
	if (prog.waitFor() != 0) {
         display("Program not completed sucessfully");
          System.exit(-1);
      }
      }   catch(EOFException e) {
         System.out.println("Program streamed nothing");      
       } catch (InterruptedException e) { 
			System.out.println("FATAL ERROR: Execution Interrupted");
			 System.exit(-1);
      } catch(IOException e) { 
			System.out.println("FATAL ERROR: Could not read output");
			  System.exit(-1);

      } catch(ClassNotFoundException e) { 
			System.out.println("FATAL ERROR: reading output resulted in error");
			  System.exit(-1);
  	}
   try {
   //all source programs must return their output by writing to System.out
         objoutstream.writeObject(output);
      }  catch (IOException ioe) { System.out.println("FATAL ERROR: Cannot write output to server"); }
      
	//execute rmi remote call
	try {
      server.signalClientFinished(id);
      } catch (RemoteException e){ 
        System.out.println("FATAL ERROR: Unable to signal end of client");
      }
    }



  /**entry point for client thread
    *@param nothing
    *@return void
    */  


   public void run()
   {
      int id = Integer.parseInt(Thread.currentThread().getName());
      display("Client " + id + " started");
      ConnectandGetStreams(id);
      GetClientFile();
      ExecuteFile(id);
      CloseStreamsandExit(id);
   }

/********************************************************
 *            START OF EXECUTION MAIN()                 *
 ********************************************************/

  /**run the program
    *@param args[0] is the identification number of the client
    *@return void
    */         
  

   public static void main(String args[])
   {
      if (args.length == 0)
		System.out.println("Supply id number of client as command line argument");
	else
		new Client(Integer.parseInt(args[0]));
   }  
 

/********************************************************
 *            		End Of File                     *
 ********************************************************/



}
