package org.bluebridge.mapper;

import org.apache.ibatis.annotations.Param;
import org.bluebridge.domain.Car;

import java.util.List;

public interface ICarMapper {

    /**
     * 多条件查询
     * 可能的条件包括：品牌（brand）、指导价格（guide_price）、汽车类型（car_type）
     *
     * @param brand 品牌
     * @param guidePrice 指导价格
     * @param carType 汽车类型
     * @return 汽车信息组成的集合
     */
    List<Car> selectByMultiCondition(
            @Param("brand") String brand,
            @Param("guidePrice") Double guidePrice,
            @Param("carType") String carType
    );

}
