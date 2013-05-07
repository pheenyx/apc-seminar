import java.io.BufferedReader;
import java.io.InputStreamReader;

public class EasyExample {

	/**
	 * @brief Dieses Beispiel zweigt wie man Einlesen/Schreiben 
	 *        vom/zum stdin/stdout organisieren kann. Aufgabe: Quadriere die eingegebene Zahl  
	 * @param args
	 */
	
	public static void main(String[] args)throws Exception{
		// TODO Auto-generated method stub
        new EasyExample().run();
	}
	
	void run() throws Exception{
		BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
		while(true){
			String line=reader.readLine();
			int zahl=Integer.parseInt(line);
			if(zahl==0) break;
			System.out.println(zahl*zahl);
		}
		reader.close();
	}
	
	
	

}
