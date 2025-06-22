package org.bluebridge.chapter_05_selector;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

@Slf4j
public class Client {

    public static void main(String[] args) throws IOException {
        // 1.创建客户端
        SocketChannel sc = SocketChannel.open();
        // 2.连接服务端
        sc.connect(new InetSocketAddress("localhost", 8080));
        Scanner scanner = new Scanner(System.in);
        // 3.发送消息
        while (true) {
            log.debug("请输入消息......");
            String input = scanner.nextLine();
//            ByteBuffer buffer = Charset.defaultCharset().encode(input.concat("\n"));
//            sc.write(buffer);
//            ByteBufferUtil.debugAll(buffer);
            sc.write(ByteBuffer.wrap(input.concat("\n").getBytes()));
            ByteBufferUtil.debugAll(ByteBuffer.wrap(input.getBytes()));
        }

//        sc.write(Charset.defaultCharset().encode("12345678\n56"));
//        sc.write(Charset.defaultCharset().encode("12345678\n56"));
//        sc.write(ByteBuffer.wrap("12345678".getBytes()));
    }

}
