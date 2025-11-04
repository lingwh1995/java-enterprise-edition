package org.bluebridge.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.EmptyHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.URI;

/**
 * @author lingwh
 * @desc
 * @date 2025/10/21 16:29
 */
public class NettyWebSocketClient {

    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static final String ENDPOINT_PATH_PREFIX = "/websocket";
    private static final String FULL_WS_URL = "ws://" + HOST + ":" + PORT + ENDPOINT_PATH_PREFIX;

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        // 日志处理器
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        // WebSocket处理器
        WebSocketClientHandler WEBSOCKET_CLIENT_HANDLER = new WebSocketClientHandler();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpClientCodec());
                        // 添加日志处理器
                        pipeline.addLast(LOGGING_HANDLER);
                        pipeline.addLast(new HttpObjectAggregator(8192));
                        pipeline.addLast(new WebSocketClientProtocolHandler(
                            WebSocketClientHandshakerFactory.newHandshaker(
                                new URI(FULL_WS_URL),
                                WebSocketVersion.V13,
                                null,
                                false,
                                EmptyHttpHeaders.INSTANCE
                            )
                        ));
                        pipeline.addLast(WEBSOCKET_CLIENT_HANDLER);
                    }
                });

            Channel channel = bootstrap.connect(HOST, PORT).sync().channel();

            // 等待连接关闭
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

}
