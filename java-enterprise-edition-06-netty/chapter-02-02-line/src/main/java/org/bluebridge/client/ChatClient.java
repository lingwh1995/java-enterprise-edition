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
import org.bluebridge.protocol.MessageCodecSharable;
import org.bluebridge.protocol.ProcotolFrameDecoder;

import java.util.Scanner;

/**
 * @author lingwh
 * @desc 聊天客户端
 * @date 2025/10/25 12:17
 */
@Slf4j
public class ChatClient {

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) {

        NioEventLoopGroup group = new NioEventLoopGroup();
        // 日志处理器
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        // 消息处理器
        MessageCodecSharable MESSAGE_CODEC_SHARABLE = new MessageCodecSharable();

        try {
            Bootstrap bootstrap = new Bootstrap()
                .channel(NioSocketChannel.class)
                .group(group)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ProcotolFrameDecoder());
                        pipeline.addLast(LOGGING_HANDLER);
                        pipeline.addLast(MESSAGE_CODEC_SHARABLE);
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
                                }).start();
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("{}", msg);
                            }
                        });
                    }
                });
            Channel channel = bootstrap.connect(HOST, PORT).sync().channel();
            log.info("Line客户端启动成功，连接到服务器：{}，端口：{}", HOST, PORT);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("client error", e);
        } finally {
            group.shutdownGracefully();
        }
    }

}
