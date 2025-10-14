package org.bluebridge._017_resolve_sticky_packet_and_half_packet._03_delimiter_based_frame_decoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author lingwh
 * @desc 自定义分隔符解码器解决黏包半包问题 客户端
 * @date 2025/10/14 11:48
 */
@Slf4j
public class DelimiterBasedFrameDecoderClient {

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    log.info("connected......");
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            log.info("sending......");
                            ByteBuf buf = ctx.alloc().buffer();
                            char c = '0';
                            Random r = new Random();
                            for (int i = 0; i < 10; i++) {
                                StringBuilder sb = makeString(c, r.nextInt(5) + 1);
                                c++;
                                buf.writeBytes(sb.toString().getBytes());
                            }
                            ctx.writeAndFlush(buf);
                        }
                    });
                }
            });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            log.error("client error......", e);
        } finally {
            worker.shutdownGracefully();
        }
    }

    /**
     * 给消息最后添加换行符
     * @param c
     * @param len
     * @return
     */
    public static StringBuilder makeString(char c, int len) {
        StringBuilder sb = new StringBuilder(len + 2);
        for (int i = 0; i < len; i++) {
            sb.append(c);
        }
        sb.append("$");
        return sb;
    }

}
