package org.bluebridge.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.domain.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author lingwh
 * @desc
 * @date 2025/10/30 20:56
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {

    CountDownLatch WAIT_FOR_LOGIN = new CountDownLatch(1);
    AtomicBoolean EXIT = new AtomicBoolean(false);
    AtomicBoolean LOGIN = new AtomicBoolean(false);

    /**
     * 当通道激活时调用，发送登录消息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        new Thread(() -> {
            log.info("请输入用户名:");
            Scanner scanner = new Scanner(System.in);
            String username = scanner.nextLine();
            if(EXIT.get()){
                return;
            }
            log.info("请输入密码:");
            String password = scanner.nextLine();
            if(EXIT.get()){
                return;
            }
            // 构造消息对象
            LoginRequestMessage message = new LoginRequestMessage(username, password);
            log.info("登录消息：{}", message);
            // 发送消息
            ctx.writeAndFlush(message);
            log.info("已发送登录消息，等待后续操作......");
            try {
                // 停止等待
                WAIT_FOR_LOGIN.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // 如果登录失败，退出程序
            if(!LOGIN.get()){
                log.info("登录失败，退出程序");
                ctx.channel().close();
                return;
            }

            while (true) {
                System.out.println("==================================");
                System.out.println("send [username] [content]");
                System.out.println("gcreate [group name] [m1,m2,m3...]");
                System.out.println("gmembers [group name]");
                System.out.println("gadd [group name]");
                System.out.println("gjoin [group]");
                System.out.println("gsend [group name] [content]");
                System.out.println("gquit [group name]");
                System.out.println("quit");
                System.out.println("==================================");
                String command = null;
                try {
                    command = scanner.nextLine();
                } catch (Exception e) {
                    break;
                }
                if(EXIT.get()){
                    return;
                }
                String[] s = command.split(" ");
                switch (s[0]){
                    case "send":
                        ctx.writeAndFlush(new ChatRequestMessage(username, s[1], s[2]));
                        break;
                    case "gcreate":
                        Set<String> set = new HashSet<>(Arrays.asList(s[2].split(",")));
                        // 加入自己
                        set.add(username);
                        ctx.writeAndFlush(new GroupCreateRequestMessage(s[1], set));
                        break;
                    case "gmembers":
                        ctx.writeAndFlush(new GroupMembersRequestMessage(s[1]));
                        break;
                    case "gadd":
                        ctx.writeAndFlush(new GroupAddRequestMessage(s[1], s[2]));
                        break;
                    case "gjoin":
                        ctx.writeAndFlush(new GroupJoinRequestMessage(s[1], username));
                        break;
                    case "gsend":
                        //ctx.writeAndFlush(new GroupChatRequestMessage(username, s[1], s[2]));
                        break;
                    case "gquit":
                        //ctx.writeAndFlush(new GroupQuitRequestMessage(username, s[1]));
                        break;
                    case "quit":
                        ctx.channel().close();
                        return;
                }
            }
        }).start();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("{}", msg);
        if(msg instanceof LoginResponseMessage){
            LoginResponseMessage loginResponseMessage = (LoginResponseMessage) msg;
            LOGIN.set(loginResponseMessage.isSuccess());
        }

        // 计数器减一
        WAIT_FOR_LOGIN.countDown();
    }

    // 在连接断开时触发
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接已经断开，按任意键退出......");
        EXIT.set(true);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("客户端发生异常", cause);
        ctx.close();
    }

}
