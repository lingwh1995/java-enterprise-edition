package org.bluebridge._017_resolve_sticky_packet_and_half_packet._01_short_connection;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc Netty黏包测试 客户端
 * @date 2025/10/11 10:43
 */
@Slf4j
public class ShortConnectionClient {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            sendMsg(i);
        }
    }

    private static void sendMsg(int i) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                .channel(NioSocketChannel.class)
                .group(worker)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        log.info("connected......");
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                log.info("sending......");
                                log.info("第{}次向服务端发送数据......", i + 1);
                                ByteBuf byteBuf = ctx.alloc().buffer();
                                byteBuf.writeBytes(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'a', 'b', 'c', 'd', 'e', 'f', 16, 17});
                                ctx.writeAndFlush(byteBuf);
                                // 每发一次数据就关闭连接
                                ctx.channel().close();
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

}