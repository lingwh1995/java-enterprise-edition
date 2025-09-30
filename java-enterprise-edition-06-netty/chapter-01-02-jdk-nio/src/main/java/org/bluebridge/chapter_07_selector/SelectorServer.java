package org.bluebridge.chapter_07_selector;


import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lingwh
 * @desc   Selector对象、SelectionKey对象详解
 * @date   2025/6/28 16:32
 */

/**
 * Selector方法详解（总共包含以下10个方法）
 *     1.Selector open(): 创建一个Selector对象
 *     2.boolean isOpen(): 是否是open状态，如果调用了close()方法则会返回false
 *     3.SelectorProvider provider(): 获取当前Selector的Provider
 *     4.Set<SelectionKey> keys(): 获取当前channel注册在Selector上所有的key
 *     5.Set<SelectionKey> selectedKeys(): 获取当前channel就绪的事件列表（特别注意：是就绪事件，是事件，就是说一个selectedKeys代表一个事件）
 *     6.int selectNow(): 获取当前是否有事件就绪，该方法立即返回结果，不会阻塞；如果返回值>0，则代表存在一个或多个
 *     7.int select(long timeout): selectNow的阻塞超时方法，超时时间内，有事件就绪时才会返回；否则超过时间也会返回
 *     8.int select(): selectNow的阻塞方法，直到有事件就绪时才会返回
 *     9.Selector wakeup(): 唤醒阻塞的线程，不管在是select()方法之前调用wakeup()方法，还是在select()方法之后调用wakeup()，都会唤醒阻塞的线程，有点类似与LockSupport.park()和LockSupport.unpark()的感觉
 *     10.void close(): 用完Selector后调用其close()方法会关闭该Selector，且使注册到该Selector上的所有SelectionKey实例无效，channel本身并不会关闭。
 *
 *  SelectionKey
 *      SelectionKey是Java NIO中的一个重要类，用于表示一个通道在Selector上的注册关系。一个SelectionKey对象包含了如下内容：
 *          interest set：当前Channel感兴趣的事件集，即在调用register方法设置的interes set
 *          ready set
 *          channel
 *          selector
 *          attached object：可选的附加对象
 *          interest set
 *      SelectionKey方法详解
 *          1.int interestOps():   返回当前选择键的兴趣集，即通道感兴趣的操作
 *          2.SelectionKey interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE):   设置通道的兴趣集，指定对哪些操作感兴趣
 *          3.int readyOps():  返回通道已经准备好的操作集
 *          4.SelectableChannel channel():  返回当前事件关联的通道，可转换的选项包括:`ServerSocketChannel`和`SocketChannel`
 *          5.Selector selector():  返回当前事件所关联的Selector对象
 *          6.boolean isValid():    检查当前事件是否仍然有效
 *          7.void cancel():   取消选择键，使其无效
 *          8.Object attach(Object ob)/Object attachment():    可以将一个对象附加到选择键上，便于在处理事件时访问相关数据
 *              或者在注册时也可以直接添加   SelectionKey key = channel.register(selector, SelectionKey.OP_READ, theObject);
 *          9.boolean isAcceptable():   服务端连接事件是否就绪
 *          10.boolean isConnectable():   客户端连接事件是否就绪
 *          11.boolean isReadable():   读事件是否就绪
 *          12.boolean isWritable():   写事件是否就绪
 *      SelectionKey注意事项
 *          有效性：在处理完一个 SelectionKey 后，通常需要调用 keyIterator.remove() 来从集合中移除它，以避免重复处理
 *          附加对象：可以通过 attach() 和 attachment() 方法将上下文信息附加到选择键上，方便在事件处理时使用
 *          取消键：当通道不再需要时，可以调用 cancel() 方法取消选择键
 *
 *  Selector和SelectionKey关系
 *      Selector和SelectionKey，两者是紧密关联，配合使用的，如上文所示，往Selector中注册Channel会返回一个SelectionKey对象
 *
 *  SelectionKey的事件类型包括：
 *      OP_READ：可读事件，值为：1<<0                                                            =>  1
 *      OP_WRITE：可写事件，值为：1<<2                                                           =>  4
 *      OP_CONNECT：客户端连接服务端的事件(tcp连接)，一般为创建SocketChannel客户端channel，值为：1<<3  =>  8
 *      OP_ACCEPT：服务端接收客户端连接的事件，一般为创建ServerSocketChannel服务端channel，值为：1<<4  =>  16
 */
