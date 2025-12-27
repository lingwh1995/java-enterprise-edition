package org.bluebridge.common.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author lingwh
 * @desc 启动初始化配置（所有的初始化配置工作都在这里面完成）
 * @date 2025/12/9 10:53
 */
@Configuration
public class StartupConfig {

    @PostConstruct
    public void init() {

    }

}
