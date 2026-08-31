package org.bluebridge.zookeeper.demo_01_curator.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Curator 配置类
 *
 * @author lingwh
 * @date 2026/8/31 16:00
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(CuratorProperties.class)
public class CuratorConfig {

    /**
     * 注入 CuratorFramework 客户端实例
     *
     * 相比原生 ZooKeeper 客户端，Curator 内置了连接重试策略（ExponentialBackoffRetry）、
     * 自动重连、session 恢复等机制，无需手动等待连接建立
     */
    @Bean(destroyMethod = "close")
    public CuratorFramework curatorFramework(CuratorProperties properties) throws Exception {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(
                properties.getBaseSleepTime(), properties.getMaxRetries());
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(properties.getConnectString())
                .sessionTimeoutMs(properties.getSessionTimeout())
                .connectionTimeoutMs(properties.getConnectionTimeout())
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        if (!client.blockUntilConnected(properties.getSessionTimeout(), TimeUnit.MILLISECONDS)) {
            client.close();
            throw new IOException("Curator 连接超时: " + properties.getConnectString());
        }
        log.info("执行链路 - Curator 连接成功: {}", properties.getConnectString());
        return client;
    }
}
