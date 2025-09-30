package org.bluebridge;

/**
 * @author lingwh
 * @desc BIO服务端
 * @date 2025/9/23 11:19
 */
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.*;

@Slf4j
public class BlockingInputOutputServer {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            // 创建ServerSocket，监听8080端口
            ServerSocket serverSocket = new ServerSocket(PORT);
            log.info("服务器启动，端口：{}......", PORT);

            while (true) {
                // 接受客户端连接（阻塞）
                Socket socket = serverSocket.accept();
                log.info("客户端连接: {}", socket.getInetAddress());

                // 为每个客户端创建一个线程处理
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 客户端处理类
    static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
            ) {
                String line;
                // 读取数据（阻塞）
                while ((line = reader.readLine()) != null) {
                    System.out.println("收到客户端消息: " + line);
                    // 发送数据（阻塞）
                    writer.println("服务器回复: " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

