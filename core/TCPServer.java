package core;
//TCPServer.java
import java.io.*;
import java.net.*;

import java.nio.file.Files;
import java.nio.file.Paths;

class TCPServer {
    public static void main(String argv[]) throws Exception {
        String filePath = "/home/zhenglicheng/Desktop/Net_exp/23Fall-Net-Exp2/core/login.txt";
        String loginInfo = new String(Files.readAllBytes(Paths.get(filePath)));
        String[] lines = loginInfo.split("\n");
        String clientSentence;
        ServerSocket welcomeSocket = new ServerSocket(6789);
        boolean flag;

        while (true) {
            flag = false;
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = inFromClient.readLine();
            
            for (String line : lines) {
                if(clientSentence.equals(line)) {
                    outToClient.writeBytes("true\n");
                    flag = true;
                }
            }
            if (flag == false) outToClient.writeBytes("false\n");
            
        }
    }
}