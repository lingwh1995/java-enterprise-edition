package org.bluebridge._18_protocol_design.http_v1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;

/**
 * @author lingwh
 * @desc 基于Netty的HTTP服务器
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
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    ch.pipeline().addLast(new HttpServerCodec());
                    // 如果需要读取POST请求的请求体数据，需要在pipeline中添加 HttpObjectAggregator，如果仅需读取GET请求，可以注释掉下面一行
                    ch.pipeline().addLast(new HttpObjectAggregator(64 * 1024));
                    ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, HttpRequest httpRequest) {
                            // netty模拟HttpServer处理来自客户端的GET请求
                            if("GET".equals(httpRequest.method().name())){
                                // 获取请求
                                log.info(httpRequest.uri());
                                // 返回响应
                                DefaultFullHttpResponse response = new DefaultFullHttpResponse(httpRequest.protocolVersion(), HttpResponseStatus.OK);
                                byte[] bytes = "<h1>Hello, world!</h1>".getBytes();
                                response.headers().setInt(CONTENT_LENGTH, bytes.length);
                                response.content().writeBytes(bytes);
                                // 写回响应
                                ctx.writeAndFlush(response);
                            }

                            // netty模拟HttpServer处理来自客户端的POST请求（实现直接返回POST请求体数据）
                            if("POST".equals(httpRequest.method().name())){
                                // 处理POST请求
                                log.info("POST request: {}", httpRequest.uri());
                                // 如果需要读取请求体，需要添加HttpObjectAggregator处理器
                                // 这里简单返回成功响应
                                DefaultFullHttpResponse response = new DefaultFullHttpResponse(httpRequest.protocolVersion(), HttpResponseStatus.OK);
                                // 获取post请求发送的数据
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
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
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
