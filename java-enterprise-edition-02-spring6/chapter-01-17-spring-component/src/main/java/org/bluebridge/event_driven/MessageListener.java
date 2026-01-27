package org.bluebridge.event_driven;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger log = LogManager.getLogger(MessageListener.class);

    /**
     * 监听注册事件
     * @param event
     */
    @EventListener
    public void sendEmail(UserRegisterEvent event) {
        log.info("邮件服务 开始处理注册事件......");
        log.info("邮件服务 给 {} 发送欢迎邮件", event.getUsername());
    }

    /**
     * Spring环境开启异步需要在 applicationContext-event_driven.xml 中配置
     * SpringBoot 环境开启异步需要在主启动类中添加 @EnableAsync
     * @param event
     */
    @Async // 如果想异步执行，不阻塞主流程，加这个注解
    @EventListener
    public void addScore(UserRegisterEvent event) {
        log.info("积分服务 给 %s 增加 100 积分", event.getUsername());
    }

}