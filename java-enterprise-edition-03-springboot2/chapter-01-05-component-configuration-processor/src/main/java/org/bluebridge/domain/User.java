package org.bluebridge.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 第一步：
 *  在pom中引入 spring-boot-configuration-processor 这个依赖
 * 第二步：
 *  在实体类上配置 @ConfigurationProperties(prefix = "user")
 * 第三步：
 *  编译代码，在/target/classes/META-INF/下会生成 spring-configuration-metadata.json
 * 第四步：
 *  在application.yml或application.properties中使用时可以看见有相应的提示，方便我们使用
 */
@ConfigurationProperties(prefix = "user")
@Component
public class User {
    public String id;
    private String name;
    private String password;
    private String ip;
    private int port;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
