package org.bluebridge.chapter_04_nc;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 非阻塞方式实现网络通信
 */
@Slf4j
public class _02_NoBlockingSocketChannelServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException, InterruptedException {
        log.debug("非阻塞服务器启动，端口：{}......", PORT);
        // 1.创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false); // 非阻塞模式
        // 2.绑定监听端口
        ssc.bind(new InetSocketAddress(PORT));
        // 3.创建连接集合
        List<SocketChannel> channels = new ArrayList<>();
        // 4.创建ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 4.循环监听
        while (true) {
            // 5.等待与客户端建立连接
            //log.debug("waiting connecting......");
            // 6.accept建立与客户端连接，SocketChannel用来与客户端之间通信
            SocketChannel sc = ssc.accept(); // 非阻塞，线程还会继续运行，如果没有连接建立，但sc是null
            if(sc != null) {
                log.debug("connected......{}", sc);
                sc.configureBlocking(false);
                channels.add(sc);
            }
            for (SocketChannel channel : channels) {
                // 5. 接收客户端发送的数据
                //log.debug("before read......{}", channel);
                int len = channel.read(buffer);; // 非阻塞，线程仍然会继续运行，如果没有读到数据，read 返回 0
                if(len > 0) {
                    log.debug("本次读取到的数据长度：{}", len);
                    buffer.flip();
                    ByteBufferUtil.debugRead(buffer);
                    buffer.clear();
                    log.debug("after read......{}", channel);
                }
            }
            // 睡眠100毫秒，防止CPU占满
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }

}
