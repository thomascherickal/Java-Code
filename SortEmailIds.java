/////////////////////////////////////////////////////////
//	Sorting e-mail ID database experiment          //
/////////////////////////////////////////////////////////
import java.io.*;
 class SortEmail {
    
    FileReader fr;
    String Ids[];
    static int len;
    
    public SortEmail() throws FileNotFoundException, IOException {
        fr = new FileReader("emailids.dat");
        BufferedReader br = new BufferedReader(fr);
        int i, check = 1;
        Ids = new String[25];
        for(i = 0;  ; i++)
        {
            Ids[i] = br.readLine();
            if (Ids[i] == null)
                break;
        }
        len = i;
	  fr.close();
        
    }
    private void Swap(int i, int j)
    {
        String t;
        t = Ids[i];
        Ids[i] = Ids[j];
        Ids[j] = t;
    }
    private int Partition(int low, int high)
    {
        int i, j, mid = (low + high)/2;
        i = low - 1;
        for (j = low; j < high - 1; j++)
        {
            if (Ids[j].compareTo(Ids[mid]) <= 0  )
            {
                i++;
                Swap(i, j);
            }
            Swap(i + 1, high);
        }
                
        return i + 1;
    }
    public void DotheSorting(int low, int high) {
       
        int mid;
        if (low <= high) {
            mid = Partition(low, high);
            DotheSorting(0, mid - 1);
            DotheSorting(mid + 1, high);
        }
        
    }
    public void OutputFile() throws FileNotFoundException, IOException
    {
        FileWriter fw = new FileWriter("SortedIds.dat");
        int i;
        for (i = 0; i < len; i++)
            fw.write(Ids[i]);
        fw.close();
        
    }
    
}

class SortEmailIds
{
    public static void main(String[] args) throws IOException {
        try {
            SortEmail se = new SortEmail();
            se.DotheSorting(0, se.len - 1);
            se.OutputFile();
        }
        catch(FileNotFoundException fnfe) {
            System.out.println("File not found" + fnfe);
            System.exit(-1);
        }

    }
    
}

