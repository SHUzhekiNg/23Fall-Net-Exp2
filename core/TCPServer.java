package core;
//TCPServer.java
import java.io.*;
import java.net.*;

import java.nio.file.Files;
import java.nio.file.Paths;

class TCPLoginServer {
    public static void main(String argv[]) throws Exception {
        String filePath = "/home/zhenglicheng/Desktop/Net_exp/23Fall-Net-Exp2/core/login.txt";
        String loginInfo = new String(Files.readAllBytes(Paths.get(filePath)));
        String[] lines = loginInfo.split("\n");
        String loginString;
        ServerSocket loginSocket = new ServerSocket(6789);
        boolean flag;
        while (true) {
            // login
            flag = false;
            Socket loginConnectionSocket = loginSocket.accept();
            BufferedReader loginFromClient = new BufferedReader(new InputStreamReader(loginConnectionSocket.getInputStream()));
            DataOutputStream loginMsgToClient = new DataOutputStream(loginConnectionSocket.getOutputStream());
            loginString = loginFromClient.readLine();
            for (String line : lines) {
                if(loginString.equals(line)) {
                    loginMsgToClient.writeBytes("true\n");
                    loginConnectionSocket.close();
                    flag = true;
                }
            }
            if (flag == false) loginMsgToClient.writeBytes("false\n");
        }
    }
}

class TCPFileServer{
    public static void main(String argv[]) throws Exception {
        ServerSocket fileSocket = new ServerSocket(6790);
        while(true){
            // file
            Socket fileConnectionSocket = fileSocket.accept();
            InputStream fileInputStream = fileConnectionSocket.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream("received_file.txt");
            
            byte[] buffer = new byte[1024];
            int bytesRead;
            
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                // System.out.println("1");
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            OutputStream outputStream = fileConnectionSocket.getOutputStream();
            String endMessage = "File transfer complete";
            outputStream.write(endMessage.getBytes());
        }
    }
}