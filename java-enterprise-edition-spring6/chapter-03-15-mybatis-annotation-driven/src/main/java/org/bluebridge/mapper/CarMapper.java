package org.bluebridge.mapper;


import org.bluebridge.domain.Car;
import org.apache.ibatis.annotations.*;

public interface CarMapper {
    @Insert("insert into t_car values(null,#{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})")
    int insert(Car car);

    @Delete("delete from t_car where id = #{id}")
    int deleteById(Long id);

    @Update("update t_car set car_num=#{carNum},brand=#{brand},guide_price=#{guidePrice},produce_time=#{produceTime},car_type=#{carType} where id=#{id}")
    int update(Car car);

//    // 有启动字段名和属性名自动映射
//    @Select("select * from t_car where id = #{id}")
//    Car selectById(Long id);

    @Select("select * from t_car where id = #{id}")
    // 没有启动字段名和属性名自动映射
    // 定义属性名与字段名的对应关系
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "carNum", column = "car_num"),
            @Result(property = "brand", column = "brand"),
            @Result(property = "guidePrice", column = "guide_price"),
            @Result(property = "produceTime", column = "produce_time"),
            @Result(property = "carType", column = "car_type")
    })
    Car selectById(Long id);
}