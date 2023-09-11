package core;
//TCPServer.java
import java.io.*;
import java.net.*;

import java.nio.file.Files;
import java.nio.file.Paths;

class TCPServer {

    public static void main(String argv[]) throws Exception {
        String clientSentence;
        String status;
        ServerSocket welcomeSocket = new ServerSocket(6789);

        try {
            String filePath = "/home/zhenglicheng/Desktop/Net_exp/23Fall-Net-Exp2/core/login.txt";
            String loginInfo = new String(Files.readAllBytes(Paths.get(filePath)));
            // System.out.println(loginInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = inFromClient.readLine();

            if(clientSentence.startsWith("a")) {
                status = "true";
            } else {
                status = "false";
            }
            outToClient.writeBytes(status + "\n");
        }
    }
}