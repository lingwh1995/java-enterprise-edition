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
 * @author lingwh
 * @desc   使用 多线程 + selector 实现Server
 * @date   2025/6/29 9:45
 */

/**
 * V2.0 客户端无法与服务端可以建立连接，多个客户端时从第二个客户端开始无法与服务端通信
 *
 * 多个客户端时从第二个客户端开始无法与服务端通信成功原因分析
 *      // 在boss线程中执行 => 第一个客户端连接时: main() => sc.register(worker.selector, SelectionKey.OP_READ, null); => OP_READ事件注册成功
 *      // 在worker-0线程中执行 => main() => worker.register(); => thread.start();  => selector.select(); => selector阻塞   //会导致selector阻塞(处于阻塞状态时其他通道上的事件无法被注册到这个selector上)
 *      // 在boss线程中执行 => 第二个客户端连接时: main() => sc.register(worker.selector, SelectionKey.OP_READ, null); => OP_READ事件注册失败
 *
 *      第一个客户端连接后会导致selector阻塞(处于阻塞状态时其他通道上的事件无法被注册到这个selector上)，所以从第二个客户端之后的客户端只能触发服务器上的OP_ACCEPT
 *      事件，无法触发OP_READ事件，所以现象是从第二个客户端之后的客户端只能与服务器连接，服务器无法接收到客户端发送的数据，核心在于理解 tag:1 处OP_READ有没有注册到
 *      selector对象上，这里的事件能注册成功，客户端就能正确发送数据给服务器，事件注册失败，客户端无法发送数据给服务器，因为事件没有注册到selector对象上
 *
 */
@Slf4j(topic = "·")
public class _02_MultiThreadServer {

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
                    log.info("connected......{}", sc.getRemoteAddress());
                    // 2.关联worker中的selector
                    log.info("before register......{}", sc.getRemoteAddress());
                    worker.init();
                    sc.register(worker.selector, SelectionKey.OP_READ, null);  //tag:1 // 在boss线程中执行
                    log.info("after register......{}", sc.getRemoteAddress());
                    log.info("thread name......{}", Thread.currentThread().getName());
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
