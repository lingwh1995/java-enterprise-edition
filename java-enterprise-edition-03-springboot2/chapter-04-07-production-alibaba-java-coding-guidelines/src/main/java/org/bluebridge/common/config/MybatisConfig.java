package org.bluebridge.common.config;

import org.bluebridge.common.interceptor.SqlCostInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author lingwh
 * @desc MyBatis配置类
 * @date 2025/12/19 11:23
 */
@Configuration
public class MybatisConfig {

    /**
     * 注册SQL执行耗时拦截器
     * @return
     */
    @Bean
    public SqlCostInterceptor sqlCostInterceptor() {
        SqlCostInterceptor interceptor = new SqlCostInterceptor();
        Properties properties = new Properties();
        // 设置慢查询阈值为500ms
        properties.setProperty("longQueryTime", "500");
        // 设置是否显示SQL参数为
        properties.setProperty("showParameters", "true");
        interceptor.setProperties(properties);
        return interceptor;
    }

}