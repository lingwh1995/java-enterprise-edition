package org.bluebridge.chapter_05_selector.tcp.tcp_01;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author lingwh
 * @desc 使用 Selector 实现 Server
 * @date 2025/6/27 9:06
 */

/**
 * Selector（IO多路复用模型）网络通信 Server 端
 *  实现了 IO 多路复用，没有处理粘包半包问题
 *
 * 测试方法
 *  1.cmd -> telnet 127.0.0.1 8080/telnet localhost 8080 -> 直接输入内容（只能发送单个字符）/按下Ctrl+]后输入 send + 内容（可以发送字符串） -> 查看idea控制台接收到的信息
 *  2.启动多个客户端
 *
 * 测试结论
 *  1.单线程可以配合 Selector 完成对多个 Channel 可读写事件的监控，这称之为多路复用
 *  2.多路复用仅针对网络 IO、普通文件IO没法利用多路复用
 *  3.如果不用 Selector 的非阻塞模式，线程大部分时间都在做无用功，而 Selector 能够保证
 *      有可连接事件时才去连接
 *      有可读事件才去读取
 *      有可写事件才去写入
 *  4.限于网络传输能力，Channel 未必时时可写，一旦 Channel 可写，会触发 Selector 的可写事件
 */
@Slf4j
public class _01_SelectorServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        // 1.创建服务器对象
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 2.设置非阻塞
        ssc.configureBlocking(false);
        // 3.创建 selector，管理多个 channel
        Selector selector = Selector.open();
        // 4.建立 selector 和 channel 的联系(注册)
        // 把 Channel 注册到 Selector 上写法一
        /*
        // SelectionKey 就是将来事件发生后，通过它可以知道事件和哪个 channel 的事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        // 5.key 只关注 accept 事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.info("register key: {}", sscKey);
        */

        // 把 Channel 注册到 Selector 上写法二
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        // 6.绑定端口号
        ssc.bind(new InetSocketAddress(HOST, PORT));
        log.info("非阻塞TCP Selector服务器启动，IP：{}，端口：{}......", HOST, PORT);
        while (true) {
            // 7.select 方法，没有事件发生，线程阻塞，有事件，线程才会恢复
            // select 在事件未处理时，它不会阻塞，事件发生后要么处理，要么取消，不能置之不理
            selector.select();
            // 8.处理事件，selectedKeys 内部包含了所有发生的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                log.info("key: {}", key);
                // 处理完 key 时，要从 selectKeys 集合中删除，否则下次处理就会有问题
                iterator.remove();
                // 9.区分事件类型
                if (key.isAcceptable()) { // 如果是 accept 事件
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel(); // 拿到触发事件的channel
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.info("sc: {}", sc);
//                    log.info("scKey: {}", scKey);
                } else if (key.isReadable()) { // 如果是读事件
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        int read = channel.read(buffer);
                        if (read == -1) {
                            // 客户端调用了socket.close()方法断开了
                            key.cancel();
                        } else {
                            buffer.flip();
                            ByteBufferUtil.debugAll(buffer);
                            log.info("读取到的来自客户端的数据： {}", Charset.defaultCharset().decode(buffer));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        // 因为客户端由于异常关闭断开了，所以要将key取消注册
                        key.cancel();
                    }
                }
            }
        }
    }

}
