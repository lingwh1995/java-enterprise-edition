package org.bluebridge.zookeeper.demo_01_curator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Curator 配置属性
 *
 * @author lingwh
 * @date 2026/8/31 16:00
 */
@Data
@ConfigurationProperties(prefix = "curator")
public class CuratorProperties {

    /**
     * ZooKeeper 连接地址，集群用逗号分隔
     */
    private String connectString = "localhost:2181";

    /**
     * 会话超时时间(毫秒)
     */
    private int sessionTimeout = 60000;

    /**
     * 连接超时时间(毫秒)
     */
    private int connectionTimeout = 15000;

    /**
     * 重试基础间隔(毫秒)，指数退避策略的初始值
     */
    private int baseSleepTime = 1000;

    /**
     * 最大重试次数
     */
    private int maxRetries = 3;
}
