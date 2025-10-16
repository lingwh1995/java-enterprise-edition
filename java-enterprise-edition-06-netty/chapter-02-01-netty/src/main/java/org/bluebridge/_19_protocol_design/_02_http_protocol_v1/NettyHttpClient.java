package org.bluebridge._19_protocol_design._02_http_protocol_v1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

/**
 * @author lingwh
 * @desc 基于Netty的HTTP客户端
 * @date 2025/10/15 10:38
 */
@Slf4j
public class NettyHttpClient {

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        NettyHttpClient client = new NettyHttpClient();

        // 发送get请求并获取响应内容
        CompletableFuture<String> future = client.get(HOST, PORT, "/");
        future.thenAccept(response -> log.info("GET 请求响应内容: {}", response))
            .exceptionally(throwable -> {
                throwable.printStackTrace();
                return null;
            })
            .join();

        // 发送post请求并获取响应内容
        String json = "{\"msg\":\"hello netty\"}";
        future = client.post(HOST, PORT, "/", json);
        future.thenAccept(response -> log.info("POST 请求响应内容: {}", response))
            .exceptionally(throwable -> {
                throwable.printStackTrace();
                return null;
            })
            .join();

        client.shutdown();
    }

    private final EventLoopGroup group = new NioEventLoopGroup(4);
    private final Bootstrap bootstrap = new Bootstrap();

    public NettyHttpClient() {
        bootstrap.group(group)
            .channel(NioSocketChannel.class)
            // 关闭 Nagle
            .option(ChannelOption.TCP_NODELAY, true)
            // TCP KeepAlive
            .option(ChannelOption.SO_KEEPALIVE, true)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();
                    // 1. HTTP 编解码

                    // 分别设置响应解码器和请求编码器
                    /*
                    // 设置请求解码器
                    pipeline.addLast(new HttpResponseDecoder());
                    // 设置响应编码器
                    pipeline.addLast(new HttpRequestEncoder());
                    */
                    // 一次设置响应解码器和请求编码器，这里使用HttpClientCodec，它包含了HttpResponseDecoder和HttpRequestEncoder
                    pipeline.addLast(new HttpClientCodec());

                    // 2.日志处理器
                    pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                    // 3. 聚合分片 -> FullHttpResponse
                    pipeline.addLast(new HttpObjectAggregator(64 * 1024));
                    // 4. 业务处理器
                    pipeline.addLast(new HttpResponseHandler());
                }
            });
    }

    /**
     * 发送 GET 请求
     */
    public CompletableFuture<String> get(String host, int port, String uri) {
        return send(HttpMethod.GET, host, port, uri, null);
    }

    /**
     * 发送 POST 请求
     */
    public CompletableFuture<String> post(String host, int port, String uri, String jsonBody) {
        return send(HttpMethod.POST, host, port, uri, jsonBody);
    }

    private CompletableFuture<String> send(HttpMethod method, String host, int port, String uri, String body) {
        CompletableFuture<String> promise = new CompletableFuture<>();
        ChannelFuture cf = bootstrap.connect(host, port);
        cf.addListener((ChannelFutureListener) f -> {
            if (!f.isSuccess()) {
                promise.completeExceptionally(f.cause());
                return;
            }
            Channel ch = f.channel();

            byte[] bodyBytes = body == null ? new byte[0] : body.getBytes(StandardCharsets.UTF_8);
            FullHttpRequest req = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method, uri, Unpooled.wrappedBuffer(bodyBytes));
            req.headers()
                    .set(HttpHeaderNames.HOST, host)
                    .set(HttpHeaderNames.CONTENT_TYPE, "application/json")
                    .set(HttpHeaderNames.CONTENT_LENGTH, bodyBytes.length);

            // 把 promise 绑定到 Channel
            ch.attr(HttpResponseHandler.PROMISE_KEY).set(promise);
            ch.writeAndFlush(req);
        });
        return promise;
    }

    public void shutdown() {
        group.shutdownGracefully();
    }

    /**
     * 响应处理器
     */
    static class HttpResponseHandler extends SimpleChannelInboundHandler<FullHttpResponse> {
        static final AttributeKey<CompletableFuture<String>> PROMISE_KEY = AttributeKey.valueOf("PROMISE");

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse resp) {
            CompletableFuture<String> f = ctx.channel().attr(PROMISE_KEY).get();
            if (f != null) {
                f.complete(resp.content().toString(StandardCharsets.UTF_8));
            }
            // 关闭连接（可根据业务复用）
            ctx.close();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            CompletableFuture<String> f = ctx.channel().attr(PROMISE_KEY).get();
            if (f != null) {
                f.completeExceptionally(cause);
            }
            ctx.close();
        }
    }

}