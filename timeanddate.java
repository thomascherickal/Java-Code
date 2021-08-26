/////////////////////////////////////////////////////////
//	Time and Date experiment                       //
/////////////////////////////////////////////////////////
import java.util.Date;
import java.util.Calendar;

class TimeAndDate
{
    public static void main(String args[])
    {
        System.out.println("TIME, DATE, AND CALENDAR functions");
	  System.out.println("**********************************");
	  Date d = new Date();
        Calendar c = Calendar.getInstance();
        String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep","Oct", "Nov", "Dec" };

        System.out.println("Calendar Functions");

        System.out.println("Date is " + c.DATE + months[c.get(Calendar.MONTH)] + c.get(Calendar.YEAR));
        System.out.println("Time is " +  c.get(Calendar.HOUR) + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND));
        
        System.out.println("Era is " + c.ERA);
        System.out.println("Week of Year " + c.WEEK_OF_YEAR);
        System.out.println("Week of month is " + c.WEEK_OF_MONTH);
        System.out.println("ERA is " + c.ERA);

        System.out.println("Date Functions");

        System.out.println("Date is " + d.getDate());
        System.out.println("Day  is " + d.getDay());
        System.out.println("Hours is " + d.getHours());
        System.out.println("Minutes is " + d.getMinutes());
        System.out.println("Seconds is " + d.getSeconds());
        System.out.println("Year is " + d.getYear());
        System.out.println("Time is " + d.getTime());
        System.out.println("Setting Date...");
        d.setDate(17);
        System.out.println("Date is " + d.getDate());
        System.out.println("Setting Seconds...");
        d.setDate(20);
        System.out.println("Date is " + d.getDate());
     }
}




