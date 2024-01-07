package org.bluebridge.datasource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class SpringTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringTest.class);

    /**
     * 测试Spring框架为我们提供的DataSource实现
     */
    @Test
    public void testDriverManagerDataSource() throws SQLException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-datasource.xml");
        DriverManagerDataSource dataSource = applicationContext.getBean("dataSource", DriverManagerDataSource.class);
        Connection connection = dataSource.getConnection();
        logger.info("Spring提供的数据源实现" + connection.toString());
    }
}
