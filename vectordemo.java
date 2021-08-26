/////////////////////////////////////////////////////////
//	Vectors demonstration experiment               //
/////////////////////////////////////////////////////////


import java.util.*;
import java.io.*;
import java.lang.*;


class vectordemo
{
     public static void main(String args[])throws IOException
     {
        Vector V = new Vector(10,1);

        int choice=1;
        InputStreamReader n1 = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(n1);
        while(choice>=1)
        {
        System.out.println("        VECTOR CLASSES USAGE");
        System.out.println("        ********************");
        System.out.println("        1.Add an element    ");
        System.out.println("        2.Check Capacity    ");
        System.out.println("        3.Find Element");
        System.out.println("        4.Remove an element ");
        System.out.println("        5.Remove All        ");
        System.out.println("        6.Set Size          ");
        System.out.println("        0.Exit              ");
        System.out.println("Enter the choice");
        choice = Integer.parseInt(br.readLine());


        int element, place;
        switch(choice)
        {
        case 1:
        System.out.println("Enter the element to be added:");
        Integer i1 = new Integer(br.readLine());
        V.addElement(i1);
	    break;
	case 2:
        System.out.println("Capacity = " + V.capacity());
	    break;
	case 3:
        System.out.println("Enter the element to be found:");
        Integer i2 = new Integer(br.readLine());
        place = V.indexOf(i2);
	    if (place == -1)
		System.out.println("Element not found");
	    else
        System.out.println(i2 + "found at" + place);
	    break;
	case 4:
        System.out.println("Enter the element to be removed:");
        Integer i3 = new Integer(br.readLine());
        V.removeElement(i3);
	    System.out.println("The element is removed");
	    break;
	case 5:
        V.removeAllElements();
	    break;
	case 6:
	    System.out.println("Enter the size");
        element = Integer.parseInt(br.readLine());
        V.setSize(element);
	    break;

	case 0:
	    System.exit(0);
	    break;
	default:
	    System.out.println("Unrecognized command , re-enter");
	    break;
        }
	}
   }

}

	    
		       
	        
            
        



        



