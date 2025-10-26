package org.bluebridge.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.domain.LoginRequestMessage;
import org.bluebridge.domain.LoginResponseMessage;
import org.bluebridge.protocol.MessageCodecSharable;
import org.bluebridge.protocol.ProcotolFrameDecoder;
import org.bluebridge.server.service.IUserService;
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
    private IUserService userService;

    @Resource
    private LoggingHandler loggingHandler;

    @Resource
    private MessageCodecSharable messageCodecSharable;

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
                    pipeline.addLast(new ProcotolFrameDecoder());
                    pipeline.addLast(loggingHandler);
                    pipeline.addLast(messageCodecSharable);
                    pipeline.addLast(new SimpleChannelInboundHandler<LoginRequestMessage>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
                            String username = msg.getUsername();
                            String password = msg.getPassword();
                            boolean isLogin = userService.login(username, password);
                            LoginResponseMessage message = null;
                            if(isLogin) {
                                message = new LoginResponseMessage(true, "登录成功");
                            } else {
                                message = new LoginResponseMessage(false, "用户名或密码错误");
                            }
                            ctx.channel().writeAndFlush(message);
                        }

                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                            log.info("server......");
                            super.exceptionCaught(ctx, cause);
                        }
                    });
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
