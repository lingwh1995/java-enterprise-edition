package org.bluebridge.mapper;

import org.apache.ibatis.annotations.*;
import org.bluebridge.wrapper.QueryWrapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author lingwh
 * @desc 基础Mapper接口，定义了通用的CRUD操作方法
 * @date 2025/12/10 18:45
 */
@Mapper
public interface BaseMapper<T> {

    /**
     * 新增记录
     * @param entity 实体对象
     * @return 影响行数
     */
    @InsertProvider(type = BaseSqlProvider.class, method = "insert")
    int insert(T entity);

    /**
     * 根据ID删除记录
     * @param id 主键ID
     * @return 影响行数
     */
    @DeleteProvider(type = BaseSqlProvider.class, method = "deleteById")
    int deleteById(Serializable id);

//    /**
//     *  根据
//     * @param queryWrapper
//     * @return
//     */
//    @DeleteProvider(type = BaseSqlProvider.class, method = "delete")
//    int delete(QueryWrapper<T> queryWrapper);

//    /**
//     * 更新记录
//     * @param entity 实体对象
//     * @return 影响行数
//     */
//    @UpdateProvider(type = BaseSqlProvider.class, method = "update")
//    int update(T entity);

    /**
     * 根据ID查询记录
     * @param id 主键ID
     * @return 实体对象
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "selectById")
    T selectById(Serializable id);

//    /**
//     * 根据条件查询单条记录
//     * @param queryWrapper
//     * @return
//     */
//    @SelectProvider(type = BaseSqlProvider.class, method = "select")
//    T select(QueryWrapper<T> queryWrapper);

    /**
     * 根据条件查询记录列表
     * @param queryWrapper
     * @return
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "selectList")
    List<T> selectList(QueryWrapper<T> queryWrapper);

}

