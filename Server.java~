/***************************************************************************
 *                                                                         *
 *			CLIENT SERVER DISTRIBUTED PROCESSING - SERVER      *
 *                                                                         *
 **************************************************************************/


/**Program to implement a Server for a Desktop Parallel
  * Processing project. Feed in asynchronously parallel
  * programs, the DPServer compiles them, sends the class
  * files to  remote computers, where a client program
  * executes them, and sends back the output.
  *@author Thomas Mathew Cherickal
  *@version 1.00
  *@comments Praise the Lord!
  */

//import statements

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.util.*;



/********************************************************
 *                 HELPER CLASSES                       *
 ********************************************************/

/**class required to implement Serialization on a File
  *@author Thomas
  */
class SerialFile extends File implements Serializable
{
   SerialFile(String s){ super(s); }
}



/**rmi stub required for Remote Method Invocation
  * Contains varous functions used by the client sources to synchronize
  * the operation of the distributed program, follows a star configuration
  * of communication between clients
  *@author Thomas Mathew Cherickal
  */
interface StubInterface extends Remote
{
   public void signalClientFinished(int id) throws RemoteException;
   public void waitForClients(int id) throws RemoteException;
   public void notifyClients(int id) throws RemoteException;
   public void notifyAllClients(int id) throws RemoteException;
   public void send(int sender, int reciever) throws RemoteException;
   public void recieve(int sender, int reciever) throws RemoteException;
   public void send(int sender) throws RemoteException;
   public void recieve(int sender) throws RemoteException;
}


/********************************************************
 *                 PUBLIC Server CLASS                  *
 ********************************************************/


/**public class that implements the server back-end operations
  *@author Thomas Mathew Cherickal
  */

public class Server extends UnicastRemoteObject implements Runnable, StubInterface
{
   // rear end processing members
   Vector files;
   Vector directories[];
   Vector addresses;
   Vector output;

   //maximum clients
   /**@value MAX_CLIENTS = 100
     */
   final int MAX_CLIENTS = 100;


   int noclients;
   int clientId;
    boolean clientFinished[];
   Thread server_thread[];
   ThreadSpecificData thrdData;



/********************************************************
 *         SERVER BACK-END SYSTEM SIDE OPERATIONS       *
 ********************************************************/


    /**Constructor that allocates memory and stars up the User Interface
      *@param None
      *@return It's a constructor
      *@exception RemoteException on error starting UnicastRemoteObject
	*/

   Server(String args[])  throws RemoteException
    {
      //invoke UncastRemoteObject's Constructor
	super();
      parseCommandLineArgs(args);
	//Allocate memory
      output = new Vector(MAX_CLIENTS);


	clientFinished = new boolean[MAX_CLIENTS];
      server_thread = new Thread[MAX_CLIENTS];
      thrdData = new ThreadSpecificData();

	//start user interface
	new ServerInterface();
    }


    /**start the threads that interact with the client
      *@param None
      *@return void
      */

    void startServerThreads()
    {
      System.out.println(noclients);
      clientId = 0;
	while (clientId < noclients)
      {
         //construct and start thread
         server_thread[clientId] = new Thread(this, "" + clientId);
         server_thread[clientId].start();

         System.out.println("Thread "+ clientId  + " started");

	   clientId++;
      }

    }



  /**tell the threads to wait
      *@param None
      *@return void
      */
    synchronized void waitforClient() throws InterruptedException
    {
      try {
      wait();
      } catch( InterruptedException e)
      {
         System.out.println("LOCAL ERROR:System level interrupt stopped this thread");
	   throw e;
       }

    }


    /****************************************************************
     *        (SYNCHRONIZATION) REMOTE METHODS IMPLEMENTATION       *
     ****************************************************************/


    /**remotely callable function that tells the server that
        * the client is waiting for another client(s)
      *@param integer id of the client
      *@return void
      *@exception RemoteException if any error in RMI
      */

   public void waitForClients(int id) throws RemoteException
   {

   }



   /**remotely callable function that tells the server to
     *notify other clients waiting
     *@param integer id of the client
     *@return void
     *@exception RemoteException if any error in RMI
     */

   public void notifyClients(int id) throws RemoteException
   {

   }



   /**remotely callable function that tells the server to
     * notify all clients waiting
     *@param integer id of the client
     *@return void
     *@exception RemoteException if any error in RMI
     */

   public void notifyAllClients(int id) throws RemoteException
   {

   }




