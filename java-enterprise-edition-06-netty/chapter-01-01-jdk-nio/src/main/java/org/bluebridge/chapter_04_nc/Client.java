package org.bluebridge.chapter_04_nc;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

@Slf4j
public class Client {
    private static final int PORT = 8080;
    public static void main(String[] args) throws IOException {
        // 1.创建客户端
        SocketChannel sc = SocketChannel.open();
        // 2.连接服务端
        sc.connect(new InetSocketAddress("localhost", PORT));
        Scanner scanner = new Scanner(System.in);
        // 3.发送消息
        while (true) {
            log.debug("请输入消息......");
            String input = scanner.nextLine();
            sc.write(ByteBuffer.wrap(input.getBytes()));
        }
    }

}
