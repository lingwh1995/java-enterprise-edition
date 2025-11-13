package org.bluebridge._22_optimize;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.BootstrapConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc 测试SO_BACKLOG参数
 * @date 2025/11/12 22:42
 */

/**
 * 测试方法：
 *    1.启动服务端
 *    2.在 NioEventLoop中 private void processSelectedKey(SelectionKey k, AbstractNioChannel ch) {} 中
 *      if ((readyOps & (SelectionKey.OP_READ | SelectionKey.OP_ACCEPT)) != 0 || readyOps == 0) {} 代码处打断点
 *    3.启动三个客户端，会观察到 当启动前两个客户端时，一切正常，当启动第三个客户端时，服务端会拒绝连接，异常信息为
 *      Caused by: java.net.ConnectException: Connection refused: no further information
 *
 * 为什么可以这样测试呢？
 *   因为服务端和客户端三次握手成功后，说明通通道畅通，然后服务端和客户端会建立连接，如果此时客户端没有连接，则这个通信通道会加入全连接
 *   队列中，这里我们测试时，每次通信通道建立后，客户端没有消费这个通信通道，所以这个通信通道会临时被添加到全连接队列中.
 */
@Slf4j
public class NettySoBacklogServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        new ServerBootstrap()
            .group(group)
            // 设置非阻塞，用它来建立新accept的连接，用于构造ServerSocketChannel的工厂类
            .channel(NioServerSocketChannel.class)
            // 临时存放已完成三次握手的全连接队列的最大长度。
            // 如果未设置或所设置的值小于1，Java将使用默认值50。
            // 如果大于队列的最大长度，请求会被拒绝
            .option(ChannelOption.SO_BACKLOG, 2)    // 全连接队列大小
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                }
            })
            .bind(HOST, PORT);
    }

}
