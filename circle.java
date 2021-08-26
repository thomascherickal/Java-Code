/////////////////////////////////////////////////////////
//	Perimeter & Area of a circle experiment        //
/////////////////////////////////////////////////////////
import java.io.*;


class Circle {
   private double radius;
   static double pi = 3.14159265358979;
   Circle (double radius) 
   {
      this.radius = radius;
   }
   private double Area()   
   {
       return pi * radius * radius;
   }
   private double Circumference()
   {
       return 2 * pi * radius;
   }
   public static void main(String args[]) throws IOException
   {
       System.out.println("AREA OF A Circle USING BufferedReader class");
	 System.out.println("*******************************************");
	 System.out.println("Enter radius");	
       BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
       Circle c = new Circle(Double.parseDouble(br.readLine()));  
       System.out.println("Area is" + c.Area()); 
       System.out.println("Perimeter is " + c.Circumference());
   }
}	 