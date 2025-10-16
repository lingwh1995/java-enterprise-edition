package org.bluebridge._19_group_chat.client;

import org.bluebridge._19_group_chat.client.handler.ClientHandler;
import org.bluebridge._19_group_chat.message.PingMessage;
import org.bluebridge._19_group_chat.protocol.MessageCodecSharable;
import org.bluebridge._19_group_chat.protocol.ProcotolFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ChatClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        ClientHandler CLIENT_HANDLER = new ClientHandler();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(group);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new ProcotolFrameDecoder());
                    pipeline.addLast(LOGGING_HANDLER);
                    pipeline.addLast(MESSAGE_CODEC);
                    // 用来判断是不是 读空闲时间过长，或 写空闲时间过长
                    // 10s 内如果没有向服务器写数据，会触发一个 IdleState#WRITER_IDLE 事件
                    pipeline.addLast(new IdleStateHandler(0, 10, 0));
                    // ChannelDuplexHandler 可以同时作为入站和出站处理器
                    pipeline.addLast(new ChannelDuplexHandler() {
                        // 用来触发特殊事件
                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
                            IdleStateEvent event = (IdleStateEvent) evt;
                            // 触发了写空闲事件
                            if (event.state() == IdleState.WRITER_IDLE) {
                                 log.info("10s 没有写数据了，发送一个心跳包");
                                 ctx.writeAndFlush(new PingMessage());
                            }
                        }
                    });
                    pipeline.addLast(CLIENT_HANDLER);
                }
            });
            Channel channel = bootstrap.connect(HOST, PORT).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("client error", e);
        } finally {
            group.shutdownGracefully();
        }
    }

}
