import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;


public class input {

	
	public static void main(String[] args) {
		
//		BufferedReader eingabe = new BufferedReader(new InputStreamReader(System.in));
//		try {
//			String str = new String(eingabe.readLine());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		int[] h = {5,0,7,0,2,6,2};
		int n = 7;
		int m = 7;
		int[] r = new int[n];
		
		int j=0;
		int s=0;
		for(int i=0;i<=n-1;++i){
			r[i] = h[j];
			s = (j+1)%m;
			h[j] = ( ( h[j] ^ h[s]) + 13 ) % 835454957;
			j = s;
		}
		
		print(r);
		if(n == 1){
			System.out.println(r[n-1]);
		}else{
			int [][] sparseTable = process2(r, r.length);
			
			int minimum = min(0,n-1, r, sparseTable);
			ArrayList<int[]> interval = new ArrayList<int[]>();
			int area = n*r[minimum];
			
			if(minimum == 0){
				if(r[0] > area){
					area = r[0];
				}
			}else{
				int[] links = new int[2];
				links[0] = 0;
				links[1] = minimum-1;
				interval.add(links);
			}
			
			if(minimum == n-1){
				if(r[minimum] > area){
					area = r[minimum];
				}
			}else{
				int [] rechts = new int[2];
				rechts[0] = minimum+1;
				rechts[1] = n-1;
				interval.add(rechts);
			}
			while(!interval.isEmpty()){
				
				int[] tmp = interval.remove(0);
				int minTmp = min(tmp[0], tmp[1], r, sparseTable);
				
				if(r[minTmp]*(tmp[1]-tmp[0]+1) > area){
					area = r[minTmp]*(tmp[1]-tmp[0]+1); 
				}
				
				int[] linksTmp = new int[2];
				
				if(tmp[0] == minTmp || tmp[0] <= minTmp-1){
					if(r[tmp[0]] > area){
						area = r[tmp[0]];
					}
				}else{
					linksTmp[0] = tmp[0];
					linksTmp[1] = minTmp-1;
					interval.add(linksTmp);
				}
				
				int[] rechtsTmp = new int[2];
				
				if(minTmp == n-1 || minTmp+1 >= tmp[1]){
					if(r[tmp[1]] > area){
						area = r[tmp[1]];
					}
				}else{
				
					rechtsTmp[0] = minTmp+1;
					rechtsTmp[1] = tmp[1];
					interval.add(rechtsTmp);
				}
			}
			System.out.println("MAX: "+area);
		}
	}
	
	public static int min(int i, int j, int[] r, int[][] M){
		
		int k  = (int) Math.floor((Math.log(j - i + 1)/Math.log(2)));
				
		if(r[M[i][k]]   <=	 r[M[(int) (j-Math.pow(2, k) +1)][k]]){
			return M[i][k];
		}else{
			return M[(int) (j-Math.pow(2, k) +1)][k];
		}
		
	}
	
	public static int[][] process2(int[] A, int N)
	  {
	      int i, j;
	      
	      int[][] M = new int[N][(int) Math.ceil(Math.log(N)/Math.log(2))];
	  //initialize M for the intervals with length 1
	      for (i = 0; i < N; i++)
	          M[i][0] = i;
	  //compute values from smaller to bigger intervals
	      for (j = 1; 1 << j <= N; j++)
	          for (i = 0; i + (1 << j) - 1 < N; i++)
	              if (A[M[i][j - 1]] < A[M[i + (1 << (j - 1))][j - 1]])
	                  M[i][j] = M[i][j - 1];
	              else
	                  M[i][j] = M[i + (1 << (j - 1))][j - 1];
	      
	      return M;
	  }  
	
	public static void print(int[]r){
		//print
		//find max in array
		int n = r.length;
		int[] neu = new int[n];
		neu = r.clone();
		Arrays.sort(neu);
		int maxheight = neu[neu.length-1];
		
		for (int i = maxheight; i >= 1; i--) {
			for (int k = 0; k < n; k++) {
				if (r[k] / i > 0) {
					System.out.print("▓|");
				} else {
					System.out.print(" |");
				}
			}
			System.out.println();
		}
		// print end
	}
		
}

