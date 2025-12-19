package org.bluebridge.common.config;

import org.bluebridge.common.interceptor.SoftDeleteInterceptor;
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
     * 注册逻辑删除拦截器
     */
    @Bean
    public SoftDeleteInterceptor logicDeleteInterceptor() {
        SoftDeleteInterceptor interceptor = new SoftDeleteInterceptor();
        Properties properties = new Properties();
        properties.setProperty("softDeleteColumn", "is_deleted");
        properties.setProperty("softDeleteValue", "0");
        interceptor.setProperties(properties);
        return interceptor;
    }

    /**
     * 注册SQL执行耗时拦截器
     * @return
     */
    @Bean
    public SqlCostInterceptor sqlCostInterceptor() {
        SqlCostInterceptor interceptor = new SqlCostInterceptor();
        Properties properties = new Properties();
        properties.setProperty("slowQueryThreshold", "500"); // 500ms以上视为慢查询
        properties.setProperty("showParameters", "true");
        interceptor.setProperties(properties);
        return interceptor;
    }

}