/////////////////////////////////////////////////////////
//	Character array experiment                     //
/////////////////////////////////////////////////////////
import java.lang.String;
import java.io.*;

class CArray {
    private char a[];
    private int len;
    CArray() throws IOException
    {
        System.out.println("CHARACTER ARRAY HANDLING");
        System.out.println("************************");
        DataInputStream d = new DataInputStream(System.in);
        String s = d.readLine();
        a = s.toCharArray();
        len = s.length();
    }
    char[] Reverse()
    {
        int i = len - 1, j = 0;
        char b[]  = new char[len];
        for ( ; i >=0; i--, j++)
            b[j] = a[i];
        return b;
    }
    char[] Concat(char b[], int len2)
    {
 	  int i = len, j = 0;
	  char c[] = new char[i + len2];
	  for (i = 0; i < len; i++)
		c[i] = a[i];
	  for (i = len; i < len + len2; i++, j++)
		c[i] = b[j];
	  return c;
    }
				
}

public class chararray
{
   public static void main(String args[]) throws IOException
   {
	  CArray c = new CArray();
	  System.out.println(c.Reverse());
        char a[] = { 'o', 'u', 't' };
	  System.out.println(c.Concat(a, 3));
   }
}

       
