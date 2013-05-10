import java.io.*;
import java.net.*;
public class Server {
    public static void main(String [] args) throws IOException{
        String clientSentence = "";
        String capitalizedSentence = "";
        //Create a welcoming socket at port 6789
        ServerSocket welcomeSocket=new ServerSocket(8081);
        //Wait on welcoming socket for contact by client
        while(true)
        {
            Socket connectionSocket=welcomeSocket.accept();
            //Create input stream attached to socket
            BufferedReader inFromClient=new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            //Create output stream attached to socket
            DataOutputStream outToClient=new DataOutputStream(connectionSocket.getOutputStream());
            //Read in line from socket
            while(true){
                clientSentence=inFromClient.readLine();
                System.out.println(clientSentence);
                if (clientSentence.startsWith("END")){
                    break;
                }
                //System.out.println(clientSentence);
            }
            capitalizedSentence=clientSentence.toUpperCase()+"\n";
            //Write out line to socket
            outToClient.writeBytes(capitalizedSentence);
        }//End of the while loop, loop back and wait for another client connection
    }
}