   /**remotely callable function that asynchronously sends a message between
     *client sender and client reciever
     *@param integer ids of participating clients
     *@exception RemoteException if any error in RMI
     *@return void
     */

   public void send(int sender, int reciever) throws RemoteException
   {

   }


   /**remotely callable function that performs a blocking recieve
     *on a message sent by client sender
     *@param integer id of the clients participating
     *@exception RemoteException if any error in RMI
     *@return void
     */

   public void recieve(int sender, int reciever) throws RemoteException
   {

   }


   /**remotely callable function that does an asynchronous send to
     * all processes which can recieve it, ie, are in blocking recieve state
     *@param integer id of the client sender
     *@exception RemoteException if any error in RMI
     *@return void
     */

   public void send(int sender) throws RemoteException
   {

   }



   /**remotely callable function that tells the server that
     * the client is blocking for a message from any client
     *@param integer id of the client
     *@exception RemoteException if any error in RMI
     *@return void
     */

   public void recieve(int reciever) throws RemoteException
   {

   }





   /**remotely callable function that tells the server that
       * the client has finished
     *@param integer id of the client
     *@return void
     */

    public void signalClientFinished(int id) throws RemoteException
    {
      clientFinished[id] = true;

	synchronized(this) {   notifyAll();  }
    }


/********************************************************
 *        INNER CLASS SERVER THREAD ENCAPSULATION       *
 ********************************************************/

    /**inner class that encapsulates the data necessary for each server thread
      *@author Thomas Mathew Cherickal
	*/

    class ThreadSpecificData
    {
	//various streams
      SerialFile file;
	ObjectOutputStream objoutstream;
      ObjectInputStream objinstream;

      //sockets
      ServerSocket servSock;
      Socket sock;
	/**Initialize the connections
	  *@param integer thread id
	  *@return void
        */
      void InitConnections(int id) throws EOFException, IOException
      {

	 try
      {
	sock = new Socket();
      System.out.println(addresses.elementAt(id));
      servSock = new ServerSocket(Integer.parseInt(addresses.elementAt(id)+ ""), 1);
      System.out.println("Waiting for connection " + id);
      sock = servSock.accept();
      objoutstream = new ObjectOutputStream(sock.getOutputStream());
      System.out.println("Got connection " + id);


      } catch (EOFException eofe) {
     System.out.println("LOCAL ERROR: OutputStream of Client " + id + " = null");
     throw eofe;

      } catch(IOException ioe) {
      System.out.println("LOCAL ERROR: IO problem at Client " + id + " = null");
      throw ioe;
      }
   }


   /**Close the connections, streams, sockets
     *@param integer thread id
     *@return void
     */

   void CloseStreamsSockets()
   {
       try {
	   servSock.close();
	   objinstream.close();
	   objoutstream.close();
         sock.close();
        } catch(IOException ioe) {
		System.out.println("WARNING: Could not close all streams and sockets");
	  }
    }



   /**Write the file to the client
     *@param integer thread id
     *@return void
     */

   void WriteFile(int id) throws IOException
   {
   try {                                                 //make the .java file a .class file
      file = new SerialFile(files.elementAt(id).toString().replaceFirst(".java", ".class"));
         objoutstream.flush();
         //serialization
         objoutstream.writeObject(file);
          System.out.println("File " + files.elementAt(id) + " sent to client " + id  + " at address " + addresses);
      } catch(IOException ioe) {
         System.out.println( "LOCAL ERROR: Error in sending file to Client  " + id);

         throw(ioe);
	}
   }



   /**Get the output from the client
     *@param integer thread id
     *@return void
     */

   void GetOutput(int id) throws IOException, ClassNotFoundException, InterruptedException
   {
	try {

      objinstream = new ObjectInputStream(sock.getInputStream());

       while(!clientFinished[id])
         waitforClient();
      //the server waits till the client calls signalClientFinished()

	  output.addElement(objinstream.readObject());
      } catch(IOException e) {

	  System.out.println("LOCAL ERROR: Error in getting output of client " + id);
        throw e;
 	} catch(ClassNotFoundException cnfe) {
	 System.out.println("LOCAL ERROR: Error in reading output of client " + id);
        throw cnfe;
      }
      catch(InterruptedException e) {
      System.out.println("LOCAL ERROR: Waiting for client interrupted");
      System.out.println(e);
      throw e;
       }

      System.out.println("Client " + id + " finished");
   }


  //end of inner class ThreadSpecificData
  }

