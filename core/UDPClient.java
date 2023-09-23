package core;

//UDPClient.java
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UDPClient {
    public static String UDPFileUpload(File uploadFile) throws Exception {
        String serverHost = "localhost";
        int serverPort = 6791;

        DatagramSocket socket = new DatagramSocket();

        try {
            InetAddress serverAddress = InetAddress.getByName(serverHost);

            if (!uploadFile.exists()) {
                socket.close();
                return "File not found";
            }

            byte[] fileData = Files.readAllBytes(Paths.get(uploadFile.getAbsolutePath()));
            int fileSize = fileData.length;
            int chunkSize = 1024;
            int chunks = (int) Math.ceil((double) fileSize / chunkSize);

            for (int i = 0; i < chunks; i++) {
                int offset = i * chunkSize;
                int length = Math.min(chunkSize, fileSize - offset);
                byte[] chunk = new byte[length];
                System.arraycopy(fileData, offset, chunk, 0, length);

                DatagramPacket sendPacket = new DatagramPacket(chunk, length, serverAddress, serverPort);
                socket.send(sendPacket);
            }

            socket.close();
            return "File upload complete";
        } catch (IOException e) {
            e.printStackTrace();
            socket.close();
            return "File upload failed";
        }
    }
}