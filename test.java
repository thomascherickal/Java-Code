class test
{
   String c[];
   test()
   {
      int i;
      c = new String[5];
      for (i = 0; i < 5; i++)
         c[i] = new String(Integer.toString(i));
    }
    public static void main(String args[])
    {
      System.out.println(Math.sin(2 * Math.PI) < 0.00000001 ? 0  : Math.sin(2 * Math.PI));
      System.out.println(Math.cos(2 * Math.PI));

    }
}
