package org.bluebridge.chapter_09_selector;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * V5.0 客户端与服务端可以建立连接，可以正常通信
 * 使用 多线程 + selector 实现Server
 *
 * tag:1 处代码解决了问题
 *
 * 核心思路：保证 sc.register(selector, SelectionKey.OP_READ, null); 执行之前，selector处于非阻塞状态
 */
@Slf4j
public class _05_MultiThreadServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss, 0, null);
        bossKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(PORT));
        // 创建固定数量的worker
        Worker worker = new Worker("worker-0");
        while (true) {
            boss.select();
            Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if(key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    log.debug("connected......{}", sc.getRemoteAddress());
                    // 2.关联worker中的selector
                    log.debug("before register......{}", sc.getRemoteAddress());
                    worker.init();
                    sc.register(worker.selector, SelectionKey.OP_READ, null);  // 在boss线程中执行
                    log.debug("after register......{}", sc.getRemoteAddress());
                }
            }
        }
    }

    static class Worker implements Runnable {
        private Thread thread;
        private Selector selector;
        private String name;
        public Worker(String name) {
            this.name = name;
        }
        private volatile boolean start = false;

        public void init() throws IOException {
            if(!start) {
                selector = Selector.open();
                thread = new Thread(this, name);
                thread.start();
                start = true;
            }
            selector.wakeup(); // tag:1
            log.debug("init() => thread name......{}", Thread.currentThread().getName());
        }

        @Override
        public void run() {
            while (true) {
                try {
                    selector.select();   // 在worker-0线程中执行
                    log.debug("run() => thread name......{}", Thread.currentThread().getName());
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel sc = (SocketChannel) key.channel();
                            log.debug("readed......{}", sc.getRemoteAddress());
                            sc.read(buffer);
                            buffer.flip();
                            ByteBufferUtil.debugAll(buffer);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
