package org.bluebridge;

import org.apache.ibatis.session.SqlSession;
import org.bluebridge.domain.Car;
import org.bluebridge.mapper.ICarMapper;
import org.bluebridge.utils.SqlSessionUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


/**
 * Mybatis动态sql
 */
public class MysqlDynamicSqlTest {

    /**
     * 测试 mybatis动态sql-使用if标签动态拼接多个查询条件
     */
    @Test
    public void selectByMultiConditionUseIfTag() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ICarMapper mapper = sqlSession.getMapper(ICarMapper.class);

        // 假设三个条件都是空
        List<Car> cars = mapper.selectByMultiConditionUseIfTag("比亚迪", 2.0, "新能源");
        System.out.println("cars = " + cars);

        // 假设三个条件都是空
        cars = mapper.selectByMultiConditionUseIfTag("", null, "");
        System.out.println("cars = " + cars);

        // 假设后两个条件不为空，第一个条件为空
        cars = mapper.selectByMultiConditionUseIfTag("", 2.0, "新能源");
        System.out.println("cars = " + cars);
        sqlSession.close();
    }

    /**
     * 测试 mybatis动态sql-使用where标签更智能(优雅)的动态拼接多个查询条件
     */
    @Test
    public void testSelectByMultiConditionUseWhereTag(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ICarMapper mapper = sqlSession.getMapper(ICarMapper.class);
        // 三个条件都不是空
        //List<Car> cars = mapper.selectByMultiConditionUseWhereTag("比亚迪", 2.0, "新能源");
        // 三个条件都是空
        //List<Car> cars = mapper.selectByMultiConditionUseWhereTag("", null, "");
        // 如果第一个条件是空
        //List<Car> cars = mapper.selectByMultiConditionUseWhereTag("", 2.0, "新能源");
        // 后面两个条件是空
        List<Car> cars = mapper.selectByMultiConditionUseWhereTag("比亚迪", null, "");
        cars.forEach(car -> System.out.println(car));
        sqlSession.close();
    }

    /**
     * 测试 mybatis动态sql-使用trim标签更细致的处理动态拼接多个查询条件多余的 and 或者 or
     */
    @Test
    public void testSelectByMultiConditionUseTrimTag(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ICarMapper mapper = sqlSession.getMapper(ICarMapper.class);
        // 三个条件都不是空
        //List<Car> cars = mapper.selectByMultiConditionUseTrimTag("比亚迪", 2.0, "新能源");
        // 三个条件都是空
        //List<Car> cars = mapper.selectByMultiConditionUseTrimTag("", null, "");
        // 如果第一个条件是空
        //List<Car> cars = mapper.selectByMultiConditionUseTrimTag("", 2.0, "新能源");
        // 后面两个条件是空
        List<Car> cars = mapper.selectByMultiConditionUseTrimTag("比亚迪", null, "");
        cars.forEach(car -> System.out.println(car));
        sqlSession.close();
    }

    /**
     * 测试 mybatis动态sql-使用trim标签更细致的处理动态拼接多个查询条件多余的 and 或者 or
     */
    @Test
    public void testUpdateCarUseSetTag(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ICarMapper mapper = sqlSession.getMapper(ICarMapper.class);
        Car car = new Car();
        car.setId(1l);
        car.setBrand("凯迪拉克");
        car.setGuidePrice(28.5);
        car.setCarType("新能源");
        int i = mapper.updateCarUseSetTag(car);
        //更新操作要提交事务，否则数据库里面的数据不会发生改变
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 测试 mybatis动态sql-使用choose、when、otherwise标签动态从多个查询条件中选出最先符合要求的一个查询条件
     */
    @Test
    public void testSelectUseChooseWhenOtherwiseTge(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ICarMapper mapper = sqlSession.getMapper(ICarMapper.class);
        // 第一个条件都不是空，其他两个条件是空
        //List<Car> cars = mapper.selectUseChooseWhenOtherwiseTge("比亚迪", null, null);

        // 第二个条件都不是空，其他两个条件是空
        //List<Car> cars = mapper.selectUseChooseWhenOtherwiseTge(null, 2.0, null);

        // 第三个条件都不是空，其他两个条件是空
        List<Car> cars = mapper.selectUseChooseWhenOtherwiseTge(null, null, "新能源");
        cars.forEach(car -> System.out.println(car));
        sqlSession.close();
    }

    /**
     * 测试 mybatis动态sql-使用foreach标签实现批量删除：使用in关键字拼接参数
     */
    @Test
    public void testDeleteByIdsUseForeachTagIn(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ICarMapper mapper = sqlSession.getMapper(ICarMapper.class);
        Long[] ids = {1l,2l,3l};
        int i = mapper.deleteBatchByIdsUseForeachTagIn(ids);
        //删除操作要提交事务，否则数据库里面的数据不会发生改变
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 测试 mybatis动态sql-使用foreach标签实现批量删除：使用or关键字拼接参数
     */
    @Test
    public void testDeleteByIdsUseForeachTagOr(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ICarMapper mapper = sqlSession.getMapper(ICarMapper.class);
        Long[] ids = {1l,2l,3l};
        int i = mapper.deleteBatchByIdsUseForeachTagOr(ids);
        //删除操作要提交事务，否则数据库里面的数据不会发生改变
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 测试 mybatis动态sql-使用foreach标签实现批量插入
     */
    @Test
    public void testInsertBatchUseForeachTag(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ICarMapper mapper = sqlSession.getMapper(ICarMapper.class);
        List<Car> cars = new ArrayList<>();
        IntStream.range(0,5).forEach(i -> {
            Car car = new Car();
            car.setBrand("凯迪拉克" + i);
            car.setGuidePrice(28.5 + i);
            car.setCarType("新能源");
            cars.add(car);
        });
        int i = mapper.insertBatchUseForeachTag(cars);
        //插入操作要提交事务，否则数据库里面的数据不会发生改变
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 测试 mybatis动态sql-使用sql和include标签抽取可重用sql
     */
    @Test
    public void testSelectAllRetMapUseSqlAndIncludeTag(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ICarMapper mapper = sqlSession.getMapper(ICarMapper.class);
        List<Car> cars = mapper.selectAllCarsUseSqlAndIncludeTag();
        System.out.println("cars = " + cars);
        cars = mapper.selectAllCarsByIdUseSqlAndIncludeTag(4l);
        System.out.println("cars = " + cars);
        //插入操作要提交事务，否则数据库里面的数据不会发生改变
        sqlSession.commit();
        sqlSession.close();
    }
}
