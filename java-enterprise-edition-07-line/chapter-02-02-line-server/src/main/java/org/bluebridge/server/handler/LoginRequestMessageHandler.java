package org.bluebridge.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.domain.LoginRequestMessage;
import org.bluebridge.domain.LoginResponseMessage;
import org.bluebridge.server.service.IUserService;
import org.bluebridge.server.session.Session;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lingwh
 * @desc 登录请求消息处理器
 * @date 2025/10/30 20:11
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {

    @Resource
    private Session session;

    @Resource
    private IUserService userService;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String password = msg.getPassword();
        boolean isLogin = userService.login(username, password);
        LoginResponseMessage message = null;
        if(isLogin) {
            log.info("用户 {} 登录成功", username);
            message = new LoginResponseMessage(true, "登录成功");
            // 登录成功后，将channel与用户名绑定
            session.bind(ctx.channel(), username);
        } else {
            log.info("用户 {} 登录失败", username);
            message = new LoginResponseMessage(false, "用户名或密码错误");
        }
        ctx.channel().writeAndFlush(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("用户 {} 登录异常", session.getUsername(ctx.channel()), cause);
        super.exceptionCaught(ctx, cause);
    }

}
