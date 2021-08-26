import java.net.*;
import java.io.*;
import java.rmi.*;
import java.util.*;
import javax.swing.JApplet;
import java.awt.Graphics;


/**Client class that connects to the Server, gets a source file
  * and executes it
  *@author Thomas
  *@version 1.00
  *@comments Praise the Lord
  */	
public class Client extends JApplet implements Runnable
{
   //connection
   Socket sock;
   
   //I/O streams
   FileInputStream filinstream;
   ObjectInputStream objinstream;
   ObjectOutputStream objoutstream;
   ObjectInputStream proginstream;
   
   static String msg;
   static int id;
 
  //remote object inteface
   StubInterface server;

   //thread	
   Thread client_thread;

   //output of program
   Object output;
   
   /**look up  server and start thread
    *@param identification number of client
    *@return constructor function
    */
   Client(int id)
   {
      
	LookUpServer();
      
	client_thread = new Thread(this, id + "");
      client_thread.start();
   }


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
      { System.out.println(e);
      } catch (java.rmi.NotBoundException e)
      {  System.out.println(e);
      } catch (java.rmi.ConnectException ce)
      {
        display("Server is unavailable, exit");
      } catch(java.rmi.RemoteException e) { System.out.println(e); }
   }
   /**display messages
    *@param String the message to display
    *@return void
    */
  
   private void display(String msg)
   {	
	this.msg = msg;
	repaint();
   } 
   /**system call to draw the applet, called by repaint()
     *@param Graphics environment
     *@return void
     */
   
   public void paint(Graphics g)
   {
      g.drawString( msg, 40, 50);
   }
	
  /**connect to server and get streams
    *@param nothing
    *@return void
    */
   private void ConnectandGetStreams()
   {
	 try {
      display( "Attempting to connect to server...");
      sock = new Socket(InetAddress.getByName("localhost"),1000 + 10 * id);
      display( "Connected to " + sock.getInetAddress().getHostName());
      
      //convert id to server context
      id = (id % 1000)/10;
      
      objinstream = new ObjectInputStream(sock.getInputStream());
      objoutstream = new ObjectOutputStream(sock.getOutputStream());
      
      display( "Got I/O Streams");
      } catch (IOException e) { System.out.println(e);
      }
   }

  /**get the client file from the server
    *@param nothing
    *@return void
    */  
   private void GetClientFile()
   {
		try
      {
         filinstream = new FileInputStream((File)objinstream.readObject());

         display( "Got client file");
      }  catch (IOException e)  { }
         catch (ClassNotFoundException e) { }
   }
  /**execute the client source file
    *@param nothing
    *@return void
    */
   private void ExecuteFile()
   {
	try {
	display( "Executing client file");
      Process prog = Runtime.getRuntime().exec("java" + "client" + id +".class");
      proginstream = new ObjectInputStream(prog.getInputStream());

      if (prog.waitFor() != 0) {
         display("Program not completed sucessfully");
         return;
      }
      
       } catch (InterruptedException e) { System.out.println(e);
      } catch(IOException e) { System.out.println(e);
      }
      
	try {
	//all source programs must return their output in the form of System.out.writeObject(obj)
      output = proginstream.readObject();
      objoutstream.writeObject(output);
      }  catch (ClassNotFoundException e) { System.out.println(e);
      }  catch (IOException ioe) { System.out.println(ioe); }
      
	//execute rmi remote call
	try {
      server.signalClientFinished(id);
      } catch (RemoteException e){ }
    
    }

  /**entry point for client thread
    *@param nothing
    *@return void
    */  
   public void run()
   {
  	ConnectandGetStreams();
      GetClientFile();
	ExecuteFile();     
   }
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
 }
