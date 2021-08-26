import java.io.*;

class FileCount
{
   public static void main(String args[])
   {
      File f = new File(args[0]);
      System.out.println(f.length());
   }
}
