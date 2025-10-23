package org.bluebridge.chapter_04_socket._02_nio;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lingwh
 * @desc 阻塞方式实现网络通信-基于 NIO 的 Channel 理解阻塞通信模型（阻塞方式可以与单个客户端完成每一次通信，无法正常的与多个客户端完成每一次通信）
 * @date 2025/6/26 18:01
 */

/**
 * NIO 模型网络通信 Server 端
 *  V1.0 服务端接收多个客户端单条消息发送和接收需求
 *
 * 测试方法
 *  1.cmd -> telnet 127.0.0.1 8080/telnet localhost 8080 -> 直接输入内容（只能发送单个字符）/按下Ctrl+]后输入 send + 内容（可以发送字符串） -> 查看 idea 控制台接收到的信息
 *  2.启动多个客户端
 */
@Slf4j
public class _01_BlockingSocketChannelServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        log.info("阻塞服务器启动，端口：{}......", PORT);
        // 1.创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 2.绑定监听端口
        ssc.bind(new InetSocketAddress(8080));
        // 3.创建连接集合
        List<SocketChannel> channels = new ArrayList<>();
        // 4.创建 ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 4.循环监听
        while (true) {
            // 5.等待与客户端建立连接
            log.info("waiting connecting......");
            // 6.accept 建立与客户端连接，SocketChannel 用来与客户端之间通信
            SocketChannel sc = ssc.accept(); // 阻塞方法，线程停止运行
            log.info("connected......{}", sc);
            channels.add(sc);
            for (SocketChannel channel : channels) {
                // 7.接收客户端发送的数据
                log.info("before read......{}", channel);
                int len = channel.read(buffer);; // 阻塞方法，线程停止运行
                log.info("本次读取到的数据长度：{}", len);
                buffer.flip();
                ByteBufferUtil.debugRead(buffer);
                buffer.clear();
                log.info("after read......{}", channel);
            }
        }
    }

}
