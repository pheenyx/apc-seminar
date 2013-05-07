import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class input {

	
	public static void main(String[] args) {
		
//		BufferedReader eingabe = new BufferedReader(new InputStreamReader(System.in));
//		try {
//			String str = new String(eingabe.readLine());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		int[] h = {3,6,5,6,2,4};
		int n = 6;
		int m = 6;
		int[] r = new int[n];
		
		int j=0;
		int s=0;
		for(int i=0;i<n-1;++i){
			r[i] = h[j];
			s = (j+1)%m;
			h[j] = ( ( h[j] ^ h[s]) + 13 ) % 835454957;
			j = s;
		}
		System.out.println(Arrays.toString(r));
	}

}
