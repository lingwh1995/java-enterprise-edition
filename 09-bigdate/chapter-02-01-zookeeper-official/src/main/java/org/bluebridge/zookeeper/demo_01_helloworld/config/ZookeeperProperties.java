package org.bluebridge.zookeeper.demo_01_helloworld.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ZooKeeper 配置属性
 *
 * @author lingwh
 * @date 2026/8/31 15:30
 */
@Data
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperProperties {

    /**
     * ZooKeeper 连接地址，集群用逗号分隔
     */
    private String connectString = "localhost:2181";

    /**
     * 会话超时时间(毫秒)
     */
    private int sessionTimeout = 30000;
}
