package org.bluebridge.event_driven;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author lingwh
 * @desc
 * @date 2026/1/10 12:32
 */
@Component
public class MessageListener {

    /**
     * 监听注册事件
     * @param event
     */
    @EventListener
    public void sendEmail(UserRegisterEvent event) {
        System.out.println("邮件服务 开始处理注册事件......");
        System.out.println(String.format("邮件服务 给 %s 发送欢迎邮件", event.getUsername()));
    }

    @Async // 如果想异步执行，不阻塞主流程，加这个注解
    @EventListener
    public void addScore(UserRegisterEvent event) {
        System.out.println(String.format("积分服务 给 %s 增加 100 积分", event.getUsername()));
    }

}