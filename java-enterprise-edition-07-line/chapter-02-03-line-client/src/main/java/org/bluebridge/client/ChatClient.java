package org.bluebridge.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.client.handler.ClientHandler;
import org.bluebridge.domain.PingMessage;
import org.bluebridge.protocol.MessageCodecSharable;
import org.bluebridge.protocol.ProcotolFrameDecoder;
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
public class ChatClient {

    @Value("${netty.tcp.host}")
    private String host;

    @Value("${netty.tcp.port}")
    private int port;

    @Resource
    private LoggingHandler loggingHandler;

    @Resource
    private MessageCodecSharable messageCodecSharable;

    @Resource
    private ClientHandler clientHandler;

    NioEventLoopGroup group = new NioEventLoopGroup();

    @PostConstruct
    public void startNettyClient() {
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .channel(NioSocketChannel.class)
                    .group(group)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 3s 内如果没有向服务器写数据，就会触发 IdleState#WRITER_IDLE 事件，这个时间一般设置为服务器时间的 1/2
                            pipeline.addLast(new IdleStateHandler(0, 3, 0));
                            // ChannelDuplexHandler 可以同时处理读事件和写事件
                            pipeline.addLast(new ChannelDuplexHandler() {
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                    if(evt instanceof IdleStateEvent) {
                                        IdleStateEvent event = (IdleStateEvent) evt;
                                        // 触发了写空闲事件
                                        if(event.state() == IdleState.WRITER_IDLE) {
                                            log.info("3s 内未收到 channel 的写事件，触发 IdleState#WRITER_IDLE 事件[写空闲事件]......");
                                            ctx.writeAndFlush(new PingMessage());
                                        }
                                    }
                                }
                            });
                            pipeline.addLast(new ProcotolFrameDecoder());
                            pipeline.addLast(loggingHandler);
                            pipeline.addLast(messageCodecSharable);
                            pipeline.addLast(clientHandler);
                        }
                    });
            Channel channel = bootstrap.connect(host, port).sync().channel();
            log.info("Line客户端启动成功，连接到服务器：{}，端口：{}", host, port);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("client error", e);
        } finally {
            group.shutdownGracefully();
        }
    }

    @PreDestroy
    public void stop() {
        group.shutdownGracefully();
    }


}
