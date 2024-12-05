package org.bluebridge;

import org.bluebridge.domain.Car;
import org.bluebridge.mapper.CarMapper;
import org.bluebridge.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;


/**
 * mybatis 注解驱动开发测试
 */
public class MybatisAnnotationDrivenTest {

    @Test
    public void testMybatisAnnotationDriven() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        Car car = mapper.selectById(4l);
        System.out.println("car = " + car);
        sqlSession.close();
    }
}
