package org.bluebridge.chapter_05_selector;

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
 * @desc   使用selector实现Server
 * @date   2025/6/27 9:06
 */
@Slf4j(topic = "·")
public class _01_SelectorServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        log.info("非阻塞Selector服务器启动，端口：{}......", PORT);
        // 1.创建服务器对象
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 2.设置非阻塞
        ssc.configureBlocking(false);
        // 3.创建selector，管理多个channel
        Selector selector = Selector.open();
        // 4. 建立selector和channel的联系(注册)
        // 把Channel注册到Selector上写法一
        /*
        // SelectionKey就是将来事件发生后，通过它可以知道事件和哪个channel的事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        // 5.key只关注accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.info("register key: {}", sscKey);
        */

        // 把Channel注册到Selector上写法二
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        // 6.绑定端口号
        ssc.bind(new InetSocketAddress(PORT));
        while (true) {
            // 7.select方法，没有事件发生，线程阻塞，有事件，线程才会恢复
            // select在事件未处理时，它不会阻塞，事件发生后要么处理，要么取消，不能置之不理
            selector.select();
            // 8.处理事件，selectedKeys内部包含了所有发生的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                log.info("key: {}", key);
                // 处理完key时，要从 selectKeys 集合中删除，否则下次处理就会有问题
                iterator.remove();
                // 9.区分事件类型
                if (key.isAcceptable()) { // 如果是accept事件
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
