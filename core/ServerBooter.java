package core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerBooter {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 启动登录服务器
        Runnable loginServerTask = () -> {
            try {
                TCPLoginServer.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        // 启动文件服务器
        Runnable fileServerTask = () -> {
            try {
                TCPFileServer.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        executorService.submit(loginServerTask);
        executorService.submit(fileServerTask);
    }
}