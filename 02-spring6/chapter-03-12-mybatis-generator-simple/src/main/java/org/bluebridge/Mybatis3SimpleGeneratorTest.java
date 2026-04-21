package org.bluebridge;

import org.bluebridge.domain.Car;
import org.bluebridge.mapper.CarMapper;
import org.bluebridge.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * mybatis 逆向工程简单版测试
 */
public class Mybatis3SimpleGeneratorTest {

    @Test
    public void testMybatis3SimpleGenerator() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectAll();
        System.out.println("cars = " + cars);
        sqlSession.close();
    }
}
