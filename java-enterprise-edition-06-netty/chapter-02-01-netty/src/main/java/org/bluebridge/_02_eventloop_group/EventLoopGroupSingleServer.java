package org.bluebridge._02_eventloop_group;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author lingwh
 * @desc 单事件循环组 服务端
 * @date 2025/9/23 11:58
 */

/**
 * 单参数版本 => public ServerBootstrap group(EventLoopGroup group)
 * 1.用于父级和子级EventLoopGroup使用同一个实例的场景，内部会将同一个group同时作为parentGroup和childGroup使用
 * 2.适用于单线程模型的场景，如简单的服务器应用，不需要复杂的线程模型配置
 */

/**
 * 关于Netty的NioEventLoopGroup的默认线程数量大小，以下是关键信息：
 *   默认线程数：NioEventLoopGroup 默认的线程数量是 CPU 核心数的两倍。
 *   具体来说，其默认构造函数会使用 Runtime.getRuntime().availableProcessors() * 2 作为线程数。
 *   这适用于大多数 I/O 密集型应用，能够较好地利用多核 CPU 性能。
 * 构造方式：
 *   new NioEventLoopGroup()：无参构造函数会自动设置线程数为 CPU 核心数的两倍。
 *   也可以通过 new NioEventLoopGroup(int nThreads) 手动指定线程数。
 * 适用场景：
 *   对于I/O密集型任务（如网络通信），默认值通常是合适的。
 *   如果是计算密集型任务，可能需要根据实际情况调整线程数，避免过多线程导致上下文切换开销。
 * 总结：
 *   Netty的NioEventLoopGroup的默认线程数量大小为 CPU 核心数 × 2，这是基于I/O多路复用和并发处理的优化设定。
 */
@Slf4j
public class EventLoopGroupSingleServer {

    public static void main(String[] args) {
        new ServerBootstrap()
            .group(new NioEventLoopGroup()) // 无参构造函数会自动设置线程数为 CPU 核心数的两倍。
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) {
                            ByteBuf byteBuf = (ByteBuf) msg;
                            String s = byteBuf.toString(Charset.defaultCharset());
                            // 每个客户端都对应着线程池中的一个线程，可能会出现一个线程处理了多个客户端的连接，这取决于线程池大小和客户端数量
                            log.debug("线程名称:{}, 收到消息:{}", Thread.currentThread().getName(), s);
                            //ByteBufUtil.debugRead(buf);
                        }
                    });
                }
            })
            .bind(8080);
    }

}
