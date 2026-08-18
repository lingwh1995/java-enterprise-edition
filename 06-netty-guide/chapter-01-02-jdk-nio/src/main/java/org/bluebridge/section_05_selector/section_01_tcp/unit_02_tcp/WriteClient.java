package org.bluebridge.section_05_selector.section_01_tcp.unit_02_tcp;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 写客户端
 *
 * @author lingwh
 * @date 2025/6/26 9:06
 */
@Slf4j
public class WriteClient {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", PORT));
        int count = 0;
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            count += sc.read(buffer);
            log.info("count: {}", count);
            buffer.clear();
        }
    }
}