   /**all the server threads start here
      *@param None
      *@return void
      */
    public void run()
    {
      int id = Integer.parseInt(Thread.currentThread().getName());
      try {
	thrdData  = new ThreadSpecificData();
      thrdData.InitConnections(id);
      thrdData.WriteFile(id);
      thrdData.GetOutput(id);
      thrdData.CloseStreamsSockets();
     } catch(Exception e) {
		return;
	}
    }





/********************************************************
 *            INNER CLASS USER INTERFACE                *
 ********************************************************/


 /**Inner class that implements a user interface
     *uses the Swing GUI
     *@author Thomas
     */

   private class ServerInterface implements ActionListener
   {
      //various Swing components
	JFrame frame;
      JLabel label[];
      JList lst;
      JTextField txt;
      JButton button[];
      JComboBox combo;


    /**sets up the JFrame frame
      *@param None
      *@return void
      */

      private void SetUpFrame()
	{
	   frame = new JFrame("Parallel Processing Server");
         frame.setSize(400, 250);
         frame.getContentPane().setLayout(new FlowLayout());
         frame.addWindowListener(
               new WindowAdapter()
               {
                  public void windowClosing(WindowEvent e)
                  {
                     frame.setVisible(false);
                     System.exit(0);
                 }
                }
           );
	}



   /**sets up the JList lst
      *@param None
      *@return void
      */

  	private void SetUpList()
	{
	   lst = new JList();
      lst.setVisibleRowCount(10);
      lst.setFixedCellWidth(300);
      String list_of_files[] = new String[MAX_CLIENTS];
	int i;
	for (i = 0; i < files.size(); i++)
		list_of_files[i] = (String)files.elementAt(i);
     lst.setListData(list_of_files);
   }


    /**sets up the JTextField txt
      *@param None
      *@return void
      */

      private void SetUpTextField()
	{
	   txt = new JTextField(10);

         txt.addFocusListener(
            new FocusAdapter() {

               public void focusLost(FocusEvent fe)
               {
                  String clist[] = new String[MAX_CLIENTS];
			combo.removeAll();
			try
                   {

			if (txt.getText().equals("") || Integer.parseInt(txt.getText()) < 1)
                  {
                     txt.setText("" + 1);

                   }
			    int i;
			   for (i = 0; i < Integer.parseInt(txt.getText()); i++)
                           combo.addItem(clist[i] = new String("" + i));
                    } catch (NumberFormatException e)
                    {
                     txt.setText(1 + "");

			  combo.addItem(clist[0] = new String(0+ ""));
                    }

               }
            }
         );
    }


    /**sets up the combo box
      *@param None
      *@return void
      */


	private void SetUpComboBox()
	{
	     combo = new JComboBox();
           combo.addActionListener(this);
   }



    /**sets up the other various controls
      *@param None
      *@return void
      */

	private void InitializeControls()
	{
	   int i;

         String blist[] = { "Compile All", "Run All", "View Output" };
         String llist[] = { "No of Clients", "Client Files"};


	   label = new JLabel[llist.length];
         button = new JButton[blist.length];

	   for (i = 0; i < blist.length; i++)
         {
            button[i] = new JButton(blist[i]);
            //register for events
            button[i].addActionListener(this);
         }

         for (i = 0; i < llist.length; i++)
            label[i] = new JLabel(llist[i]);
	}



   /**adds various controls to the form
      *@param None
      *@return void
      */

	private void AddControls()
	{
	   int i;
         frame.getContentPane().add(label[0]);
         frame.getContentPane().add(txt);
         frame.getContentPane().add(combo);

         frame.getContentPane().add(label[1]);
         frame.getContentPane().add(new JScrollPane(lst));
	   for (i = 0; i <  button.length; i++)
	   frame.getContentPane().add(button[i]);
      }


    /**starts the GUI
      *@param None
      *@return void
      */

	private void StartGUI()
	{
	   //Disable Run All and View Output buttons
	   button[1].removeActionListener(this);
         button[2].removeActionListener(this);
         frame.setVisible(true);
	}

    /**Constructor
      *@param None
      *@return constr
      */

      ServerInterface()
      {
         SetUpFrame();
	   SetUpList();
   	   SetUpTextField();
         SetUpComboBox();
	   InitializeControls();
         AddControls();
         StartGUI();

      }




    /**Compile the Source Files for all the clients
      *@param None
      *@return void
      */

