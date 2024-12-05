package com.bluebridge;

import org.bluebridge.domain.Car;
import org.bluebridge.domain.CarExample;
import org.bluebridge.mapper.CarMapper;
import com.bluebridge.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * mybatis 逆向工程简单版测试
 */
public class Mybatis3ComplexGeneratorTest {

    @Test
    public void testMybatis3ComplexGenerator() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        // 执行查询
        // 1. 查询一个
        Car car = mapper.selectByPrimaryKey(4L);
        System.out.println(car);

        // 2. 查询所有（selectByExample，根据条件查询，如果条件是null表示没有条件。）
        List<Car> cars = mapper.selectByExample(null);
        cars.forEach(car1 -> System.out.println(car1));
        System.out.println("=========================================");

        // 3. 按照条件进行查询
        // QBC 风格：Query By Criteria 一种查询方式，比较面向对象，看不到sql语句。
        // QBC 面向对象查询
        // 封装条件，通过CarExample对象来封装查询条件
        CarExample carExample = new CarExample();
        // 调用carExample.createCriteria()方法来创建查询条件
        // 继续向后调用方法追加查询条件
        carExample.createCriteria()
                .andBrandLike("帕萨特") // 模糊查询
                .andGuidePriceGreaterThan(new BigDecimal(20.0)); // 并且价格大于20
        // 添加or
        // 上面添加的为and条件，or()开始添加or条件
        // (... and ... 前面的查询条件) or (...) 生成后的查询条件
        carExample.or().andCarTypeEqualTo("燃油车");
        // 执行查询
        // 更加封装的条件进行查询
        List<Car> cars2 = mapper.selectByExample(carExample);
        cars2.forEach(car2 -> System.out.println(car2));

        sqlSession.close();
    }
}
