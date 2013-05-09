import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;


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
		for(int i=0;i<=n-1;++i){
			r[i] = h[j];
			s = (j+1)%m;
			h[j] = ( ( h[j] ^ h[s]) + 13 ) % 835454957;
			j = s;
		}
		int [][] test = process2(r, r.length);
//		System.out.println(Arrays.toString(r));
//		for(int i=0;i<test.length;++i){
//		System.out.println(Arrays.toString(test[i]));
//		}
//		
//		System.out.println(min(0,n-1,r,test));
		
		int minimum = min(0,n-1, r, test);
		ArrayList<int[]> interval = new ArrayList<int[]>();
		int area = n*minimum;
		
		int[] links = new int[2];
		links[0] = 0;
		links[1] = minimum-1;
		
		int [] rechts = new int[2];
		rechts[0] = minimum+1;
		rechts[1] = n;

		if(links[0] == links[1]){
			if(links[0] > area){
				area = links[0]; 
			}
		}else {
			interval.add(links);
		}
		
		if(rechts[0] == rechts[1]){
			if(rechts[0] > area){
				area = rechts[0]; 
			}
		}else{
			interval.add(rechts);
		}
				
		while(!interval.isEmpty()){
			
			int[] tmp = interval.remove(0);
			int minTmp = min(tmp[0], tmp[1], r, test);
			if(minTmp*(tmp[1]-tmp[0]) > area){
				area = minTmp*(tmp[1]-tmp[0]); 
			}
			
			int[] linksTmp = new int[2];
			linksTmp[0] = tmp[0];
			linksTmp[1] = minTmp-1;
			
			int[] rechtsTmp = new int[2];
			rechtsTmp[0] = minTmp+1;
			rechtsTmp[1] = tmp[1];
			
			if(linksTmp[0] == linksTmp[1]){
				if(linksTmp[0] > area){
					area = linksTmp[0]; 
				}
			}else {
				interval.add(linksTmp);
			}
			
			if(rechtsTmp[0] == rechtsTmp[1]){
				if(rechtsTmp[0] > area){
					area = rechtsTmp[0]; 
				}
			}else{
				interval.add(rechtsTmp);
			}
		}
		System.out.println("MAX: "+area);
	}
	
	public static int min(int i, int j, int[] r, int[][] M){
		
		int k  = (int) Math.floor((Math.log(j - i + 1)/Math.log(2)));
		System.out.println("k="+k+"|i="+i+"|j="+j);
		System.out.println(r[M[i][k]]);
		
		
		
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
		
}

