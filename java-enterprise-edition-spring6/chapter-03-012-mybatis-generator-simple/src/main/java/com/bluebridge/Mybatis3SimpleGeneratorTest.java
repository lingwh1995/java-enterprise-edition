package com.bluebridge;

import com.bluebridge.domain.Car;
import com.bluebridge.mapper.CarMapper;
import com.bluebridge.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * mybatis 逆向工程简单版测试
 */
public class Mybatis3SimpleGeneratorTest {

    @Test
    public void test() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectAll();
        System.out.println("cars = " + cars);
        sqlSession.close();
    }
}
