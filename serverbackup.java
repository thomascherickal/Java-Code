   /**Program to implement a server for a Desktop Parallel
  * Processing project. Feed in asynchronously parallel
  * programs, the server compiles them, sends the class
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




/**class required to implement Serialization on a File
  *@author Thomas
  */
class SerialFile extends File implements Serializable
{
   SerialFile(String s){ super(s); }
}



/**rmi stub required for Remote Method Invooation
  *@author Thomas Mathew Cherickal
  */ 
interface StubInterface extends Remote
{
   public void signalClientFinished(int id) throws RemoteException;
    
}




/**public class that implements the server back-end operations
  *@author Thomas Mathew Cherickal
  */

public class Server extends UnicastRemoteObject implements Runnable, StubInterface
{
   // rear end processing members
   Vector files[];
   Vector directories[];
   Vector addresses;
   Vector output;
   
   //maximum clients
   /**@value MAX_CLIENTS = 50
     */
   final int MAX_CLIENTS = 50;
   
   //maximum programs per client
   /**@value MAX_SUBS = 50
     */
   final int MAX_SUBS = 5;
 
   int noclients;
   int clientId;
    boolean clientFinished[];
   Thread server_thread[];
   ThreadSpecificData thrdData;	
    


    /**Constructor that allocates memory and stars up the User Interface
      *@param None
      *@return It's a constructor
      *@exception RemoteException on error starting UnicastRemoteObject
	*/
    
    Server()  throws RemoteException
    {
      //invoke UncastRemoteObject's Constructor
	super();
      
	//Allocate memory
	addresses = new Vector(MAX_CLIENTS);
      output = new Vector(MAX_CLIENTS);
      files = new Vector[MAX_CLIENTS];
	directories = new Vector[MAX_CLIENTS];
	clientFinished = new boolean[MAX_CLIENTS];
      server_thread = new Thread[MAX_CLIENTS];
      thrdData = new ThreadSpecificData[MAX_CLIENTS];
	
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
	  System.out.println(LOCAL ERROR: OutputStream of Client " + id + " = null);
	  throw(e);

      } catch(IOException ioe) { 
		System.out.println(LOCAL ERROR: IO problem at Client " + id + " = null);
	  	throw e;
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
      file = new SerialFile(files[id].elementAt(0).toString().replaceFirst(".java", ".class"));
         objoutstream.flush();
         //serialization
         objoutstream.writeObject(file);
          System.out.println("File " + files[id].elementAt(0) + " sent to client " + id  + " at address " + addresses.elementAt(id));
      } catch(IOException ioe) {
         System.out.println( "LOCAL ERROR: Error in sending file to Client  " + id);
                                       
         throw(ioe);
	}
   }
   


   /**Get the output from the client
     *@param integer thread id
     *@return void
     */

   void GetOutput(int id) throws IOException,. ClassNotFoundException
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
	catch(Exception e) {
		return;
	}
    }
     

  
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
      JFileChooser jfc;
      
    
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
         lst.setVisibleRowCount(7);
         lst.addKeyListener(
            new KeyAdapter() {
               public void keyPressed(KeyEvent ke)
               {
                  if (ke.getKeyCode() == KeyEvent.VK_DELETE)
                  {
                     files[Integer.parseInt(txt.getText()+"")].removeElementAt(lst.getSelectedIndex());
                     lst.setListData(files[Integer.parseInt(txt.getText()+"")]);
                  }
               }
           }
        );
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
           combo.addItemListener(
		   new ItemAdapter() {
		        public void itemStateChanged(ItemEvent e)
			  {
			     try {
      		     addresses.add( Integer.parseInt(combo.getSelectedItem()+ ""), new String("" + (10 * (clientId = (Integer.parseInt(combo.getSelectedItem() + ""))) + 5010)));

     		        } catch(NumberFormatException e) {
      	            combo.setSelectedIndex(0);
            	  }
			  } catch(ArrayIndexOutOfBoundsException e) {
			  }
 		  }
           );
        }
	/**sets up the other various controls
      *@param None
      *@return void
      */

	private void InitializeControls()
	{
	   int i;
	   
         String blist[] = { "Source Files..", "Compile All", "Run All", "View Output" };
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
	   button[2].removeActionListener(this);
         button[3].removeActionListener(this);
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


    /**get the Source Files for each client
      *@param None
      *@return void
      */

      private void GetSourceFiles()
	{        
		String display[][] = new String[MAX_CLIENTS][MAX_SUBS];
		
		

             jfc = new JFileChooser();
             jfc.setFileSelectionMode(JFileChooser.FILES_ONLY); 
             
		 int result = jfc.showOpenDialog(frame);
             if (result == JFileChooser.CANCEL_OPTION)
			 return;
             
		else {
               if (files[clientId] == null) files[clientId] = new Vector(10);
               if (directories[clientId] == null) directories[clientId] = new Vector(10);
          if (!files[clientId].contains(new String(jfc.getSelectedFile().getName()))
               files[clientId].addElement(new String(jfc.getSelectedFile().getName())));
         if (!directories.contains(new String(jfc.getCurrentDirectory().getName()))
              directories[clientId].addElement(new String(jfc.getCurrentDirectory().getName())));    
               
		   //iterator 
		   int i;
		for (i = 0; i < files.length; i++)
			display[clientId][i] = "//" + directories.elementAt(i) + "/" + files.elementAt(i) ;
		   lst.setListData(display[clientId]);
             
            }
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
		 for (j = 0; j < Integer.parseInt(txt.getText() + ""); j++)
                for (i = 0; i < files[j].size(); i++)
            {
               
		   System.out.print(".");
         //change code here to incorporate compiling in various directories
         compiler = Runtime.getRuntime().exec("javac " + files[j].elementAt(i));

               if (compiler.waitFor() != 0) 
                  compilation[k++] = new String("Error in compiling program " + i + " of client " + j);
               else
			compilation[k++] = new String("Compiled program " + i + " of client " + j);
	
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
		button[2].addActionListener(this);
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
           button[3].addActionListener(this);
	}



    /**process Action Events
      *@param None
      *@return void
      */


      public void actionPerformed(ActionEvent ae)
      {
         
         if (ae.getActionCommand().equals("Source Files.."))
        	GetSourceFiles();
   	   
         else if (ae.getActionCommand().equals("Compile All"))
            CompileSourceFiles();  
	   
         else if(ae.getActionCommand().equals("Run All"))
         	RunThreads();
		                                          
         else if (ae.getActionCommand().equals("View Output"))
         {
              lst.setListData(output);
         }

      }
    }


    public static void main(String args[])
    {
      try {
      
	System.out.println("Initiating Server..");
      
	Server s = new Server();
     
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



//EOF
}

