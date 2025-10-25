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
@Slf4j
@Component
public class ChatServer {

    @Value("${netty.tcp.host}")
    private String host;

    @Value("${netty.tcp.port}")
    private int port;

    @Resource
    private IUserService userService;

    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    // 日志处理器
    LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
    // 消息处理器
    MessageCodecSharable MESSAGE_CODEC_SHARABLE = new MessageCodecSharable();

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
                    pipeline.addLast(LOGGING_HANDLER);
                    pipeline.addLast(MESSAGE_CODEC_SHARABLE);
                    pipeline.addLast(new SimpleChannelInboundHandler<LoginRequestMessage>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
                            String username = msg.getUsername();
                            String password = msg.getPassword();
                            boolean isLogin = userService.login(username, password);
                            LoginResponseMessage message = null;
                            if(isLogin) {
                                message = new LoginResponseMessage(false, "登录成功");
                            } else {
                                message = new LoginResponseMessage(false, "用户名或密码错误");
                            }
                            ctx.channel().writeAndFlush(message);
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
