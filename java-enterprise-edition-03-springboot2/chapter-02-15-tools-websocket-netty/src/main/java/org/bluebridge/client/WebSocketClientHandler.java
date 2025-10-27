package org.bluebridge.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * @author lingwh
 * @desc 客户端处理器
 * @date 2025/10/21 16:31
 */
@Slf4j
@ChannelHandler.Sharable
public class WebSocketClientHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 可以在这里处理handler添加事件
        super.handlerAdded(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketClientProtocolHandler.ClientHandshakeStateEvent) {
            WebSocketClientProtocolHandler.ClientHandshakeStateEvent handshakeEvent =
                    (WebSocketClientProtocolHandler.ClientHandshakeStateEvent) evt;
            if (handshakeEvent == WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE) {
                new Thread(() -> {
                    log.info("WebSocket握手完成，可以发送消息，请输入消息（输入 exit 退出）");
                    // 改为从终端中接收信息
                    Scanner scanner = new Scanner(System.in);
                    while (scanner.hasNextLine()) {
                        System.out.print("请输入消息（输入 exit 退出）：\n");
                        String message = scanner.nextLine();
                        if ("exit".equals(message)) {
                            break;
                        }
                        // 握手完成后发送消息
                        ctx.channel().writeAndFlush(new TextWebSocketFrame(message));
                    }
                }).start();
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("收到服务器消息: {}", msg.text());
    }

}
