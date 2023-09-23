package core;

//UDPServer.java
import java.io.*;
import java.net.*;

class UDPFileServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(6791);
        int cnt = 1;
        byte[] receiveData = new byte[1024];

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            byte[] fileData = receivePacket.getData();

            String fileName = "received_file_udp" + String.valueOf(cnt) + ".txt";
            cnt++;

            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            fileOutputStream.write(fileData, 0, receivePacket.getLength());
            fileOutputStream.close();

            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            String endMessage = "File transfer complete";
            byte[] endData = endMessage.getBytes();
            DatagramPacket endPacket = new DatagramPacket(endData, endData.length, clientAddress, clientPort);
            socket.send(endPacket);
        }
    }
}