package org.bluebridge.chapter_04_socket._01_bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author lingwh
 * @desc 测试阻塞方式实现网络通信-基于BIO的流理解阻塞通信模型的客户端
 * @date 2025/7/7 18:01
 */

/**
 * BIO模型网络通信Client端
 * 目标: 服务端接收单个客户端单条消息发送和接收需求
 */
@Slf4j
public class Client {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        OutputStream socketOutputStream = null;
        PrintStream printStream = null;
        try {
            log.info("客户端启动......");
            // 1.获取Socket对象，从Socket对象中可以获得与服务端的连接
            Socket socket = new Socket("localhost", PORT);
            // 2.获取输出流
            socketOutputStream = socket.getOutputStream();
            // 3.根据输出流获取打印流
            printStream = new PrintStream(socketOutputStream);
            // 4.使用打印流打印一行数据
            Scanner scanner = new Scanner(System.in);
            while(true) {
                String line = scanner.nextLine();
                printStream.println(line);
                // 5.把数据从内存中写出，一般用在close()方法之前
                printStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socketOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            printStream.close();
        }
    }

}
