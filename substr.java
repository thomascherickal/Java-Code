/////////////////////////////////////////////////////////
//	Substring removal experiment                   //
/////////////////////////////////////////////////////////
import java.io.*;

class substr
{
    public static void main(String args[]) throws IOException
    {
        DataInputStream d = new DataInputStream(System.in);
        String str;
        String substr;
        StringBuffer newstr = new StringBuffer();
        int loc[];
        System.out.println("\t\t\tFINDING AND REPLACING SUBSTRING");
        System.out.println("\t\t\t*******************************");
        System.out.println("\nEnter the string");
        str = d.readLine();
        System.out.println("\nEnter the substring");
        substr = d.readLine();
        int strlen = str.length();
        int sublen = substr.length();
        loc = new int[strlen/sublen];
        int Iterator;
        int Index = 0;
        /*Find the location of the substrings*/
        for (Iterator = 0; Iterator < strlen - sublen; Iterator++)
        {
            if (str.regionMatches(Iterator, substr, 0, sublen) == true)
                loc[Index++] = Iterator;
        }

        loc[Index] = -1;
        Index = 0;
        for (Iterator = 0; Iterator < strlen; Iterator++)
        {
            if (Iterator == loc[Index])
            {
                Index++;
                Iterator += sublen - 1;
                continue;
            }
            else  newstr.append(str.charAt(Iterator));
               
        }
        System.out.println("The new string is " + newstr);
        System.exit(0);
    }
}
                

