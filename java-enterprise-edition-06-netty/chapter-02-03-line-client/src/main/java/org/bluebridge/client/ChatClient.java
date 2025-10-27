package org.bluebridge.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.domain.LoginRequestMessage;
import org.bluebridge.domain.LoginResponseMessage;
import org.bluebridge.protocol.MessageCodecSharable;
import org.bluebridge.protocol.ProcotolFrameDecoder;
import org.bluebridge.client.service.IUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

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
    private IUserService userService;

    @Resource
    private LoggingHandler loggingHandler;

    @Resource
    private MessageCodecSharable messageCodecSharable;

    NioEventLoopGroup group = new NioEventLoopGroup();

    CountDownLatch WAIT_FOR_LOGIN = new CountDownLatch(1);

    AtomicBoolean LOGIN = new AtomicBoolean(false);

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
                            pipeline.addLast(new ProcotolFrameDecoder());
                            pipeline.addLast(loggingHandler);
                            pipeline.addLast(messageCodecSharable);
                            pipeline.addLast(new ChannelInboundHandlerAdapter() {
                                /**
                                 * 当通道激活时调用，发送登录消息
                                 * @param ctx
                                 * @throws Exception
                                 */
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    new Thread(() -> {
                                        log.info("请输入用户名:");
                                        Scanner scanner = new Scanner(System.in);
                                        String username = scanner.nextLine();
//                                if(EXIT.get()){
//                                    return;
//                                }
                                        log.info("请输入密码:");
                                        String password = scanner.nextLine();
//                                if(EXIT.get()){
//                                    return;
//                                }
                                        // 构造消息对象
                                        LoginRequestMessage message = new LoginRequestMessage(username, password);
                                        log.info("登录消息：{}", message);
                                        // 发送消息
                                        ctx.writeAndFlush(message);
                                        log.info("已发送登录消息，等待后续操作......");
                                        try {
                                            // 停止等待
                                            WAIT_FOR_LOGIN.await();
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }

                                        // 如果登录失败，退出程序
                                        if(!LOGIN.get()){
                                            log.info("登录失败，退出程序");
                                            ctx.channel().close();
                                            return;
                                        }
                                        System.out.println("xxxx");
                                    }).start();
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    log.info("{}", msg);
                                    if(msg instanceof LoginResponseMessage){
                                        LoginResponseMessage loginResponseMessage = (LoginResponseMessage) msg;
                                        LOGIN.set(loginResponseMessage.isSuccess());
                                    }

                                    // 计数器减一
                                    WAIT_FOR_LOGIN.countDown();
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    log.error("客户端发生异常", cause);
                                    ctx.close();
                                }
                            });
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
