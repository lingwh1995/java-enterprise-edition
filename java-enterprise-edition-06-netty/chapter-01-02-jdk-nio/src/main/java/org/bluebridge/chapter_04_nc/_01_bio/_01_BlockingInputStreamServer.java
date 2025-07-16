package org.bluebridge.chapter_04_nc._01_bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lingwh
 * @desc   阻塞方式实现网络通信-基于BIO的流理解阻塞通信模型
 * @date   2025/7/7 18:01
 */

/**
 * BIO模型网络通信Server端
 * V1.0 服务端接收单个客户端多条消息发送和接收需求
 *
 * 测试方法:
 *  1.cmd -> telnet 127.0.0.1 8080/telnet localhost 8080 ->直接输入内容/按下Ctrl+]后输入 send +内容 ->查看idea控制台接收到的信息
 *  2.启动多个客户端
 */
@Slf4j(topic = "·")
public class _01_BlockingInputStreamServer {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        BufferedReader bufferedReader = null;
        try {
            log.info("阻塞服务器启动，端口：{}......", PORT);
            // 1.创建ServerSocket
            ServerSocket serverSocket = new ServerSocket(PORT);
            // 2.获取Socket
            Socket socket = serverSocket.accept();
            // 3.获取输入流
            InputStream socketInputStream = socket.getInputStream();
            // 4.获取包装后的输入流
            bufferedReader = new BufferedReader(new InputStreamReader(socketInputStream));
            // 5.读取数据
            String message = null;
            while((message = bufferedReader.readLine()) != null) {
                log.info("来自客户端的消息: {}", message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 使用装饰流时，只需要关闭最后的装饰流即可
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
