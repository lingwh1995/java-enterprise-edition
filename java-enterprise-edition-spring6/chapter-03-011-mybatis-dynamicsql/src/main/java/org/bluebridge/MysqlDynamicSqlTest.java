package org.bluebridge;

import org.apache.ibatis.session.SqlSession;
import org.bluebridge.domain.Car;
import org.bluebridge.mapper.ICarMapper;
import org.bluebridge.utils.SqlSessionUtil;
import org.junit.Test;

import java.util.List;


/**
 * Mybatis动态sql
 */
public class MysqlDynamicSqlTest {

    @Test
    public void fun() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ICarMapper mapper = sqlSession.getMapper(ICarMapper.class);

        // 假设三个条件都是空
        List<Car> cars = mapper.selectByMultiCondition("比亚迪", 2.0, "新能源");
        System.out.println("cars = " + cars);

        // 假设三个条件都是空
        cars = mapper.selectByMultiCondition("", null, "");
        System.out.println("cars = " + cars);

        // 假设后两个条件不为空，第一个条件为空
        cars = mapper.selectByMultiCondition("", 2.0, "新能源");
        System.out.println("cars = " + cars);
        sqlSession.close();
    }
}
