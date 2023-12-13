package com.dragonsoft;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * 自定义redis组件监控返回信息
 * 测试url:
 *      http://localhost:8080/actuator/health
 * @author ronin
 */
@Component
public class MyRedisReactiveHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        return Health.down().withDetail("msg","Redis启动异常").build();
    }
}
