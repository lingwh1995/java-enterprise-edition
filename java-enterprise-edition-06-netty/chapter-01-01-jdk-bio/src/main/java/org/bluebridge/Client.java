package org.bluebridge;

/**
 * @author lingwh
 * @desc BIO客户端
 * @date 2025/9/23 11:19
 */
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.*;

@Slf4j(topic = "·")
public class Client {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            // 连接服务器
            Socket socket = new Socket("localhost", PORT);
            log.info("连接到服务器，端口：{}......", PORT);

            // 获取输入输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            String input;
            log.info("请输入消息（输入'q'退出）:");

            // 读取控制台输入（阻塞）
            while ((input = console.readLine()) != null) {
                if ("q".equals(input)) {
                    break;
                }

                // 发送消息到服务器（阻塞）
                writer.println(input);
                // 读取服务器响应（阻塞）
                String response = reader.readLine();
                log.info("服务器响应: {}", response);
            }

            // 关闭资源
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

