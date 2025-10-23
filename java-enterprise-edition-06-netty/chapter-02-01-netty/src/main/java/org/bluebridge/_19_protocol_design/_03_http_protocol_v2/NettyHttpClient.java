package org.bluebridge._19_protocol_design._03_http_protocol_v2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * @author lingwh
 * @desc 基于 Netty 的 HTTP 客户端
 * @date 2025/10/15 14:51
 */
@Slf4j
public class NettyHttpClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        // 创建自定义事件组，一个线程循环的处理事件，类似与 nio 的 selector
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            //客户端只需要用 bootStrap
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                // 长连接
                .option(ChannelOption.SO_KEEPALIVE,true)
                // 和服务器不一样，这里只需要连接服务器地址即可
                .remoteAddress(new InetSocketAddress(HOST, PORT))
                // 和服务端不同，服务端使用的childHandler客户端只需要具体的 handler 即可
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        // 分别设置响应解码器和请求编码器
                        /*
                        // 设置请求解码器
                        pipeline.addLast(new HttpResponseDecoder());
                        // 设置响应编码器
                        pipeline.addLast(new HttpRequestEncoder());
                        */
                        // 一次设置响应解码器和请求编码器，这里使用 HttpClientCodec ，它包含了 HttpResponseDecoder 和 HttpRequestEncoder
                        pipeline.addLast(new HttpClientCodec());

                        // 类似于 HttpServerCodec 的写法
                        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                        // 聚合成一个完整报文
                        pipeline.addLast("aggregator", new HttpObjectAggregator(10 * 1024 * 1024));
                        // 允许压缩解压等操作
                        pipeline.addLast("decompressor", new HttpContentDecompressor());
                        pipeline.addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                String url = "/test";
                                URI uri = new URI(url);
                                //String msg = "hello netty http server, i am netty http client!";
                                String msg = "hello!";
                                DefaultFullHttpRequest request =
                                        new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString(),
                                                Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));
                                // 构建 http 请求
                                request.headers().set(HttpHeaderNames.HOST, HOST);
                                request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                                request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
                                // 发送 http 请求
                                ctx.writeAndFlush(request);
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                FullHttpResponse httpResponse = (FullHttpResponse) msg;
                                String response = httpResponse.content().toString(StandardCharsets.UTF_8);
                                log.info("来自服务器端的数据： {}", response);
                            }
                        });
                    }
                });
            //完成绑定,内部如果异步实现 bind ，因此需要阻塞拿到返回结果
            ChannelFuture future = bootstrap.connect().sync();
            //关闭 future 时也需要阻塞，内部也采用的是异步操作
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                //处理中断异常
                eventLoopGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
