package org.bluebridge.client.initializer;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.client.handler.ClientHandler;
import org.bluebridge.domain.PingMessage;
import org.bluebridge.protocol.MessageCodecSharable;
import org.bluebridge.protocol.ProcotolFrameDecoder;
import org.springframework.stereotype.Component;

/**
 * @author lingwh
 * @desc Channel 初始化配置类 使用@RequiredArgsConstructor + final 简化依赖注入
 * @date 2025/11/10 15:10
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ChannelInitializer extends io.netty.channel.ChannelInitializer<NioSocketChannel> {

    private final LoggingHandler LOGGING_HANDLER;
    private final MessageCodecSharable MESSAGE_CODEC_SHARABLE;
    private final ClientHandler CLIENT_HANDLER;

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 30s 内如果没有向服务器写数据，就会触发 IdleState#WRITER_IDLE 事件，这个时间一般设置为服务器时间的 1/2
        pipeline.addLast(new IdleStateHandler(0, 30, 0));
        // ChannelDuplexHandler 可以同时处理读事件和写事件
        pipeline.addLast(new ChannelDuplexHandler() {
            @Override
            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                if(evt instanceof IdleStateEvent) {
                    IdleStateEvent event = (IdleStateEvent) evt;
                    // 触发了写空闲事件
                    if(event.state() == IdleState.WRITER_IDLE) {
                        log.info("30s 内未收到 channel 的写事件，触发 IdleState#WRITER_IDLE 事件[写空闲事件]......");
                        ctx.writeAndFlush(new PingMessage());
                    }
                }
            }
        });
        pipeline.addLast(new ProcotolFrameDecoder());
        pipeline.addLast(LOGGING_HANDLER);
        pipeline.addLast(MESSAGE_CODEC_SHARABLE);
        pipeline.addLast(CLIENT_HANDLER);
    }

}
