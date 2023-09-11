package core;
//TCPServer.java
import java.io.*;
import java.net.*;

class TCPClientMain {
    public static void main(String argv[]) throws Exception {
        String username = "sds";
        String status;
        Socket clientSocket = new Socket("localhost", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outToServer.writeBytes(username+'\n');
        status = inFromServer.readLine();
        System.out.println("FROM SERVER: " + status);
        clientSocket.close();
        //  return status;
    }
}