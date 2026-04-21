package org.bluebridge.server.initializer;

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
import org.bluebridge.domain.PongMessage;
import org.bluebridge.protocol.MessageCodecSharable;
import org.bluebridge.protocol.ProcotolFrameDecoder;
import org.bluebridge.server.handler.*;
import org.springframework.stereotype.Component;

/**
 * @author lingwh
 * @desc Channel 初始化配置类 使用@RequiredArgsConstructor + final 简化依赖注入
 * @date 2025/11/10 10:24
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ChannelInitializer extends io.netty.channel.ChannelInitializer<NioSocketChannel> {

    private final LoggingHandler LOGGING_HANDLER;
    private final MessageCodecSharable MESSAGE_CODEC_SHARABLE;
    private final LoginRequestMessageHandler LOGIN_REQUEST_MESSAGE_HANDLER;
    private final ChatRequestMessageHandler CHAT_REQUEST_MESSAGE_HANDLER;
    private final GroupCreateRequestMessageHandler GROUP_CREATE_REQUEST_MESSAGE_HANDLER;
    private final GroupMembersRequestMessageHandler GROUP_MEMBERS_REQUEST_MESSAGE_HANDLER;
    private final GroupAddRequestMessageHandler GROUP_ADD_REQUEST_MESSAGE_HANDLER;
    private final GroupJoinRequestMessageHandler GROUP_JOIN_REQUEST_MESSAGE_HANDLER;
    private final GroupChatRequestHandler GROUP_CHAT_REQUEST_HANDLER;
    private final GroupQuitRequestMessageHandler GROUP_QUIT_REQUEST_MESSAGE_HANDLER;
    private final QuitRequestHandler QUIT_REQUEST_HANDLER;

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 60s 内如果没有收到 channel 的读事件，就会触发 IdleState#READER_IDLE 事件
        pipeline.addLast(new IdleStateHandler(60, 0, 0));
        // ChannelDuplexHandler 可以同时处理读事件和写事件
        pipeline.addLast(new ChannelDuplexHandler() {
            @Override
            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                if(evt instanceof IdleStateEvent) {
                    IdleStateEvent event = (IdleStateEvent) evt;
                    // 触发了读空闲事件
                    if(event.state() == IdleState.READER_IDLE) {
                        log.info("60s 内未收到 channel 的读事件，触发 IdleState#READER_IDLE 事件[读空闲事件]......");
                        ctx.writeAndFlush(new PongMessage());
                    }
                }
            }
        });
        pipeline.addLast(new ProcotolFrameDecoder());
        pipeline.addLast(LOGGING_HANDLER);
        pipeline.addLast(MESSAGE_CODEC_SHARABLE);
        pipeline.addLast(LOGIN_REQUEST_MESSAGE_HANDLER);
        pipeline.addLast(CHAT_REQUEST_MESSAGE_HANDLER);
        pipeline.addLast(GROUP_CREATE_REQUEST_MESSAGE_HANDLER);
        pipeline.addLast(GROUP_MEMBERS_REQUEST_MESSAGE_HANDLER);
        pipeline.addLast(GROUP_ADD_REQUEST_MESSAGE_HANDLER);
        pipeline.addLast(GROUP_JOIN_REQUEST_MESSAGE_HANDLER);
        pipeline.addLast(GROUP_CHAT_REQUEST_HANDLER);
        pipeline.addLast(GROUP_QUIT_REQUEST_MESSAGE_HANDLER);
        pipeline.addLast(QUIT_REQUEST_HANDLER);
    }

}
