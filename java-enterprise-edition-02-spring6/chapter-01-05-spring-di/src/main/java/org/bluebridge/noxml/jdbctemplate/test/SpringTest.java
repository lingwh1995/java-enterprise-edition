package org.bluebridge.noxml.jdbctemplate.test;

import org.bluebridge.annotation.resource.dao.StudentDao;
import org.bluebridge.noxml.jdbctemplate.config.SpringConfig;
import org.bluebridge.noxml.jdbctemplate.dao.UserDao;
import org.bluebridge.noxml.jdbctemplate.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTest {

    private static final Logger logger = LogManager.getLogger(StudentDao.class);

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
