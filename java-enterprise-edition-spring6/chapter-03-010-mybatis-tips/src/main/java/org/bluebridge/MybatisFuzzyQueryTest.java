package org.bluebridge;

import org.apache.ibatis.session.SqlSession;
import org.bluebridge.domain.Car;
import org.bluebridge.mapper.CarMapper;
import org.bluebridge.utils.SqlSessionUtil;
import org.junit.Test;

import java.util.List;

/**
 * mybatis 模糊查询测试
 */
public class MybatisFuzzyQueryTest {

    /**
     * 模糊查询实现方式一：使用mysql的concat()函数实现模糊查询
     */
    @Test
    public void testFuzzyQueryUseMysqlConcat() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectCarByBrandFuzzyQueryUseMysqlConcat("宝马");
        System.out.println("cars = " + cars);
        sqlSession.close();
    }

    /**
     * 模糊查询实现方式二：使用 双引号 + #{} 拼接实现模糊查询
     */
    @Test
    public void testFuzzyQueryUseDoubleQuotationMarkAndHashSymbolConcat() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectCarByBrandFuzzyQueryUseDoubleQuotationMarkAndHashSymbolConcat("奔驰");
        System.out.println("cars = " + cars);
        sqlSession.close();
    }

    /**
     * 模糊查询实现方式三：使用 单引号 + || + #{} 拼接实现模糊查询
     */
    @Test
    public void testFuzzyQueryUseSingleQuotationMarkAndHashSymbolConcat() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectCarByBrandFuzzyQueryUseSingleQuotationMarkAndHashSymbolConcat("奔驰");
        System.out.println("cars = " + cars);
        sqlSession.close();
    }

    /**
     * 模糊查询实现方式四：使用 mybatis的bind标签 拼接实现模糊查询(实际上相当于传入的时候就把参数拼接好了)
     */
    @Test
    public void testFuzzyQueryUseMybatisBindTagConcat() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectCarByBrandFuzzyQueryUseMybatisBindTagConcat("宝马");
        System.out.println("cars = " + cars);
        sqlSession.close();
    }

    /**
     * 模糊查询实现方式五：直接传入拼接好的模糊查询参数 实现模糊查询
     */
    @Test
    public void testFuzzyQueryUseCompleteFuzzyQueryParam() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectCarByBrandFuzzyQueryUseCompleteFuzzyQueryParam("%奔驰%");
        System.out.println("cars = " + cars);
        sqlSession.close();
    }
}
