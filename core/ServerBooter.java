package core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerBooter {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Runnable loginServerTask = () -> {
            try {
                TCPLoginServer.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        
        Runnable TCPFileServerTask = () -> {
            try {
                TCPFileServer.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Runnable UDPFileServerTask = () -> {
            try {
                UDPFileServer.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        executorService.submit(loginServerTask);
        executorService.submit(TCPFileServerTask);
        executorService.submit(UDPFileServerTask);
    }
}
