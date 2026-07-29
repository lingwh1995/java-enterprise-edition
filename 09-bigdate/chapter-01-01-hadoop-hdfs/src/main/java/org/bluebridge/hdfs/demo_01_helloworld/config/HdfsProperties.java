package org.bluebridge.hdfs.demo_01_helloworld.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * HDFS 配置属性
 */
@Data
@ConfigurationProperties(prefix = "hdfs")
public class HdfsProperties {

    /**
     * HDFS 地址
     */
    private String uri = "hdfs://localhost:9000";

    /**
     * HDFS 用户
     */
    private String user = "hadoop";
}
