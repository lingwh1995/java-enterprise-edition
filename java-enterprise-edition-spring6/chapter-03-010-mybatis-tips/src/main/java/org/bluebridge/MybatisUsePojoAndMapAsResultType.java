package org.bluebridge;

import org.apache.ibatis.session.SqlSession;
import org.bluebridge.domain.Car;
import org.bluebridge.mapper.CarMapper;
import org.bluebridge.utils.SqlSessionUtil;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Mybatis使用pojo和map封装查询结果
 */
public class MybatisUsePojoAndMapAsResultType {

    /**
     * 使用Pojo封装查询结果
     */
    @Test
    public void testUsePojoAsResultType() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = mapper.selectCarByIdUsePojoAsResultType(1l);
        System.out.println("car = " + car);
        List<Car> cars = mapper.selectAllCarsUsePojoAsResultType();
        System.out.println("cars = " + cars);
        sqlSession.close();
    }

    /**
     * 使用map封装查询结果
     */
    @Test
    public void testUseMapAsResultType() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Map<String, String> car = mapper.selectCarByIdUseMapAsResultType(1l);
        System.out.println("car = " + car);
        List<Map<String, String>> cars = mapper.selectAllCarsUseMapAsResultType();
        System.out.println("cars = " + cars);
        sqlSession.close();
    }
}
