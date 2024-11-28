package org.bluebridge;

import org.apache.ibatis.session.SqlSession;
import org.bluebridge.domain.Car;
import org.bluebridge.mapper.CarMapper;
import org.bluebridge.utils.SqlSessionUtil;
import org.junit.Test;

/**
 * Mybatis 插入数据时对主键的处理
 */
public class MybatisDealPrimarykeyTest {

    /**
     * Mysql插入数据时设置主键方式一(写法一): 使用Mysql自动设置主键，sql语句中不用出现主键
     *      不推荐：插入数据成功后无法读取到Mysql生成的主键
     */
    @Test
    public void testMysqlAutoSetPrimaryKeyWhenInsert_1() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(null, "9199", "凯美瑞", 32.0, "2020-11-12", "电车");
        mapper.insertUseMysqlAutoSetPrimaryKeyWhenInsert_1(car);
        System.out.println("car.getId() = " + car.getId());
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * Mysql插入数据时设置主键方式一(写法二): 使用Mysql自动设置主键，sql语句中不用出现主键
     *      不推荐：插入数据成功后无法读取到Mysql生成的主键
     */
    @Test
    public void testMysqlAutoSetPrimaryKeyWhenInsert_2() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(null, "8596", "卡罗拉", 32.0, "2020-11-12", "电车");
        mapper.insertUseMysqlAutoSetPrimaryKeyWhenInsert_2(car);
        System.out.println("car.getId() = " + car.getId());
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * Mysql插入数据时设置主键方式二(写法一): 使用Mybatis读取Mysql生成的主键，在sql语句中使用，sql语句中要出现主键
     *      推荐：插入数据成功后可以读取到Mysql生成的主键
     */
    @Test
    public void testUseMybatisReadMysqlGeneratedPrimaryKeyWhenInsert_1() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(null, "8635", "吉利帝豪", 32.0, "2020-11-12", "电车");
        mapper.insertUseMybatisReadMysqlGeneratedPrimaryKeyWhenInsert_1(car);
        System.out.println("car.getId() = " + car.getId());
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * Mysql插入数据时设置主键方式二(写法二): 使用Mybatis读取Mysql生成的主键，在sql语句中使用，sql语句中要出现主键
     *      推荐：插入数据成功后可以读取到Mysql生成的主键
     */
    @Test
    public void testUseMybatisReadMysqlGeneratedPrimaryKeyWhenInsert_2() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(null, "9731", "比亚迪秦", 32.0, "2020-11-12", "电车");
        mapper.insertUseMybatisReadMysqlGeneratedPrimaryKeyWhenInsert_2(car);
        System.out.println("car.getId() = " + car.getId());
        sqlSession.commit();
        sqlSession.close();
    }
}
