package org.bluebridge.mapper;

import org.apache.ibatis.annotations.Param;
import org.bluebridge.domain.Car;

import java.util.List;

/**
 * 汽车 Mapper 接口
 *
 * @author lingwh
 * @date 2026/1/10 14:30
 */
public interface ICarMapper {

    /**
     * mybatis 动态 sql-使用 if 标签动态拼接多个查询条件
     * 可能的条件包括：品牌（brand）、指导价格（guide_price）、汽车类型（car_type）
     *
     * @param brand 品牌
     * @param guidePrice 指导价格
     * @param carType 汽车类型
     * @return 汽车信息组成的集合
     */
    List<Car> selectByMultiConditionUseIfTag(
            @Param("brand") String brand,
            @Param("guidePrice") Double guidePrice,
            @Param("carType") String carType
    );

    /**
     * mybatis 动态 sql-使用 where 标签更智能(优雅)的动态拼接多个查询条件
     *
     * @param brand 品牌
     * @param guidePrice 指导价格
     * @param carType 汽车类型
     * @return 汽车信息组成的集合
     */
    List<Car> selectByMultiConditionUseWhereTag(@Param("brand") String brand, @Param("guidePrice") Double guidePrice, @Param("carType") String carType);

    /**
     * mybatis 动态 sql-使用 trim 标签更细致的处理动态拼接多个查询条件多余的 and 或者 or
     *
     * @param brand 品牌
     * @param guidePrice 指导价格
     * @param carType 汽车类型
     * @return 汽车信息组成的集合
     */
    List<Car> selectByMultiConditionUseTrimTag(@Param("brand") String brand, @Param("guidePrice") Double guidePrice, @Param("carType") String carType);

    /**
     * 更新信息，使用 set 标签
     *
     * @param car 汽车信息
     * @return 受影响的行数
     */
    int updateCarUseSetTag(Car car);

    /**
     * mybatis 动态 sql-使用 choose、when、otherwise 标签动态从多个查询条件中选出最先符合要求的一个查询条件
     *
     * @param brand 品牌
     * @param guidePrice 指导价格
     * @param carType 汽车类型
     * @return 汽车信息组成的集合
     */
    List<Car> selectUseChooseWhenOtherwiseTge(@Param("brand") String brand, @Param("guidePrice") Double guidePrice, @Param("carType") String carType);

    /**
     * mybatis 动态 sql-使用 foreach 标签实现批量删除：使用 in 关键字拼接参数
     *
     * @param ids 待删除的 id 数组
     * @return 受影响的行数
     */
    int deleteBatchByIdsUseForeachTagIn(@Param("ids") Long[] ids);

    /**
     * mybatis 动态 sql-使用 foreach 标签实现批量删除：使用 or 关键字拼接参数
     *
     * @param ids 待删除的 id 数组
     * @return 受影响的行数
     */
    int deleteBatchByIdsUseForeachTagOr(@Param("ids") Long[] ids);

    /**
     * mybatis 动态 sql-使用 foreach 标签实现批量插入
     *
     * @param cars 待插入的汽车集合
     * @return 受影响的行数
     */
    int insertBatchUseForeachTag(@Param("cars") List<Car> cars);

    /**
     * mybatis 动态 sql-使用 sql 和 include 标签抽取可重用 sql-查询所有汽车
     *
     * @return 汽车信息组成的集合
     */
    List<Car> selectAllCarsUseSqlAndIncludeTag();

    /**
     * mybatis 动态 sql-使用 sql 和 include 标签抽取可重用 sql-根据 id 查询汽车
     *
     * @param id 汽车 id
     * @return 汽车信息组成的集合
     */
    List<Car> selectAllCarsByIdUseSqlAndIncludeTag(long id);
}
