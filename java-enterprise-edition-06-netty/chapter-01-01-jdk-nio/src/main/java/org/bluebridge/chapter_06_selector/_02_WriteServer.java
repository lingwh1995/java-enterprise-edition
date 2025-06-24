package org.bluebridge.chapter_06_selector;


import lombok.extern.slf4j.Slf4j;

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
 * 解决了写大量数据阻塞问题
 */
@Slf4j
public class _02_WriteServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        SelectionKey scKey = ssc.register(selector, 0, null);
        scKey.interestOps(SelectionKey.OP_READ);

        ssc.bind(new InetSocketAddress(8080));

        while (true) {
            // 阻塞直到绑定事件发生
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if(key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking( false);

                    // 向客户端发送大量数据
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 30000000; i++) {
                        sb.append("a");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());
                    // 2.判断是否有剩余
                    if (buffer.hasRemaining()) {
                        // 3.关注可写事件
                        //scKey.interestOps(scKey.interestOps() + SelectionKey.OP_WRITE);
                        scKey.interestOps(scKey.interestOps() | SelectionKey.OP_WRITE);

                        // 4.把未写完的数据以附件形式挂在 scKey
                        scKey.attach( buffer);
                    }
                }else if(key.isWritable()) {
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel sc = (SocketChannel) key.channel();
                    // 5.返回值代表实际写入的字节数
                    int write = sc.write(buffer);
                    log.debug("write: {}", write);
                    // 6.清理操作
                    if(!buffer.hasRemaining()) {
                        key.attach(null);
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);
                    }
                }
            }
        }
    }
}