@Slf4j
public class SelectorServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        // 1.创建服务器对象
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 2.设置非阻塞
        ssc.configureBlocking(false);
        // 3.创建Selector对象
        Selector selector = Selector.open();

        /*--------------测试selector api--------------*/
        // 测试isOpen(): 判断是否selector对象是否创建
        boolean isOpen = selector.isOpen();
        log.info("isOpen: {}", isOpen);
        // 测试provider(): 获取当前Selector的Provider
        SelectorProvider provider = selector.provider();
        log.info("provider: {}", provider);
        /*--------------测试selector api--------------*/

        /*
         * 4.把服务器对象注册到Selector对象中，第二个参数指定了selector对Channel的什么类型的事件感兴趣，此处为 OP_ACCEPT 事件
         *  注意：一个Channel仅仅可以被注册到一个Selector一次，如果将Channel注册到Selector多次，那么其实就是相当于更新SelectionKey的interest set.
         */
        SelectionKey selectionKey = ssc.register(selector, SelectionKey.OP_ACCEPT);
        log.info("selectionKey: {}", selectionKey);

        /*--------------测试selectionKey api--------------*/
        // interestOps()：返回当前感兴趣的事件列表
        int interestOps = selectionKey.interestOps();
        log.info("selectionKey.interestOps: {}", interestOps);
        // 也可通过interestSet判断其中包含的事件
        boolean isInterestedInAccept  = interestOps == SelectionKey.OP_ACCEPT;
        boolean isInterestedInConnect = interestOps == SelectionKey.OP_CONNECT;
        boolean isInterestedInRead    = interestOps == SelectionKey.OP_READ;
        boolean isInterestedInWrite   = interestOps == SelectionKey.OP_WRITE;
        log.info("isInterestedInAccept: {}, isInterestedInConnect: {}, isInterestedInRead: {}, isInterestedInWrite: {}",
                isInterestedInAccept, isInterestedInConnect, isInterestedInRead, isInterestedInWrite);
        // 也可通过四个方法来分别判断不同事件是否就绪
        isInterestedInAccept  = selectionKey.isAcceptable();  //服务端连接事件是否就绪
        isInterestedInConnect = selectionKey.isConnectable(); //客户端连接事件是否就绪
        isInterestedInRead    = selectionKey.isReadable();    //读事件是否就绪
        isInterestedInWrite   = selectionKey.isWritable();    //写事件是否就绪
        log.info("isInterestedInAccept: {}, isInterestedInConnect: {}, isInterestedInRead: {}, isInterestedInWrite: {}",
                isInterestedInAccept, isInterestedInConnect, isInterestedInRead, isInterestedInWrite);

        // 返回当前事件关联的通道，可转换的选项包括:`ServerSocketChannel`和`SocketChannel`
        Channel selectionKeyBindChannel = selectionKey.channel();
        log.info("before connect with client => selectionKeyBindChannel: {}", selectionKeyBindChannel);
        //返回当前事件所关联的Selector对象
        Selector selectionKeyBindSelector = selectionKey.selector();
        log.info("before connect with client => selectionKeyBindSelector: {}", selectionKeyBindSelector);
        /*--------------测试selectionKey api--------------*/

        /*--------------测试selector api--------------*/
        // 测试keys(): 获取当前channel注册在Selector上所有的key
        Set<SelectionKey> keys = selector.keys();
        log.info("before op_accept => keys: {}", keys);
        // 测试selectedKeys(): 获取当前channel就绪的事件列表
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        log.info("before op_accept => selectionKeys: {}", selectionKeys);
        /*--------------测试selector api--------------*/

        ssc.bind(new InetSocketAddress(PORT));

        /*--------------测试selector api--------------*/
        // 测试wakeup(): 唤醒selector，使selector不再处于阻塞状态
        //selector.wakeup();
        /*--------------测试selector api--------------*/

        while (true) {
            /*--------------测试selector api--------------*/
            // 测试selectNow(): 获取当前是否有事件就绪，该方法立即返回结果，不会阻塞；如果返回值>0，则代表存在一个或多个
            /*
            int selectNowSelected = selector.selectNow();
            log.info("selectNowSelected: {}", selectNowSelected);
             */
            /*--------------测试selector api--------------*/

            // 测试select(): selectNow的阻塞方法，直到有事件就绪时才会返回
            int selectSelected = selector.select();
            log.info("selectSelected: {}", selectSelected);

            /*--------------测试selector api--------------*/
            // 测试keys(): 获取当前channel注册在Selector上所有的key
            keys = selector.keys();
            log.info("after op_accept => keys: {}", keys);
            // 测试selectedKeys(): 获取当前channel就绪的事件列表
            selectionKeys = selector.selectedKeys();
            log.info("after op_accept => selectionKeys: {}", selectionKeys);
            /*--------------测试selector api--------------*/

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                // 获取当前就绪的SelectionKey
                SelectionKey beReadySelectionKey = iterator.next();
                log.info("beReadySelectionKey: {}", beReadySelectionKey);

                /*--------------测试selectionKey api--------------*/
                // 返回当前事件关联的通道，可转换的选项包括:`ServerSocketChannel`和`SocketChannel`
                selectionKeyBindChannel = selectionKey.channel();
                log.info("after connect with client => selectionKeyBindChannel: {}", selectionKeyBindChannel);
                //返回当前事件所关联的Selector对象
                selectionKeyBindSelector = selectionKey.selector();
                log.info("after connect with client => selectionKeyBindSelector: {}", selectionKeyBindSelector);
                /*--------------测试selectionKey api--------------*/

                iterator.remove();
                // 如果是OP_ACCEPT
                if(beReadySelectionKey.isAcceptable()) {
                    // 拿到触发事件的channel
                    ServerSocketChannel channel = (ServerSocketChannel)beReadySelectionKey.channel();
                    // 获取SocketChannel对象
                    SocketChannel sc = channel.accept();
                    // 设置为非阻塞
                    sc.configureBlocking(false);
                    // 把scChannel注册到Selector中，关注读事件
                    sc.register(selector, SelectionKey.OP_READ);
                }else if(beReadySelectionKey.isReadable()) {
                    try {
                        // 拿到触发事件的channel
                        SocketChannel channel = (SocketChannel)beReadySelectionKey.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        int readLength = channel.read(buffer);
                        if(readLength == -1) {
                            // 客户端调用了socket.close()方法断开了
                            beReadySelectionKey.cancel();
                        }else {
                            buffer.flip();
                            ByteBufferUtil.debugAll(buffer);
                            log.info("读取到的来自客户端的数据： {}", Charset.defaultCharset().decode(buffer));
                        }
                    }catch (IOException e) {
                        e.printStackTrace();
                        // 因为客户端由于异常关闭断开了，所以要将key取消注册
                        beReadySelectionKey.cancel();
                    }
                }
            }
        }
    }

}
