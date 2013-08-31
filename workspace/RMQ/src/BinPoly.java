import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BinPoly {

    /**
     * the main function.
     * @param args commandline args
     */
    public static void main(final String[] args) {

        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(System.in));
        try {
            while (true) {
                String line = reader.readLine();

                if (line.startsWith("END")) {
                    break;
                }

                // first line n 
                int n = Integer.parseInt(line);
                
                // second line h
                line = reader.readLine();
                String[] stra = line.split(" ");
                int[] a = new int[n+1];
                for (int i = 0; i < n; i++) {
                    a[i] = Integer.parseInt(stra[i]);
                }
                generate(a);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public static void generate(int[] a){
        int rootCounter = 0;
        int sum = 0;
        if(a[0] == 0) rootCounter++;
        for (int i = 0; i < a.length; ++i){
            sum += a[i];
        }
        if (sum % 2 == 0) rootCounter ++;
        System.out.println(rootCounter);
    }
}