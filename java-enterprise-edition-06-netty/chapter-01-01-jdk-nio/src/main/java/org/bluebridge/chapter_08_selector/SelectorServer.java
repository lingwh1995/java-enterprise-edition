package org.bluebridge.chapter_08_selector;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author lingwh
 * @desc   从事件驱动角度理解 selector 网络通信
 * @date   2025/6/28 17:35
 */
@Slf4j(topic = "·")
public class SelectorServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        // 1.创建服务器对象
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 2.设置服务器对象
        ssc.configureBlocking(false);
        // 3.绑定端口号
        ssc.bind(new InetSocketAddress(PORT));
        // 4.创建Selector对象
        Selector selector = Selector.open();
        // 5.把Channel注册到selector上
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select();
            // 获取所有就绪事件
            Iterator<SelectionKey> eventIterator = selector.selectedKeys().iterator();
            while (eventIterator.hasNext()) {
                SelectionKey event = eventIterator.next();
                eventIterator.remove();
                if(event.isAcceptable()) {
                    // 获取每一个就绪事件
                    ServerSocketChannel acceptEventSSCChannel = (ServerSocketChannel) event.channel();
                    SocketChannel sc = acceptEventSSCChannel.accept();
                    // 设置为非阻塞
                    sc.configureBlocking(false);
                    // 把scChannel注册到Selector中，关注读事件
                    sc.register(selector, SelectionKey.OP_READ);
                }else if(event.isReadable()) {
                    try {
                        // 获取每一个读事件
                        SocketChannel readEventSCChannel = (SocketChannel)event.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        int readLength = readEventSCChannel.read(buffer);
                        if(readLength == -1) {
                            // 客户端调用了socket.close()方法断开了
                            event.cancel();
                        }else {
                            buffer.flip();
                            ByteBufferUtil.debugAll(buffer);
                            log.info("读取到的来自客户端的数据： {}", Charset.defaultCharset().decode(buffer));
                        }
                    }catch (IOException e) {
                        e.printStackTrace();
                        // 因为客户端由于异常关闭断开了，所以要将key取消注册
                        event.cancel();
                    }
                }
            }
        }
    }

}
