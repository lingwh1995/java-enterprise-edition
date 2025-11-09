package org.bluebridge.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.server.session.Session;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lingwh
 * @desc 退出请求处理器
 * @date 2025/11/9 19:15
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class QuitRequestHandler extends ChannelInboundHandlerAdapter {

    @Resource
    private Session session;

    /**
     * 退出请求处理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 先输出日志，再解除会话绑定，否则会导致日志中用户名为  null
        log.info("用户 {} 退出登录", session.getUsername(ctx.channel()));
        // 解除会话绑定
        session.unbind(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("用户 {} 异常退出登录, 异常信息: {}", session.getUsername(ctx.channel()), cause.getMessage());
    }

}
