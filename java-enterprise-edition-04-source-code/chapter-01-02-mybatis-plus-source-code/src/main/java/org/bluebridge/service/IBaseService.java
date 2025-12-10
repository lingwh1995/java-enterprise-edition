package org.bluebridge.service;

import org.bluebridge.entity.QueryWrapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/10 19:15
 */
public interface IBaseService<T> {

    /**
     *
     *  removeById(Serializable id);
     *  remove(Wrapper<Employee> queryWrapper);
     *
     *
     *  list();
     *  list(Wrapper<Employee> queryWrapper);
     *  count();
     *  count(Wrapper<Employee> queryWrapper);
     *  exists(Wrapper<Employee> queryWrapper);
     *
     *
     *  getOne(Wrapper<Employee> queryWrapper);
     *  getById(Serializable id);
     *  page(IPage<Employee> page);
     *  page(IPage<Employee> page, Wrapper<Employee> queryWrapper);
     *
     */


    /**
     * 新增一条记录
     * @param entity 实体对象
     * @return 影响行数
     */
    int create(T entity);

    /**
     * 根据主键删除记录
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteById(Serializable id);

    /**
     * 根据条件删除记录
     * @param queryWrapper
     * @return
     */
    int delete(QueryWrapper<T> queryWrapper);

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
    T selectById(Serializable id);

    /**
     * 根据条件查询单
     * @param queryWrapper
     * @return
     */
    T select(QueryWrapper<T> queryWrapper);

    /**
     * 根据条件查询记录列表
     * @param queryWrapper
     * @return
     */
    List<T> selectList(QueryWrapper<T> queryWrapper);

}