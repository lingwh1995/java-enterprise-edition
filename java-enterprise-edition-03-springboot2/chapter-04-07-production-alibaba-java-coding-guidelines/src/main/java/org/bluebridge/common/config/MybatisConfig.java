package org.bluebridge.common.config;

import org.bluebridge.common.interceptor.SqlPerformanceInterceptor;
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
    public SqlPerformanceInterceptor sqlCostInterceptor() {
        SqlPerformanceInterceptor interceptor = new SqlPerformanceInterceptor();
        Properties properties = new Properties();
        // 设置慢查询阈值为500ms
        properties.setProperty("longQueryTime", "1000");
        // 是否显示原始SQL
        properties.setProperty("showOriginalSql", Boolean.TRUE.toString());
        // 是否显示格式化后的SQL
        properties.setProperty("showFormattedSql", Boolean.FALSE.toString());
        // 是否高亮显示SQL
        properties.setProperty("showHighlightSql", Boolean.TRUE.toString());
        interceptor.setProperties(properties);
        return interceptor;
    }

}