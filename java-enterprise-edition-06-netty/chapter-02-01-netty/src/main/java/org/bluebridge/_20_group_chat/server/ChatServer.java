package org.bluebridge._20_group_chat.server;

import io.netty.channel.*;
import org.bluebridge._20_group_chat.protocol.MessageCodecSharable;
import org.bluebridge._20_group_chat.protocol.ProcotolFrameDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge._20_group_chat.server.handler.*;

@Slf4j
public class ChatServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        LoginRequestMessageHandler LOGIN_HANDLER = new LoginRequestMessageHandler();
        ChatRequestMessageHandler CHAT_HANDLER = new ChatRequestMessageHandler();
        GroupCreateRequestMessageHandler GROUP_CREATE_HANDLER = new GroupCreateRequestMessageHandler();
        GroupJoinRequestMessageHandler GROUP_JOIN_HANDLER = new GroupJoinRequestMessageHandler();
        GroupMembersRequestMessageHandler GROUP_MEMBERS_HANDLER = new GroupMembersRequestMessageHandler();
        GroupQuitRequestMessageHandler GROUP_QUIT_HANDLER = new GroupQuitRequestMessageHandler();
        GroupChatRequestMessageHandler GROUP_CHAT_HANDLER = new GroupChatRequestMessageHandler();
        // 添加心跳包处理器
        PingMessageHandler PING_HANDLER = new PingMessageHandler();
        QuitHandler QUIT_HANDLER = new QuitHandler();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new ProcotolFrameDecoder());
                    pipeline.addLast(LOGGING_HANDLER);
                    pipeline.addLast(MESSAGE_CODEC);
                    // 用来判断是不是 读空闲时间过长，或 写空闲时间过长
                    // 30s 内如果没有收到 channel 的数据，会触发一个 IdleState#READER_IDLE 事件
                    pipeline.addLast(new IdleStateHandler(30, 0, 0));
                    // ChannelDuplexHandler 可以同时作为入站和出站处理器
                    pipeline.addLast(new ChannelDuplexHandler() {
                        // 用来触发特殊事件
                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
                            IdleStateEvent event = (IdleStateEvent) evt;
                            // 触发了读空闲事件
                            if (event.state() == IdleState.READER_IDLE) {
                                log.info("已经 30s 没有读到数据了......");
                                ctx.channel().close();
                            }
                        }
                    });
                    pipeline.addLast(LOGIN_HANDLER);
                    pipeline.addLast(CHAT_HANDLER);
                    pipeline.addLast(GROUP_CREATE_HANDLER);
                    pipeline.addLast(GROUP_JOIN_HANDLER);
                    pipeline.addLast(GROUP_MEMBERS_HANDLER);
                    pipeline.addLast(GROUP_QUIT_HANDLER);
                    pipeline.addLast(GROUP_CHAT_HANDLER);
                    // 添加心跳包处理器
                    pipeline.addLast(PING_HANDLER);
                    pipeline.addLast(QUIT_HANDLER);
                }
            });
            Channel channel = serverBootstrap.bind(HOST, PORT).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error......", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