	private void CompileSourceFiles()
	{
	    String compilation[] =  new String[MAX_CLIENTS];
          //local iterators
	    int i, j, k = 0;

       Process compiler;
	  try {
           System.out.print("Wait, compiling files..");
           for (i = 0; i < files.size(); i++)
            {

		   System.out.print(".");
         //change code here to incorporate compiling in various directories
         compiler = Runtime.getRuntime().exec("javac " + files.elementAt(i));

               if (compiler.waitFor() != 0)
                  compilation[k++] = new String("Error in compiling program  of client " + i);
               else
			compilation[k++] = new String("Compiled program of client " + i);

            }
		System.out.println(" ");

            }
		  catch(InterruptedException e) {
			System.out.println("FATAL ERROR: Compilation interrupted");
			System.out.println(e);
			System.exit(1);
            }
		  catch(IOException e) {
			System.out.println("FATAL ERROR: Compilation process I/O Error");
			System.out.println(e);
			System.exit(1);
            }

            lst.setListData(compilation);

		for (i = 0; i < k;i++)
               System.out.println(compilation[i]);

            //Enable Run All command
		button[1].addActionListener(this);
    }



    /**Run threads for all the clients
      *@param None
      *@return void
      */


   	public void RunThreads()
	{
	    	int i;
		noclients = Integer.parseInt(txt.getText());

		startServerThreads();
            //while running
            try
            {
            for (i = 0; i < noclients; i++)
               server_thread[i].join();
            } catch(InterruptedException e)
            {
              	System.out.println("FATAL ERROR: Running of server main thread interrupted");
			System.out.println(e);
			System.exit(-1);
            }

           // after running, enable View Output button
           button[2].addActionListener(this);
	}



    /**process Action Events
      *@param None
      *@return void
      */


      public void actionPerformed(ActionEvent ae)
      {

          if (ae.getActionCommand().equals("Compile All"))
            CompileSourceFiles();

         else if(ae.getActionCommand().equals("Run All"))
         	RunThreads();

         else if (ae.getActionCommand().equals("View Output"))
         {
              lst.setListData(output);
         }
         else if (ae.getSource() == combo)
         {
      	try {
	       String list_of_files[] = new String[MAX_CLIENTS];
		   int i;
		   for (i = 0; i < files.size(); i++)
			list_of_files[i] = (String)files.elementAt(i);
		   lst.setListData(list_of_files);
               } catch(NumberFormatException e) {
      	         combo.setSelectedIndex(0);

		  } catch(ArrayIndexOutOfBoundsException e) {

           }
         }


      }
    }
   /**Function that parses the all-important arguments
     *@param Command Line arguments
     *@return void
     */
	void parseCommandLineArgs(String args[])
	{

	// add your own exceptions later to check for invalid arguments

     //check whether all arguments are supplied
      if (args.length == 0 || args.length == 1)
      {
         System.out.println("FATAL ERROR: supply");
	   System.out.println("(1) starting value of client addresses");
	   System.out.println("(2)array of files as command line arguments");
	   System.out.println("eg. 5000 c1.java c2.java");
         System.exit(1);
      }
      else
      {
         //allocate memory
 	   files = new Vector(MAX_CLIENTS);
      int i, j, k = 2;
           noclients = args.length - 1;


         for (i = 0; i < noclients; i++) {
             files.addElement(args[i + 1]);
	    }
      }
}
/********************************************************
 *            START OF EXECUTION MAIN()                 *
 ********************************************************/
  /**Main function
    *@param arg[0] starting address for connections
    *@param arg[1] to maximum arg[NO_CLIENTS * MAX_SUBS] files names of each client
    *@return nothing
    */
    public static void main(String args[])
    {

      try {
         Runtime.getRuntime().exec("rmiregistry");
      } catch (Exception e)
      {
         System.out.println("FATAL ERROR:Cannot start rmi(check if java is installed");
         System.exit(1);
      }

      try {


	System.out.println("Initiating Server..");

   Server s = new Server(args);

	// create an rmiregistry entry for the Server object
	String servername = "//localhost/Server";

	Naming.rebind(servername, s);

	System.out.println("Server up and running");

	} catch (RemoteException re) {
         System.out.println("FATAL ERROR: Remote Error on attempt to register using rebind");
			System.out.println(re);
			System.exit(1);

      } catch (MalformedURLException mue) {
           System.out.println("FATAL ERROR: Error choosing server name");
			System.out.println(mue);
			System.exit(1);
      }
   }




/********************************************************
 *            		End Of File                     *
 ********************************************************/


}

