package org.bluebridge._19_protocol_design._02_http_protocol_v1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;

/**
 * @author lingwh
 * @desc 基于 Netty 的 HTTP 服务器
 * @date 2025/10/15 10:13
 */

/**
 * 测试方法： 访问 http://localhost:8080/
 */
@Slf4j
public class NettyHttpServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
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
                    // 一次设置请求解码器和响应编码器，这里使用 HttpServerCodec ，它包含了 HttpRequestDecoder 和 HttpResponseEncoder
                    pipeline.addLast(new HttpServerCodec());

                    pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                    // 如果需要读取POST请求的请求体数据，需要在 pipeline 中添加 HttpObjectAggregator，如果仅需读取 GET 请求，可以注释掉下面一行
                    pipeline.addLast(new HttpObjectAggregator(64 * 1024));
                    pipeline.addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, HttpRequest httpRequest) {
                            // netty 模拟 HttpServer 处理来自客户端的 GET 请求
                            if("GET".equals(httpRequest.method().name())){
                                // 获取请求
                                log.info(httpRequest.uri());
                                // 返回响应
                                DefaultFullHttpResponse response = new DefaultFullHttpResponse(httpRequest.protocolVersion(),
                                        HttpResponseStatus.OK);
                                byte[] bytes = "<h1>Hello, world!</h1>".getBytes();

                                //为响应增加 mime 类型和长度
                                response.headers().set(CONTENT_TYPE,"text/html");
                                response.headers().setInt(CONTENT_LENGTH, bytes.length);
                                response.content().writeBytes(bytes);
                                // 把响应渲染到 html 客户端页面上，接下来会经过出栈处理器处理
                                ctx.writeAndFlush(response);
                            }

                            // netty 模拟 HttpServer 处理来自客户端的 POST 请求（实现直接返回 POST 请求体数据）
                            if("POST".equals(httpRequest.method().name())){
                                // 处理 POST 请求
                                log.info("POST request: {}", httpRequest.uri());
                                // 如果需要读取请求体，需要添加 HttpObjectAggregator 处理器
                                // 这里简单返回成功响应
                                DefaultFullHttpResponse response = new DefaultFullHttpResponse(httpRequest.protocolVersion(), HttpResponseStatus.OK);
                                // 获取 post 请求发送的数据
                                FullHttpRequest fullHttpRequest = (FullHttpRequest) httpRequest;
                                String requestBody = fullHttpRequest.content().toString(StandardCharsets.UTF_8);
                                log.info("请求体内容： {}", requestBody);
                                byte[] responseBody = requestBody.getBytes();
                                response.headers().setInt(CONTENT_LENGTH, responseBody.length);
                                response.content().writeBytes(responseBody);
                                // 写回响应
                                ctx.writeAndFlush(response);
                            }
                        }
                    });
                    /*
                    pipeline.addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.info("{}", msg.getClass());

                            if (msg instanceof HttpRequest) { // 请求行，请求头

                            } else if (msg instanceof HttpContent) { //请求体

                            }
                        }
                    });
                    */
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(HOST, PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error......", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

}
