package org.bluebridge.section_19_protocol_design.unit_01_redis_protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * Netty 版 RedisClient 测试类
 *
 * Windows、Linux、Macos、网络通信中换行符总结
 * 1. \r 对应 ASCII 码 13（0x0D）
 * 2. \n 对应 ASCII 码 10（0x0A）
 * 3. 网络协议中常用 CRLF（\r\n）作为行分隔符
 * 4. 不同操作系统的换行符差异：
 *    - Windows：CRLF（\r\n）
 *    - Linux/Unix：LF（\n）
 *    - MacOS(旧版): CR（\r）
 *
 * @author lingwh
 * @date 2025/10/15 9:33
 */
@Slf4j
public class NettyRedisClientTest {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 6379;

    // 换行符
    private static byte[] NEWLINE_CHARACTERS = { 13, 10 };

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        // 网络通信中通常使用 CRLF（\r\n）作为行分隔符

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new LoggingHandler());
                    pipeline.addLast(new ChannelInboundHandlerAdapter() {
                        // 会在连接 channel 建立成功后，会触发 active 事件
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) {
                            set(ctx);
                            get(ctx);
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            ByteBuf buf = (ByteBuf) msg;
                            log.info(buf.toString(Charset.defaultCharset()));
                        }
                    });
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("client error......", e);
        } finally {
            worker.shutdownGracefully();
        }
    }

    /**
     * 根据 key 获取值
     * @param ctx
     */
    private static void set(ChannelHandlerContext ctx) {
        ByteBuf buf = ctx.alloc().buffer();
        // 命令字符串中子字符串个数 set name zhangsan，这里有 3 个子字符串，所以这里写入 *3
        buf.writeBytes("*3".getBytes());
        buf.writeBytes(NEWLINE_CHARACTERS);
        // set 字符串的长度是 3，所以写入 $3
        buf.writeBytes("$3".getBytes());
        buf.writeBytes(NEWLINE_CHARACTERS);
        // 写入 set 字符串
        buf.writeBytes("set".getBytes());
        buf.writeBytes(NEWLINE_CHARACTERS);
        // name 字符串的长度是 4，所以写入 $4
        buf.writeBytes("$4".getBytes());
        buf.writeBytes(NEWLINE_CHARACTERS);
        // 写入 name 字符串
        buf.writeBytes("name".getBytes());
        buf.writeBytes(NEWLINE_CHARACTERS);
        // zhangsan 字符串的长度是 7，所以写入 $7
        buf.writeBytes("$7".getBytes());
        buf.writeBytes(NEWLINE_CHARACTERS);
        // 写入 zhangsan 字符串
        buf.writeBytes("zhangsan".getBytes());
        buf.writeBytes(NEWLINE_CHARACTERS);
        ctx.writeAndFlush(buf);
    }

    /**
     * 给某个 key 设置值
     * @param ctx
     */
    private static void get(ChannelHandlerContext ctx) {
        ByteBuf buf = ctx.alloc().buffer();
        // 命令字符串中子字符串个数 get name，这里有 2 个子字符串，所以写入 *2
        buf.writeBytes("*2".getBytes());
        buf.writeBytes(NEWLINE_CHARACTERS);
        // get 字符串的长度是 3，所以写入 $3
        buf.writeBytes("$3".getBytes());
        buf.writeBytes(NEWLINE_CHARACTERS);
        // 写入 get 字符串
        buf.writeBytes("get".getBytes());
        buf.writeBytes(NEWLINE_CHARACTERS);
        // name 字符串的长度是 4，所以写入 $4
        buf.writeBytes("$4".getBytes());
        buf.writeBytes(NEWLINE_CHARACTERS);
        // 写入 name 字符串
        buf.writeBytes("name".getBytes());
        buf.writeBytes(NEWLINE_CHARACTERS);
        ctx.writeAndFlush(buf);
    }
}
