package org.bluebridge.chapter_05_selector.udp.udp_01;

/**
 * @author lingwh
 * @desc
 * @date 2025/11/2 0:28
 */
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * UDP server
 *
 * @author jimo
 * @version 1.0.0
 * @date 2020/7/12 11:42
 */
@Slf4j
public class SelectorServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress(HOST, PORT));
        log.info("非阻塞UDP Selector服务器启动，IP：{}，端口：{}......", HOST, PORT);

        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_READ);

        // 通过选择器，查询IO事件
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // 可读事件，有数据到来
                if (selectionKey.isReadable()) {
                    final SocketAddress client = channel.receive(buffer);
                    buffer.flip();
                    ByteBufferUtil.debugAll(buffer);
                    log.info("收到客户端消息：{}", new String(buffer.array(), 0, buffer.limit()));
                }
            }
            iterator.remove();
        }
        selector.close();
        channel.close();
    }

}
