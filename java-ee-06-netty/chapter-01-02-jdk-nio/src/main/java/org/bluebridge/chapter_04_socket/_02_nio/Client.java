package org.bluebridge.chapter_04_socket._02_nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author lingwh
 * @desc 测试阻塞方式实现网络通信-基于 NIO 的 Channel 理解阻塞通信模型的客户端
 * @date 2025/6/26 18:01
 */
@Slf4j
public class Client {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        // 1.创建客户端
        SocketChannel sc = SocketChannel.open();
        // 2.连接服务端
        sc.connect(new InetSocketAddress("localhost", PORT));
        log.info("客户端启动......");
        Scanner scanner = new Scanner(System.in);
        // 3.发送消息
        while (true) {
            log.info("请输入消息......");
            String input = scanner.nextLine();
            sc.write(ByteBuffer.wrap(input.getBytes()));
        }
    }

}
