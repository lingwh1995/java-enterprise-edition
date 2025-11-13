package org.bluebridge.chapter_06_groupchat.demo_01;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 目标：服务端群聊系统实现
 */
@Slf4j
public class GroupChatServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    private Selector selector;
    private ServerSocketChannel ssChannel;

    public GroupChatServer() {
        try {
            // 1.创建选择器
            selector = Selector.open();
            // 2.获取通道
            ssChannel = ServerSocketChannel.open();
            // 3.切换为非阻塞模式
            ssChannel.configureBlocking(false);
            // 4.绑定连接的端口
            ssChannel.bind(new InetSocketAddress(PORT));
            // 5.将通道都注册到选择器上去，并且开始指定监听接收事件
            ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 创建服务端对象
        GroupChatServer server = new GroupChatServer();
        // 开始监听客户端的各种消息事件：连接、群聊消息、离线消息
        server.listen();
    }

    public void listen() {
        //System.out.println("监听线程：" + Thread.currentThread().getName());
        try {
            while (selector.select() > 0){
                // 获取选择器中所有注册通道的就绪事件
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                // 开始遍历这个事件
                while (it.hasNext()){
                // 提取这个事件
                    SelectionKey sk = it.next();
                    // 判断这个事件
                    if(sk.isAcceptable()) {
                        // 客户端接入请求
                        // 获取当前客户端通道
                        SocketChannel schannel = ssChannel.accept();
                        // 注册成非阻塞模式
                        schannel.configureBlocking(false);
                        // 注册给选择器，监听读数据的事件
                        schannel.register(selector,SelectionKey.OP_READ);
                    }else if(sk.isReadable()){
                        // 处理这个客户端的消息，接收它，然后实现转发逻辑
                        readClientData(sk);
                    }
                    //处理完毕之后，需要移除当前事件
                    it.remove();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 接收当前客户端的信息，转发给其他全部客户端通道
     * @param sk
     */
    private void readClientData(SelectionKey sk) {
        SocketChannel channel = null;
        try {
            // 直接得到当前客户端通道
            channel = (SocketChannel) sk.channel();
            // 创建缓存区对象，开始接收客户端通道的数据
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            if(count > 0) {
                buffer.flip();
                // 提取读取到的信息
                String msg = new String(buffer.array(),0,buffer.remaining());
                log.info("接收到了客户端的消息：{}", msg);
                // 把这个消息推送给全部客户端接收
                sendMsgToAllClient(msg, channel);
            }
        }catch (Exception e){
            try {
                log.info("有人离线了： {}", channel.getRemoteAddress());
                //当前客户端离线
                sk.cancel();//取消注册
                channel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 把当前客户端的消息推送给当前全部在线注册的channel
     * @param msg
     * @param sChannel
     * @throws IOException
     */
    private void sendMsgToAllClient(String msg, SocketChannel sChannel) throws IOException {
        log.info("服务端开始转发这个消息： {}，当前处理的线程： {}", msg, Thread.currentThread().getName());
        for(SelectionKey key : selector.keys()) {
            Channel channel = key.channel();
            // 不要把消息发给自己
            if(channel instanceof SocketChannel && channel != sChannel){
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                ((SocketChannel)channel).write(buffer);
            }
        }
    }

}

