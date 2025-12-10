package org.bluebridge.service;

import java.util.List;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/10 19:15
 */
public interface IBaseService<T> {

    /**
     * 插入一条记录
     * @param entity 实体对象
     * @return 影响行数
     */
    int save(T entity);

    /**
     * 根据主键删除记录
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteById(Object id);

    /**
     * 根据实体删除记录
     * @param entity 实体对象
     * @return 影响行数
     */
    int delete(T entity);

    /**
     * 更新记录
     * @param entity 实体对象
     * @return 影响行数
     */
    int update(T entity);

    /**
     * 根据主键查询记录
     * @param id 主键ID
     * @return 实体对象
     */
    T selectById(Object id);

    /**
     * 根据实体查询单条记录
     * @param entity 实体对象
     * @return 实体对象
     */
    T select(T entity);

    /**
     * 根据实体查询记录列表
     * @param entity 实体对象
     * @return 实体对象列表
     */
    List<T> selectList(T entity);

}