package core;
//TCPServer.java
import java.io.*;
import java.net.*;

public class TCPClient {
    public static String TCPLogin(String username, String password) throws Exception {
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
    public static String TCPFileUpload(File uploadFile) throws Exception {
        try {
            Socket socket = new Socket("localhost", 6790);
            OutputStream outputStream = socket.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream(uploadFile);
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            fileInputStream.close();
            socket.close();

            System.out.println("文件发送完成");
            return "true";
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
    }
}