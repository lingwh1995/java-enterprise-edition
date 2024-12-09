package org.bluebridge.noxml.jdbctemplate;

import org.bluebridge.noxml.jdbctemplate.dao.UserDao;
import org.bluebridge.noxml.jdbctemplate.domain.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringTest.class);

    /**
     * 测试Spring的全注解开发
     */
    @Test
    public void testNoXml() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);
        User user = userDao.getUserById("001");
        logger.info(user.toString());
    }

}
