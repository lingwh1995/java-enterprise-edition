package org.bluebridge.crud;

import org.bluebridge.crud.dao.JdbcTemplateBatchOperate;
import org.bluebridge.crud.domain.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JdbcTemplateBatchOperateTest {

    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplateBatchOperateTest.class);

    /**
     * 测试初始化数据库表
     */
    @Test
    public void init() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateBatchOperate jdbcTemplateBatchOperate = applicationContext.getBean("jdbcTemplateBatchOperate", JdbcTemplateBatchOperate.class);
        jdbcTemplateBatchOperate.init();
        jdbcTemplateBatchOperate.showAllUser();
    }

    /**
     * 测试清空数据库表数据
     */
    @Test
    public void truncate() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateBatchOperate jdbcTemplateBatchOperate = applicationContext.getBean("jdbcTemplateBatchOperate", JdbcTemplateBatchOperate.class);
        jdbcTemplateBatchOperate.truncate();
    }

    /**
     * 测试批量新增方式一    非常推荐使用
     */
    @Test
    public void testJdbcTemplateBatchOperateInsert1() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateBatchOperate jdbcTemplateBatchOperate = applicationContext.getBean("jdbcTemplateBatchOperate", JdbcTemplateBatchOperate.class);
        //参数为List，泛型为Object[]
        List<Object[]> users = new ArrayList<>();
        users.add(new Object[]{"004","赵六",22});
        users.add(new Object[]{"005", "孙七", 25});
        int[] result = jdbcTemplateBatchOperate.batchInsert1(users);
        logger.info(Arrays.toString(result));
        jdbcTemplateBatchOperate.showAllUser();
    }

    /**
     * 测试批量新增方式二    不推荐使用
     */
    @Test
    public void testJdbcTemplateBatchOperateInsert2() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateBatchOperate jdbcTemplateBatchOperate = applicationContext.getBean("jdbcTemplateBatchOperate", JdbcTemplateBatchOperate.class);
        int[] result = jdbcTemplateBatchOperate.batchInsert2();
        logger.info(Arrays.toString(result));
        jdbcTemplateBatchOperate.showAllUser();
    }

    /**
     * 测试批量新增方式三    一般推荐使用
     *      使用匿名内部类完成批量新增操作
     */
    @Test
    public void testJdbcTemplateBatchOperateInsert3() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateBatchOperate jdbcTemplateBatchOperate = applicationContext.getBean("jdbcTemplateBatchOperate", JdbcTemplateBatchOperate.class);
        List<User> users = new ArrayList<>();
        users.add(new User("006","沈六",18));
        users.add(new User("007","钱七",19));
        users.add(new User("009","周八",20));
        int[] result = jdbcTemplateBatchOperate.batchInsert3(users);
        logger.info(Arrays.toString(result));
        jdbcTemplateBatchOperate.showAllUser();
    }

    /**
     * 测试批量更新方式一    非常推荐
     */
    @Test
    public void testJdbcTemplateBatchOperateUpdate1() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateBatchOperate jdbcTemplateBatchOperate = applicationContext.getBean("jdbcTemplateBatchOperate", JdbcTemplateBatchOperate.class);
        //参数为List，泛型为Object[]
        List<Object[]> users = new ArrayList<>();
        users.add(new Object[]{"张三修改后",28,"001"});
        users.add(new Object[]{"李四修改后",28,"002"});
        users.add(new Object[]{"王五修改后",28,"003"});
        int[] result = jdbcTemplateBatchOperate.batchUpdate1(users);
        logger.info(Arrays.toString(result));
        jdbcTemplateBatchOperate.showAllUser();
    }

    /**
     * 测试批量新增方式二    不推荐使用
     */
    @Test
    public void testJdbcTemplateBatchOperateUpdate2() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateBatchOperate jdbcTemplateBatchOperate = applicationContext.getBean("jdbcTemplateBatchOperate", JdbcTemplateBatchOperate.class);
        int[] result = jdbcTemplateBatchOperate.batchUpdate2();
        logger.info(Arrays.toString(result));
        jdbcTemplateBatchOperate.showAllUser();
    }

    /**
     * 测试批量更新方式三    一般推荐
     *      使用匿名内部类完成批量新增操作
     */
    @Test
    public void testJdbcTemplateBatchOperateUpdate3() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateBatchOperate jdbcTemplateBatchOperate = applicationContext.getBean("jdbcTemplateBatchOperate", JdbcTemplateBatchOperate.class);
        List<User> users = new ArrayList<>();
        users.add(new User("001","张三修改后",28));
        users.add(new User("002","李四修改后",28));
        users.add(new User("003","王五修改后",28));
        int[] result = jdbcTemplateBatchOperate.batchUpdate3(users);
        logger.info(Arrays.toString(result));
        jdbcTemplateBatchOperate.showAllUser();
    }

    /**
     * 测试批量删除方式一    非常推荐
     */
    @Test
    public void testJdbcTemplateBatchOperateDelete1() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateBatchOperate jdbcTemplateBatchOperate = applicationContext.getBean("jdbcTemplateBatchOperate", JdbcTemplateBatchOperate.class);
        //参数为List，泛型为Object[]
        List<Object[]> users = new ArrayList<>();
        users.add(new Object[]{"001"});
        users.add(new Object[]{"002"});
        int[] result = jdbcTemplateBatchOperate.batchDelete1(users);
        logger.info(Arrays.toString(result));
        jdbcTemplateBatchOperate.showAllUser();
    }

    /**
     * 测试批量删除方式二    不推荐使用
     */
    @Test
    public void testJdbcTemplateBatchOperateDelete2() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplateBatchOperate jdbcTemplateBatchOperate = applicationContext.getBean("jdbcTemplateBatchOperate", JdbcTemplateBatchOperate.class);
        int[] result = jdbcTemplateBatchOperate.batchDelete2();
        logger.info(Arrays.toString(result));
        jdbcTemplateBatchOperate.showAllUser();
    }
}
