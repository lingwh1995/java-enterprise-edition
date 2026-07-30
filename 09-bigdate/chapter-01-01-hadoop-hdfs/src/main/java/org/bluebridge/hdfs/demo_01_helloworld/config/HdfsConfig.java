package org.bluebridge.hdfs.demo_01_helloworld.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.net.URI;

/**
 * HDFS 配置类
 *
 * @author lingwh
 * @date 2025/7/10 10:02
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(HdfsProperties.class)
public class HdfsConfig {

    /**
     * 注入 HDFS FileSystem
     */
    @Bean
    public FileSystem fileSystem(HdfsProperties hdfsProperties) throws Exception {
        Configuration configuration = new Configuration();
        return FileSystem.get(new URI(hdfsProperties.getUri()), configuration, hdfsProperties.getUser());
    }
}