package org.bluebridge.chapter_05_selector.udp.udp_01;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

/**
 * @author lingwh
 * @desc
 * @date 2025/11/2 0:29
 */

@Slf4j
public class Client {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        try (final DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            Scanner scanner = new Scanner(System.in);
            log.info("非阻塞UDP Selector客户端启动，IP：{}，端口：{}......", HOST, PORT);
            while (scanner.hasNext()) {
                log.info("请输入消息......");
                String input = scanner.next();
                buffer.put(input.getBytes());
                buffer.flip();
                //ByteBufferUtil.debugAll(buffer);
                channel.send(buffer, new InetSocketAddress(HOST, PORT));
                buffer.clear();
            }
        }
    }

}

