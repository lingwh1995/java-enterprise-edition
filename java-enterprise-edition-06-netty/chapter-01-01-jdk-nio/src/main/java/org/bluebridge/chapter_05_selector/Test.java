package org.bluebridge.chapter_05_selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;

@Slf4j
public class Test {

    public static void main(String[] args) throws IOException {
        // 1.创建selector，管理多个channel
        Selector selector = Selector.open();
        // 2.创建服务器对象
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 3.设置非阻塞
        ssc.configureBlocking(false);
        // 4. 建立selector和channel的联系(注册)
        // SelectionKey就是将来事件发生后，通过它可以知道事件和哪个channel的事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        // 5.key只关注accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key: {}", sscKey);
        // 6.绑定端口号
        ssc.bind(new InetSocketAddress(8080));
        while (true) {
            // 7.select方法，没有事件发生，线程阻塞，有事件，线程才会恢复
                // select在事件未处理时，它不会阻塞，事件发生后要么处理，要么取消，不能置之不理
            selector.select();
            // 8.处理事件，selectedKeys内部包含了所有发生的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                log.debug("key: {}", key);
                ServerSocketChannel channel = (ServerSocketChannel)key.channel();
//                SocketChannel sc = channel.accept();
//                log.debug("sc: {}", sc);
            }
        }
    }

}
