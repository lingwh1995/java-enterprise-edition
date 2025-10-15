package org.bluebridge.chapter_09_selector;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author lingwh
 * @desc   使用 多线程 + selector 实现Server
 * @date   2025/6/29 10:12
 */

/**
 * V3.0 客户端与服务端可以建立连接，可以正常通信（单个worker版）
 *
 * tag:1 和 tag:2 处代码解决了问题
 *
 * 核心思路：保证 sc.register(selector, SelectionKey.OP_READ, null); 执行之前，selector处于非阻塞状态
 */
@Slf4j
public class _03_MultiThreadServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss, 0, null);
        bossKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(HOST, PORT));
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
                    log.info("connected......{}", sc.getRemoteAddress());
                    // 2.关联worker中的selector
                    log.info("before register......{}", sc.getRemoteAddress());
                    worker.init(sc);
                    log.info("after register......{}", sc.getRemoteAddress());
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
        private ConcurrentLinkedQueue<Runnable> tasks = new ConcurrentLinkedQueue<>();

        public void init(SocketChannel sc) throws IOException {
            if(!start) {
                selector = Selector.open();
                thread = new Thread(this, name);
                thread.start();
                start = true;
            }
            // 向队列中添加任务
            tasks.add(() -> {                                               // tag:1
                try {                                                       // tag:1
                    sc.register(selector, SelectionKey.OP_READ, null);  // tag:1   // 在boss线程中执行)
                } catch (ClosedChannelException e) {                        // tag:1
                    throw new RuntimeException(e);                          // tag:1
                }                                                           // tag:1
            });                                                             // tag:1
            selector.wakeup();                                              // tag:1
            log.info("init() => thread name......{}", Thread.currentThread().getName());
        }

        @Override
        public void run() {
            while (true) {
                try {
                    selector.select();   // 在worker-0线程中执行
                    log.info("run() => thread name......{}", Thread.currentThread().getName());
                    Runnable task = tasks.poll();           // tag:2
                    if(task != null) {                      // tag:2
                        task.run();                         // tag:2
                    }                                       // tag:2
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel sc = (SocketChannel) key.channel();
                            log.info("readed......{}", sc.getRemoteAddress());
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
