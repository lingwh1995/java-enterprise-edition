package org.bluebridge.zookeeper.demo_01_helloworld.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * ZooKeeper 配置类
 *
 * @author lingwh
 * @date 2026/8/31 15:30
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(ZookeeperProperties.class)
public class ZookeeperConfig {

    /**
     * 注入 ZooKeeper 客户端实例
     *
     * ZooKeeper 连接是异步的，这里用 CountDownLatch 等待连接建立成功后再返回
     */
    @Bean(destroyMethod = "close")
    public ZooKeeper zooKeeper(ZookeeperProperties properties) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper(properties.getConnectString(), properties.getSessionTimeout(), event -> {
            log.info("执行链路 - ZooKeeper 默认 Watcher 收到事件: state={}, type={}, path={}",
                    event.getState(), event.getType(), event.getPath());
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                latch.countDown();
            }
        });
        if (!latch.await(properties.getSessionTimeout(), TimeUnit.MILLISECONDS)) {
            zooKeeper.close();
            throw new IOException("ZooKeeper 连接超时: " + properties.getConnectString());
        }
        log.info("执行链路 - ZooKeeper 连接成功: {}", properties.getConnectString());
        return zooKeeper;
    }
}
