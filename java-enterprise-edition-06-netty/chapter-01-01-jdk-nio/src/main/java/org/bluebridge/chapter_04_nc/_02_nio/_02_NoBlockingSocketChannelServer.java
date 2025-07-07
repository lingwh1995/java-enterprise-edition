package org.bluebridge.chapter_04_nc._02_nio;

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
 * @author lingwh
 * @desc   阻塞方式实现网络通信-基于NIO的Channel理解阻塞通信模型
 * @date   2025/7/7 18:01
 */

/**
 * NIO模型网络通信Server端
 * V2.0 服务端接收多个客户端多条消息发送和接收需求
 *
 * 测试方法:
 *  1.cmd -> telnet 127.0.0.1 8080/telnet localhost 8080 ->直接输入内容/按下Ctrl+]后输入 send +内容 ->查看idea控制台接收到的信息
 *  2.启动多个客户端
 */
@Slf4j
public class _02_NoBlockingSocketChannelServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException, InterruptedException {
        log.info("非阻塞服务器启动，端口：{}......", PORT);
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
            //log.info("waiting connecting......");
            // 6.accept建立与客户端连接，SocketChannel用来与客户端之间通信
            SocketChannel sc = ssc.accept(); // 非阻塞，线程还会继续运行，如果没有连接建立，但sc是null
            if(sc != null) {
                log.info("connected......{}", sc);
                sc.configureBlocking(false);
                channels.add(sc);
            }
            for (SocketChannel channel : channels) {
                // 5. 接收客户端发送的数据
                //log.info("before read......{}", channel);
                int len = channel.read(buffer);; // 非阻塞，线程仍然会继续运行，如果没有读到数据，read 返回 0
                if(len > 0) {
                    log.info("本次读取到的数据长度：{}", len);
                    buffer.flip();
                    ByteBufferUtil.debugRead(buffer);
                    buffer.clear();
                    log.info("after read......{}", channel);
                }
            }
            // 睡眠100毫秒，防止CPU占满
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }

}
