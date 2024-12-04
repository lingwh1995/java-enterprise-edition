package org.bluebridge;

import org.apache.ibatis.session.SqlSession;
import org.bluebridge.domain.Car;
import org.bluebridge.domain.Log;
import org.bluebridge.mapper.ICarMapper;
import org.bluebridge.mapper.LogMapper;
import org.bluebridge.utils.SqlSessionUtil;
import org.junit.Test;

import java.util.List;

/**
 * mybatis Tips
 *
 */
public class MybatisHashSymbolAndDollarSymbolTest {

    /**
     * 测试使用 #{} 设置参数和使用 ${} 设置参数的不同之处，观察控制台输出的sql可以发现
     *  1.使用 #{} 设置参数有预编译的效果，可以避免SQL注入的风险
     *  2.使用 ${} 设置参数没有预编译的效果，存在SQL注入的风险
     *  3.优先使用 #{} 设置参数，这是原则。避免SQL注入的风险。#{}不能实现在考虑${}
     */
    @Test
    public void testSetParamsUseHashSymbolOrDollarSymbol() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ICarMapper mapper = sqlSession.getMapper(ICarMapper.class);
        System.out.println("----------------------------------------------------------------------");
        // 使用 # 设置参数
        Car car1 = mapper.selectCarByIdSetParamsUseHashSymbol(1l);
        System.out.println("car1 = " + car1);
        System.out.println("----------------------------------------------------------------------");
        Car car2 = mapper.selectCarByIdSetParamsUseDollarSymbol(1l);
        // 使用 $ 设置参数
        System.out.println("car2 = " + car2);
        System.out.println("----------------------------------------------------------------------");
        sqlSession.close();
    }

    /**
     * 测试使用 ${} 设置排序条件 (${}常见使用场景一：设置排序条件)
     */
    @Test
    public void testSetSortOrderUseDollarSymbol() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ICarMapper mapper = sqlSession.getMapper(ICarMapper.class);
        System.out.println("----------------------------------------------------------------------");
        List<Car> carsAsc = mapper.selectAllCarsSetParamsUseDollarSymbol("asc");
        System.out.println("carsAsc = " + carsAsc);
        System.out.println("----------------------------------------------------------------------");
        List<Car> carsDesc = mapper.selectAllCarsSetParamsUseDollarSymbol("desc");
        System.out.println("carsDesc = " + carsDesc);
        System.out.println("----------------------------------------------------------------------");
        sqlSession.close();
    }

    /**
     * 测试使用 ${} 设置表名 (${}常见使用场景二：设置表名)
     *      ● 现实业务当中，可能会存在分表存储数据的情况。因为一张表存的话，数据量太大。查询效率比较低。可以将这些数据有规律的分表存储，这样在查询的时候效率就比较高。因为扫描的数据量变少了。
     *      ○ 如，日志表：专门存储日志信息的。如果t_log只有一张表，这张表中每一天都会产生很多log，慢慢的，这个表中数据会很多。我们可以每月生成一个新表。每张表以当月日期作为名称，例如：t_log_202401、t_log_202402....当我们需要知道某一月的日志信息，假设本月是202401，那么我们可以直接查：t_log_202401的表即可。
     */
    @Test
    public void testSetTableNameUseDollarSymbol() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        LogMapper mapper = sqlSession.getMapper(LogMapper.class);
        System.out.println("----------------------------------------------------------------------");
        List<Log> tLog202401 = mapper.selectAllLogByTableName("202401");
        System.out.println("2024年1月日志数据 = " + tLog202401);
        System.out.println("----------------------------------------------------------------------");
        List<Log> tLog202402 = mapper.selectAllLogByTableName("202402");
        System.out.println("2024年2月日志数据 = " + tLog202402);
        System.out.println("----------------------------------------------------------------------");
        sqlSession.close();
    }
}
