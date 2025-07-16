package org.bluebridge.chapter_09_selector;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author lingwh
 * @desc   测试使用 多线程 + selector 实现Server的客户端
 * @date   2025/6/29 9:24
 */
@Slf4j(topic = "·")
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
//            ByteBuffer buffer = Charset.defaultCharset().encode(input.concat("\n"));
//            sc.write(buffer);
//            ByteBufferUtil.debugAll(buffer);
            sc.write(ByteBuffer.wrap(input.concat("\n").getBytes()));
            ByteBufferUtil.debugAll(ByteBuffer.wrap(input.getBytes()));
        }
    }

}
