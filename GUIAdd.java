package check;

import javax.swing.JOptionPane;

public class GUIAdd {
    public static void main(String args[])
    {
        int no1, no2, sum;
        no1 = Integer.parseInt(JOptionPane.showInputDialog("Enter integer one"));
        no2 = Integer.parseInt(JOptionPane.showInputDialog("Enter integer two"));
        sum = no1 + no2;
        JOptionPane.showMessageDialog(null, "The sum is " + sum, "Results", JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
    }
}

