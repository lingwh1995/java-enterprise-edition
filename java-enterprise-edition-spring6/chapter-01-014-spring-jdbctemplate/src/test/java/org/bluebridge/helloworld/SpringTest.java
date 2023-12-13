package org.bluebridge.helloworld;

import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 测试从Spring容器中获取JdbcTemplate对象
 */
public class SpringTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringTest.class);

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
