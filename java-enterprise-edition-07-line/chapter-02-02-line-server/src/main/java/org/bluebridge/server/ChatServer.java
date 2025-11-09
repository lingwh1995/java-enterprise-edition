package org.bluebridge.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.domain.PingMessage;
import org.bluebridge.domain.PongMessage;
import org.bluebridge.protocol.MessageCodecSharable;
import org.bluebridge.protocol.ProcotolFrameDecoder;
import org.bluebridge.server.handler.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @author lingwh
 * @desc 聊天服务器
 * @date 2025/10/25 12:17
 */

/**
 * 测试方法
 *  1.cmd -> telnet 127.0.0.1 8080/telnet localhost 8080 -> 直接输入内容（只能发送单个字符）/按下Ctrl+]后输入 send + 内容（可以发送字符串） -> 查看idea控制台接收到的信息
 *  2.启动多个客户端
 */
@Slf4j
@Component
public class ChatServer {

    @Value("${netty.tcp.host}")
    private String host;

    @Value("${netty.tcp.port}")
    private int port;

    @Resource
    private LoggingHandler loggingHandler;

    @Resource
    private MessageCodecSharable messageCodecSharable;

    @Resource
    private LoginRequestMessageHandler loginRequestMessageHandler;

    @Resource
    private ChatRequestMessageHandler chatRequestMessageHandler;

    @Resource
    private GroupCreateRequestMessageHandler groupCreateRequestMessageHandler;

    @Resource
    private GroupMembersRequestMessageHandler groupMembersRequestMessageHandler;

    @Resource
    private GroupAddRequestMessageHandler groupAddRequestMessageHandler;

    @Resource
    private GroupJoinRequestMessageHandler groupJoinRequestMessageHandler;

    @Resource
    private GroupChatRequestHandler groupChatRequestHandler;

    @Resource
    private GroupQuitRequestMessageHandler groupQuitRequestMessageHandler;

    @Resource
    private QuitRequestHandler quitRequestHandler;

    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    @PostConstruct
    public void startNettyServer() {
        ServerBootstrap serverBootstrap = new ServerBootstrap()
            .group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    // 5s 内如果没有收到 channel 的读事件，就会触发 IdleState#READER_IDLE 事件
                    pipeline.addLast(new IdleStateHandler(5, 0, 0));
                    // ChannelDuplexHandler 可以同时处理读事件和写事件
                    pipeline.addLast(new ChannelDuplexHandler() {
                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                            if(evt instanceof IdleStateEvent) {
                                IdleStateEvent event = (IdleStateEvent) evt;
                                // 触发了读空闲事件
                                if(event.state() == IdleState.READER_IDLE) {
                                    log.info("5s 内未收到 channel 的读事件，触发 IdleState#READER_IDLE 事件[读空闲事件]......");
                                    ctx.writeAndFlush(new PongMessage());
                                }
                            }
                        }
                    });
                    pipeline.addLast(new ProcotolFrameDecoder());
                    pipeline.addLast(loggingHandler);
                    pipeline.addLast(messageCodecSharable);
                    pipeline.addLast(loginRequestMessageHandler);
                    pipeline.addLast(chatRequestMessageHandler);
                    pipeline.addLast(groupCreateRequestMessageHandler);
                    pipeline.addLast(groupMembersRequestMessageHandler);
                    pipeline.addLast(groupAddRequestMessageHandler);
                    pipeline.addLast(groupJoinRequestMessageHandler);
                    pipeline.addLast(groupChatRequestHandler);
                    pipeline.addLast(groupQuitRequestMessageHandler);
                    pipeline.addLast(quitRequestHandler);
                }
            });
        try {
            Channel channel = serverBootstrap.bind(host, port).sync().channel();
            log.info("Line服务器端启动成功，监听地址：{}，端口：{}", host, port);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("Line服务器端启动发生异常......", e);
        }
    }

    @PreDestroy
    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
