package org.bluebridge.chapter_05_selector.tcp.tcp_05;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author lingwh
 * @desc 使用 多线程 + selector 实现Server（单个worker版）
 * @date 2025/6/29 9:24
 */

/**
 * V1.0 客户端无法与服务端可以建立连接，无法正常通信
 *
 * 客户端无法与服务端通信成功原因分析
 *      // 在 worker-0 线程中执行 => main() => worker.register(); => thread.start(); => selector.select(); => selector 阻塞  //会导致 selector 阻塞(处于阻塞状态时其他通道上的事件无法被注册到这个 selector 上)
 *      // 在 boss 线程中执行 => main() => sc.register(worker.selector, SelectionKey.OP_READ, null); => OP_READ事件注册失败
 *      上面 selector 和下面 worker.selector 是同一个对象，上面的方法会导致 selector 阻塞(处于阻塞状态时其他通道上的事件无法被注册到这个 selector 上)，所以下面的方法无法把Channel注册到同一个 selector 中
 */
@Slf4j
public class _01_MultiThreadServer {

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
        log.info("非阻塞TCP Selector服务器启动，IP：{}，端口：{}......", HOST, PORT);

        // 创建固定数量的 worker
        Worker worker = new Worker("worker-0");
        worker.init();
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
                    SelectionKey selectionKey = sc.register(worker.selector, SelectionKey.OP_READ, null); // 在boss线程中执行
                    log.info("selectionKey: {}", selectionKey);
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

        public void init() throws IOException {
            if(!start) {
                selector = Selector.open();
                thread = new Thread(this, name);
                thread.start();
                start = true;
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    log.info("thread name......{}", Thread.currentThread().getName());
                    selector.select(); // 在worker-0 线程中执行
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
