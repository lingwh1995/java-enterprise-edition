package org.bluebridge;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author lingwh
 * @desc 客户端
 * @date 2025/8/20 17:24
 */
@Slf4j(topic = "·")
public class HelloClient {

    public static void main(String[] args) throws InterruptedException {
        // 1.启动类
        new Bootstrap()
            // 2.添加 EventLoop
            .group(new NioEventLoopGroup())
            // 3.选择客户 Socket 实现类，NioSocketChannel 表示基于 NIO 的客户端实现
            .channel(NioSocketChannel.class)
            // 4.添加处理器，仅执行一次，它的作用是待客户端SocketChannel建立连接后，执行initChannel以便添加更多的处理器
            .handler(new ChannelInitializer<NioSocketChannel>() {
                @Override // 在连接建立后被调用
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    // 消息会经过通道 handler 处理，这里是将 String => ByteBuf 编码发出
                    ch.pipeline().addLast(new StringEncoder());
                }
            })
            // 5. 连接到服务器
            .connect(new InetSocketAddress("127.0.0.1", 8080))
            // netty 中很多方法都是异步的，如connect，这时需要使用 sync 方法等待 connect 建立连接完毕
            .sync()
            // 获取 channel 对象，它即为通道抽象，可以进行数据读写操作
            .channel()
            // 6. 向服务器发送消息并清空缓冲区
            .writeAndFlush("hello, world");
    }

}

