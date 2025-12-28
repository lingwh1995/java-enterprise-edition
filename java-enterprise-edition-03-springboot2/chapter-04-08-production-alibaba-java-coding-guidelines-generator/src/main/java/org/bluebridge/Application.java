package org.bluebridge;

import org.bluebridge.system.constant.SqlConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.xa8bit.*.mapper")
@SpringBootApplication
public class Application {

    static {
        // 直接设置系统属性，这是 P6Spy 读取配置的最高优先级
        System.setProperty("p6spy.config.logMessageFormat", SqlConstants.P6SPY_FORMATTER_CLASS);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
