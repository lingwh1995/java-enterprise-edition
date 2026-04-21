package org.bluebridge.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author lingwh
 * @desc 生命周期测试配置类
 * @date 2025/10/25 16:55
 */
@Slf4j
@Configuration
public class LifeCycleTestBean {

    @PostConstruct
    public void init() {
        log.info("当前bean正在初始化......");
    }

    @PreDestroy
    public void destroy() {
        log.info("当前bean即将销毁......");
    }

}
