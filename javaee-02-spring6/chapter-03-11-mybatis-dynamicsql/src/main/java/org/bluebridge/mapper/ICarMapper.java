package org.bluebridge.mapper;

import org.apache.ibatis.annotations.Param;
import org.bluebridge.domain.Car;

import java.util.List;
import java.util.Map;

public interface ICarMapper {

    /**
     * mybatis动态sql-使用if标签动态拼接多个查询条件
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
     * mybatis动态sql-使用where标签更智能(优雅)的动态拼接多个查询条件
     * @param brand
     * @param guidePrice
     * @param carType
     * @return
     */
    List<Car> selectByMultiConditionUseWhereTag(@Param("brand") String brand, @Param("guidePrice") Double guidePrice, @Param("carType") String carType);

    /**
     * mybatis动态sql-使用trim标签更细致的处理动态拼接多个查询条件多余的 and 或者 or
     * @param brand
     * @param guidePrice
     * @param carType
     * @return
     */
    List<Car> selectByMultiConditionUseTrimTag(@Param("brand") String brand, @Param("guidePrice") Double guidePrice, @Param("carType") String carType);

    /**
     * 更新信息，使用set标签
     * @param car
     * @return
     */
    int updateCarUseSetTag(Car car);


    /**
     * mybatis动态sql-使用choose、when、otherwise标签动态从多个查询条件中选出最先符合要求的一个查询条件
     * @param brand
     * @param guidePrice
     * @return
     */
    List<Car> selectUseChooseWhenOtherwiseTge(@Param("brand") String brand, @Param("guidePrice") Double guidePrice, @Param("carType") String carType);

    /**
     * mybatis动态sql-使用foreach标签实现批量删除：使用in关键字拼接参数
     * @param ids
     * @return
     */
    int deleteBatchByIdsUseForeachTagIn(@Param("ids") Long[] ids);

    /**
     * mybatis动态sql-使用foreach标签实现批量删除：使用or关键字拼接参数
     * @param ids
     * @return
     */
    int deleteBatchByIdsUseForeachTagOr(@Param("ids") Long[] ids);

    /**
     * mybatis动态sql-使用foreach标签实现批量插入
     * @param cars
     * @return
     */
    int insertBatchUseForeachTag(@Param("cars") List<Car> cars);


    /* mybatis动态sql-使用sql和include标签抽取可重用sql开始 */
    List<Car> selectAllCarsUseSqlAndIncludeTag();
    List<Car> selectAllCarsByIdUseSqlAndIncludeTag(long id);
    /* mybatis动态sql-使用sql和include标签抽取可重用sql结束 */
}
