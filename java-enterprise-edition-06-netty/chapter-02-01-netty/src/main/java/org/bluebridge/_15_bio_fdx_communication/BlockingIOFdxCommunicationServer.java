package org.bluebridge._15_bio_fdx_communication;

/**
 * @author lingwh
 * @desc 基于Java的BIO+多线程实现全双工通信 服务端
 * @date 2025/10/10 10:42
 */

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Java的BIO和全双工通信
 *    1.理论基础与实现方案
 *        网络层面：BIO 基于 TCP 协议进行通信，而 TCP 本身是全双工的协议，允许通信双方在同一时间双向传输数据。
 *        实现层面：在 BIO 模型中，虽然底层 TCP 支持全双工，但默认的 Socket 通信实现方式可能会限制全双工特性的发挥。
 *           *如果使用单线程处理读写操作，由于 I/O 操作是阻塞的，读操作会阻塞写操作，反之亦然，无法真正同时进行双向通信
 *           *要实现真正的全双工通信，需要为每个 Socket 创建两个线程：一个负责读取数据，另一个负责写入数据，通过多线程配合，才能在 BIO 模型下实现全双工通信
 *    2.总结
 *        BIO 通信理论上支持全双工（因为基于全双工的 TCP 协议），但默认单线程实现不具备全双工能力，需要通过多线程编程才能实现真正的全双工通信。
 */
@Slf4j
public class BlockingIOFdxCommunicationServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(HOST, PORT));

        Socket s = ss.accept();
        new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                while (true) {
                    log.info("接收到的数据: {}", reader.readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                // 例如在这个位置加入 thread 级别断点，可以发现即使不写入数据，也不妨碍前面线程读取客户端数据
                for (int i = 0; i < 100; i++) {
                    writer.write("我是来自服务端的消息: " + i);
                    writer.newLine();
                    writer.flush();
                    TimeUnit.MILLISECONDS.sleep(1000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

}
