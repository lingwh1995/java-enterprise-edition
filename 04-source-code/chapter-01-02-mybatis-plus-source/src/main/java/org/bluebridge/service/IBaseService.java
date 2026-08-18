package org.bluebridge.service;

import org.bluebridge.wrapper.QueryWrapper;

import java.io.Serializable;
import java.util.List;

/**
 * 基础服务接口，定义通用的 CRUD 操作
 *
 * @author lingwh
 * @date 2025/12/10 19:15
 */
public interface IBaseService<T> {

    /**
     * 新增一条记录
     *
     * @param entity 实体对象
     * @return 影响行数
     */
    int save(T entity);

    /**
     * 根据主键删除记录
     *
     * @param id 主键 ID
     * @return 影响行数
     */
    int deleteById(Serializable id);

    /**
     * 根据条件删除记录
     * @param queryWrapper
     * @return
     */
//    int delete(QueryWrapper<T> queryWrapper);

    /**
     * 更新记录
     * @param entity 实体对象
     * @return 影响行数
     */
//    int update(T entity);

    /**
     * 根据主键查询记录
     *
     * @param id 主键 ID
     * @return 实体对象
     */
    T getById(Serializable id);

    /**
     * 根据条件查询单
     * @param queryWrapper
     * @return
     */
//    T getOne(QueryWrapper<T> queryWrapper);

    /**
     * 根据条件查询记录列表
     *
     * @param queryWrapper
     * @return
     */
    List<T> list(QueryWrapper<T> queryWrapper);
}
