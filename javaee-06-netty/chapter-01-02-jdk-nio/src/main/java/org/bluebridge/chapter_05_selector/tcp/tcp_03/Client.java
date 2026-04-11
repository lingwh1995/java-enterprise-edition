package org.bluebridge.chapter_05_selector.tcp.tcp_03;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author lingwh
 * @desc 测试 Selector 对象、 SelectionKey 对象详解的客户端
 * @date 2025/6/28 16:35
 */
@Slf4j
public class Client {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        // 1.创建客户端
        SocketChannel sc = SocketChannel.open();
        // 2.连接服务端
        sc.connect(new InetSocketAddress(HOST, PORT));
        log.info("非阻塞TCP Selector客户端启动......");
        Scanner scanner = new Scanner(System.in);
        // 3.发送消息
        while (true) {
            log.info("请输入消息......");
            String input = scanner.nextLine();
//            ByteBuffer buffer = Charset.defaultCharset().encode(input.concat("\n"));
//            sc.write(buffer);
//            ByteBufferUtil.debugAll(buffer);
            sc.write(ByteBuffer.wrap(input.concat("\n").getBytes()));
            ByteBufferUtil.debugAll(ByteBuffer.wrap(input.getBytes()));
        }
    }

}
