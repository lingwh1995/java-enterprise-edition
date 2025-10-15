package org.bluebridge.chapter_04_socket._01_bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lingwh
 * @desc   阻塞方式实现网络通信-基于BIO的流理解阻塞通信模型
 * @date   2025/7/7 18:01
 */

/**
 * BIO模型网络通信Server端
 *  V2.0 服务端接收多个客户端多条消息发送和接收需求(一个客户端对应一个线程)
 *
 * 测试方法
 *  1.cmd -> telnet 127.0.0.1 8080/telnet localhost 8080 -> 直接输入内容（只能发送单个字符）/按下Ctrl+]后输入 send + 内容（可以发送字符串） -> 查看idea控制台接收到的信息
 *  2.启动多个客户端
 */
@Slf4j
public class _02_BlockingIOServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            // 1.获取ServerSocket
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(HOST, PORT));
            log.info("阻塞服务器启动，端口：{}......", PORT);
            while(true) {
                log.info("服务端同步阻塞，等待客户端连接中......");
                // 2.获取Socket
                Socket socket = serverSocket.accept();
                log.info("客户端成功连接到服务端......");
                // 3.启动多个客户端连接同一个服务端多线程类
                new Thread(new ServerThread(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

/**
 * 用于处理多个客户端连接同一个服务端的多线程类
 */
@Slf4j
class ServerThread implements Runnable{
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        try {
            //3.从Socket中获取输入流
            InputStream socketInputStream = socket.getInputStream();
            //4.获取包装流
            bufferedReader = new BufferedReader(new InputStreamReader(socketInputStream));
            //5.读取数据
            String message = null;
            while ((message = bufferedReader.readLine()) != null) {
                log.info("来自客户端的消息: {}，当前线程: {}", message, Thread.currentThread().getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
