import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws UnknownHostException,
            IOException {
        String sentence;
        String modifiedSentence;
        // Create a standard input stream
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
                System.in));
        // Create a client socket and connect to the server
        Socket clientSocket = new Socket("powerno.de", 8081);
        // Create output Stream attached to socket
        // DataOutputStream outToServer=new
        // DataOutputStream(clientSocket.getOutputStream());

        // DataOutputStream outToServer=new
        // DataOutputStream(clientSocket.getOutputStream());
        Writer outToServer = new OutputStreamWriter(
                clientSocket.getOutputStream());

        // Create input stream and attached it to socket
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream()));

        while (true) {
            // Read text from user
            sentence = inFromUser.readLine();
            // Send line to server
            // outToServer.writeBytes(sentence);
            outToServer.write(sentence + "\n");
            outToServer.flush(); // to ensure the data is sent
            if (sentence.startsWith("END")){
                break;
            }
        }

        // Read a modified sentence from the user
        modifiedSentence = inFromServer.readLine();
        System.out.println("Last Message from Server: " + modifiedSentence);
        clientSocket.close();

    }

}