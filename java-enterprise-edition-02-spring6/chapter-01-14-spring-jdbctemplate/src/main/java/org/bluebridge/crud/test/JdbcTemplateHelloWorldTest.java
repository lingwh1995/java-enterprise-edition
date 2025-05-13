package org.bluebridge.crud.test;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 测试从Spring容器中获取JdbcTemplate对象
 */
public class JdbcTemplateHelloWorldTest {

    private static final Logger logger = LogManager.getLogger(JdbcTemplateHelloWorldTest.class);

    /**
     * 测试从Spring容器中获取JdbcTemplate对象
     */
    @Test
    public void testGetJdbcTemplateFromSpring() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplate jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
        logger.info(jdbcTemplate.toString());
    }
}
