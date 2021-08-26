/////////////////////////////////////////////////////////
//	Stack operations experiment                    //
/////////////////////////////////////////////////////////


import java.io.*;

class Stack
{
    private int Data[];
    private int StackPtr;
    private int No;
    Stack()
    {
        Data = new int[10];
        StackPtr = 0;
        No = 10;
    }
    Stack(int no)
    {
        No = no;
        Data = new int[No];
        StackPtr = 0;
    }
    public void Push(int data)
    {
        if (StackPtr < No)
            Data[StackPtr++] = data;
        else
            System.out.println("Stack overflow");
    }
    public int Pop()
    {
        if (StackPtr > 0)
           return Data[--StackPtr];
        else {
            System.out.println("Stack underflow");
            return -1;
        }
    }
    public void Traverse()
    {
        int iterator;
        for (iterator = StackPtr - 1; iterator >= 0;iterator--)
            System.out.println(Data[iterator]);
    }
    public int Menu() throws IOException
    {
        System.out.println("*******STACK OPERATIONS*******");
        System.out.println("      ");
        System.out.println("1.Push");
        System.out.println("2.Pop ");
        System.out.println("3.View");
        System.out.println("4.Exit");
        DataInputStream d = new DataInputStream(System.in);
        return Integer.parseInt(d.readLine());
    }
    void Choose(int choice) throws IOException
    {
        int data;
        DataInputStream d = new DataInputStream(System.in);
        switch(choice)
        {
        case 1:
            data = Integer.parseInt(d.readLine());
            Push(data);
            break;
        case 2:
            System.out.println(Pop());
            break;
        case 3:
            Traverse();
            break;
        case 4:
            System.exit(0);
            break;
        default:
            break;
        }
   }
}
class stackdemo {
    public static void main(String args[]) throws IOException
    {
        Stack s = new Stack();
        while(true) s.Choose(s.Menu());
    }
}








