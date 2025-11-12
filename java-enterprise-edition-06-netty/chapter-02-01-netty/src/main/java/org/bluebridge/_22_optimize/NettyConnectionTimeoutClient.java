package org.bluebridge._22_optimize;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.BootstrapConfig;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc 测试连接超时
 * @date 2025/11/11 23:09
 */
@Slf4j
public class NettyConnectionTimeoutClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            // 客户端通过 .options() 方法配置参数给 SocketChannel 配置参数
            Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 300) // 设置连接超时时间，单位毫秒
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                        // 内部使用 CharBuffer.wrap(msg)
                        pipeline.addLast(new StringEncoder());
                    }
                });
            ChannelFuture future = bootstrap.connect(HOST, PORT);
            // 获取 netty 配置
            BootstrapConfig config = bootstrap.config();
            log.info("config: {}", config);
            future.sync().channel().closeFuture().channel();
        }catch (Exception e) {
            log.error("发生了异常，异常原因：{}", e.getMessage());
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

}
