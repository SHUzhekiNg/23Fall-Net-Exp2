package core;
//TCPServer.java
import java.io.*;
import java.net.*;

public class TCPClient {
    public static String TCPSent(String username, String password) throws Exception {
        String status;
        Socket clientSocket = new Socket("localhost", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outToServer.writeBytes(username + " " + password + "\n");
        status = inFromServer.readLine();
        System.out.println("FROM SERVER: " + status);
        clientSocket.close();
        return status;
    }
}