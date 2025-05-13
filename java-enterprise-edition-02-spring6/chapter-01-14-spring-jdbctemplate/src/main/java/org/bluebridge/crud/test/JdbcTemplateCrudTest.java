package org.bluebridge.crud.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bluebridge.crud.dao.JdbcTemplateCrud;
import org.bluebridge.crud.domain.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 测试JdbcTemplate增删改查
 */
public class JdbcTemplateCrudTest {

    private static final Logger logger = LogManager.getLogger(JdbcTemplateCrudTest.class);

    /**
     * 初始化数据库表
     */
    @Test
    public void init() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateCrud jdbcTemplateCrud = applicationContext.getBean("jdbcTemplateCrud", JdbcTemplateCrud.class);
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("001","张三",18));
        users.add(new User("002","李四",19));
        users.add(new User("003","王五",20));
        jdbcTemplateCrud.init(users);
        jdbcTemplateCrud.showAllUser();
    }

    /**
     * 清空数据库表数据
     */
    @Test
    public void truncate() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateCrud jdbcTemplateCrud = applicationContext.getBean("jdbcTemplateCrud", JdbcTemplateCrud.class);
        jdbcTemplateCrud.truncate();
    }

    /**
     * 测试新增记录
     */
    @Test
    public void testJdbcTemplateCrudInsert() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateCrud jdbcTemplateCrud = applicationContext.getBean("jdbcTemplateCrud", JdbcTemplateCrud.class);
        int result = jdbcTemplateCrud.addUser(new User("004", "张三", 18));
        logger.info("受影响的记录条数:" + result);
        jdbcTemplateCrud.showAllUser();
    }

    /**
     * 测试删除数据
     */
    @Test
    public void testJdbcTemplateCrudDelete() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateCrud jdbcTemplateCrud = applicationContext.getBean("jdbcTemplateCrud", JdbcTemplateCrud.class);
        int result = jdbcTemplateCrud.deleteUserById("004");
        logger.info("受影响的记录条数:" + result);
        jdbcTemplateCrud.showAllUser();
    }

    /**
     * 测试修改数据
     */
    @Test
    public void testJdbcTemplateCrudUpdate() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateCrud jdbcTemplateCrud = applicationContext.getBean("jdbcTemplateCrud", JdbcTemplateCrud.class);
        int result = jdbcTemplateCrud.updateUser(new User("001","张三修改后",28));
        logger.info("受影响的记录条数:" + result);
        jdbcTemplateCrud.showAllUser();
    }

    /**
     * 测试查询一条记录
     */
    @Test
    public void testJdbcTemplateCrudQueryOne() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateCrud jdbcTemplateCrud = applicationContext.getBean("jdbcTemplateCrud", JdbcTemplateCrud.class);
        User user = jdbcTemplateCrud.queryOneObject("001");
        logger.info(user.toString());
    }

    /**
     * 测试查询一条或者多条记录
     */
    @Test
    public void testJdbcTemplateCurdQueryMoreThanOne() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateCrud jdbcTemplateCrud = applicationContext.getBean("jdbcTemplateCrud", JdbcTemplateCrud.class);
        List<User> users = jdbcTemplateCrud.queryMoreThanOneObject(20);
        logger.info(users.toString());
    }

    /**
     * 测试查询所有记录
     */
    @Test
    public void testJdbcTemplateCrudQueryAll() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateCrud jdbcTemplateCrud = applicationContext.getBean("jdbcTemplateCrud", JdbcTemplateCrud.class);
        List<User> users = jdbcTemplateCrud.queryAll();
        logger.info(users.toString());
    }

    /**
     * 测试查询一个值
     */
    @Test
    public void testJdbcTemplateCrudQueryOneValue() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateCrud jdbcTemplateCrud = applicationContext.getBean("jdbcTemplateCrud", JdbcTemplateCrud.class);
        Integer count = jdbcTemplateCrud.queryOneValue_1();
        logger.info("总记录数:" + count);
        String nameFieldValue = jdbcTemplateCrud.queryOneValue_2("001");
        logger.info("当id=001时name字段的值:" + nameFieldValue);
    }

    /**
     * 测试查询一列数据
     */
    @Test
    public void testJdbcTemplateCrudQuerySingleColumn() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateCrud jdbcTemplateCrud = applicationContext.getBean("jdbcTemplateCrud", JdbcTemplateCrud.class);
        List<String> names = jdbcTemplateCrud.querySingleColumn();
        logger.info("单列数据:" + names.toString());
    }

    /**
     * 查询一个对象，把查询到的结果封装到Map中
     */
    @Test
    public void testJdbcTemplateCrudQueryOneObjectUseRowMapper() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateCrud jdbcTemplateCrud = applicationContext.getBean("jdbcTemplateCrud", JdbcTemplateCrud.class);
        Map<String, String> userInfo = jdbcTemplateCrud.queryOneObjectUseRowMapper("001");
        logger.info("userInfo:" + userInfo);
    }
}
