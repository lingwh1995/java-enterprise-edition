package org.bluebridge.mapper;

import org.bluebridge.domain.Car;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarMapper {

    /**
     * 分页查询-使用mysql原生方式分页
     * @param startIndex 起始下标
     * @param pageSize 每页显示的记录条数
     * @return
     */
    List<Car> selectByPageUseMysqlNative(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     * 分页查询-使用PageHelper插件分页
     * @return
     */
    List<Car> selectByPageUsePageHelper();
}