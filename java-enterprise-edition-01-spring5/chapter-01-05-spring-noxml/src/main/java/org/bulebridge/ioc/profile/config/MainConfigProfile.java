package org.bulebridge.ioc.profile.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Profile:
 *      Spring为我们提供的可以根据当前环境,动态的期货和切换一系列组件的功能
 * 开发环境:测试环境、生产环境
 * 数据源:A/B/C
 * @Profile
 * @author ronin
 */
@Profile("default")
@PropertySource("classpath:/db.properties")
@Configuration
public class MainConfigProfile implements EmbeddedValueResolverAware {

    @Value("${home.develop.mysql.jdbc.password}")
    private String password;

    private StringValueResolver resolver;

    /**
     * 测试数据源
     * @return
     * @throws PropertyVetoException
     */
    @Profile("test")
    @Bean(name="testDataSource")
    public DataSource dataSourceTest(@Value("${home.develop.mysql.jdbc.username}")String username) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl("${jdbc:mysql://192.168.1.102:3306/mybatis}");
        String driver = resolver.resolveStringValue("${home.develop.mysql.jdbc.driver}");
        dataSource.setDriverClass(driver);
        return dataSource;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * 开发数据源
     * @return
     * @throws PropertyVetoException
     */
    @Profile("default")
    @Bean
    public DataSource dataSourceDefault() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setJdbcUrl("jdbc:mysql://192.168.0.38:3306");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

}
