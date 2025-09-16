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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author lingwh
 * @desc   使用 多线程 + selector 实现Server
 * @date   2025/6/29 15:43
 */

/**
 * V6.0 客户端与服务端可以建立连接，可以正常通信（多个worker版）
 *
 * tag:1 处代码解决了问题
 *
 * 核心思路：保证 sc.register(selector, SelectionKey.OP_READ, null); 执行之前，selector处于非阻塞状态
 *
 * worker数量建议设置为cpu核心数
 *      使用 Runtime.getRuntime().availableProcessors() 获取cpu核心数，docker下获取的是物理机核心数，而非docker容器核心数，所以手工指定最好
 */
@Slf4j(topic = "·")
public class _06_MultiThreadServer {

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
        //Worker[] workers = new Worker[Runtime.getRuntime().availableProcessors()];  // 获取cpu核心数
        Worker[] workers = new Worker[3];
        IntStream.range(0, 3).forEach(i -> {
            workers[i] = new Worker("worker-" + i);
        });
        AtomicInteger index = new AtomicInteger();
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
                    workers[index.getAndIncrement() % workers.length].init(sc);  // boss线程调用，初始化selector，启动worker
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

        public void init(SocketChannel sc) throws IOException {
            if(!start) {
                selector = Selector.open();
                thread = new Thread(this, name);
                thread.start();
                start = true;
            }
            selector.wakeup();    //boss线程中执行  // tag:1
            sc.register(selector, SelectionKey.OP_READ, null);  //boss线程中执行 // tag:1
            log.info("init() => thread name......{}", Thread.currentThread().getName());
        }

        @Override
        public void run() {
            while (true) {
                try {
                    selector.select();   // 在worker-0线程中执行
                    log.info("run() => thread name......{}", Thread.currentThread().getName());
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
