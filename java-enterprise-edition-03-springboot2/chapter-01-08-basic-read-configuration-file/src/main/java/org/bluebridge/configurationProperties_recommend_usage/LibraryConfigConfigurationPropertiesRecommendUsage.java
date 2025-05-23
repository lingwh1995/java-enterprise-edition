package org.bluebridge.configurationProperties_recommend_usage;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LibraryConfigConfigurationPropertiesRecommendUsage {

    @Bean
    @ConfigurationProperties(prefix ="spring.datasource")
    public DataSource dataSource(){
        return new DruidDataSource();
    }
}
