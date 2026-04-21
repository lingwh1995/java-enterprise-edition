package org.bluebridge._19_protocol_design._03_http_protocol_v2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lingwh
 * @desc 基于 Netty 的 HTTP 服务器
 * @date 2025/10/15 14:42
 */
@Slf4j
public class NettyHttpServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        // 创建自定义事件组，一个线程循环的处理事件，类似与 nio 的 selector
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            //创建服务端主启动类
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                 // 绑定 IP 和端口
                .localAddress(HOST, PORT)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();

                        // 分别设置请求解码器和响应编码器
                        /*
                        // 设置请求解码器
                        pipeline.addLast(new HttpRequestDecoder());
                        // 设置响应编码器
                        pipeline.addLast(new HttpResponseEncoder());
                        */
                        // 一次设置请求解码器和响应编码器，这里使用HttpServerCodec，它包含了HttpRequestDecoder和HttpResponseEncoder
                        pipeline.addLast(new HttpServerCodec());

                        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                        // 聚合操作，将请求体和请求头等聚合
                        pipeline.addLast("aggregator", new HttpObjectAggregator(10 * 1024 * 1024));
                        // 允许压缩解压等操作
                        pipeline.addLast("compressor", new HttpContentCompressor());
                        pipeline.addLast(new ChannelInboundHandlerAdapter() {
                            private AtomicInteger successCount = new AtomicInteger();
                            private AtomicInteger errorCount = new AtomicInteger();

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                FullHttpRequest httpRequest = (FullHttpRequest)msg;
                                String uri = httpRequest.uri();
                                if ("/test".equals(uri)){
                                    successCount.incrementAndGet();
                                }else{
                                    errorCount.incrementAndGet();
                                }
                                log.info("请求成功数为：{}", successCount);
                                log.info("请求失败数为：{}", errorCount);
                                //传递给下一个handler
                                ctx.fireChannelRead(msg);
                            }
                        });
                        pipeline.addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                // netty中封装的http请求
                                FullHttpRequest httpRequest = (FullHttpRequest)msg;
                                String uri = httpRequest.uri();
                                log.info("请求的路径为：" + uri);
                                String body = httpRequest.content().toString(CharsetUtil.UTF_8);
                                log.info("获取到的请求体的数据为：" + body);
                                if ("/test".equals(uri)){
                                    send(ctx, HttpResponseStatus.OK,"请求成功!");
                                }else{
                                    send(ctx,HttpResponseStatus.NOT_FOUND,"路径不存在!");
                                }
                            }

                            private void send(ChannelHandlerContext ctx, HttpResponseStatus status, String context) {
                                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,status,
                                        Unpooled.copiedBuffer(context,CharsetUtil.UTF_8));
                                response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=UTF-8");
                                ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                            }
                        });
                    }
                });
            // 完成绑定,内部如果异步实现bind，因此需要阻塞拿到返回结果
            ChannelFuture future = bootstrap.bind().sync();
            // 关闭future时也需要阻塞，内部也采用的是异步操作
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
