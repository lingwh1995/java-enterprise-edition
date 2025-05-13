package org.bluebridge.crud.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bluebridge.crud.dao.JdbcTemplateNativeJdcOperate;
import org.bluebridge.crud.domain.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class JdbcTemplateNativeJdcOperateTest {

    private static final Logger logger = LogManager.getLogger(JdbcTemplateNativeJdcOperateTest.class);

    /**
     * 测试初始化数据库表
     */
    @Test
    public void init() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateNativeJdcOperate jdbcTemplateNativeJdcOperate = applicationContext.getBean("jdbcTemplateNativeJdcOperate", JdbcTemplateNativeJdcOperate.class);
        jdbcTemplateNativeJdcOperate.init();
        jdbcTemplateNativeJdcOperate.showAllUser();
    }

    /**
     * 测试清空数据库表数据
     */
    @Test
    public void truncate() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateNativeJdcOperate jdbcTemplateNativeJdcOperate = applicationContext.getBean("jdbcTemplateNativeJdcOperate", JdbcTemplateNativeJdcOperate.class);
        jdbcTemplateNativeJdcOperate.truncate();
        jdbcTemplateNativeJdcOperate.showAllUser();
    }

    /**
     * 测试使用JdbcTemplate提供的原生jdbc操作  查询所有数据
     */
    @Test
    public void testJdbcTemplateNativeJdcOperateQueryAll() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateNativeJdcOperate jdbcTemplateNativeJdcOperate = applicationContext.getBean("jdbcTemplateNativeJdcOperate", JdbcTemplateNativeJdcOperate.class);
        List<User> users = jdbcTemplateNativeJdcOperate.queryAll();
        logger.info(users.toString());
    }

    /**
     * 测试使用JdbcTemplate提供的原生jdbc操作  查询单条记录
     */
    @Test
    public void testJdbcTemplateNativeJdcOperateQueryOneObject() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateNativeJdcOperate jdbcTemplateNativeJdcOperate = applicationContext.getBean("jdbcTemplateNativeJdcOperate", JdbcTemplateNativeJdcOperate.class);
        User user = jdbcTemplateNativeJdcOperate.queryOneObject("001");
        logger.info(user.toString());
    }
}
