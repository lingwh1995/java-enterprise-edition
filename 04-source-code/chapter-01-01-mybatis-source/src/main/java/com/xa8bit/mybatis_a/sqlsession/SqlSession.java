package com.xa8bit.mybatis_a.sqlsession;

import java.util.List;

/**
 * MyBatis 暴露给外部的 SqlSession 接口，提供数据访问 API
 *
 * @author lingwh
 * @date 2025/12/20 15:05
 */
public interface SqlSession {

    /**
     * 查找单个数据：实际上调用的查询列表的方法，返回列表中的第一个元素
     *
     * @param coordinate sql 语句在 mapper.xml 中的坐标，namespace+id
     * @param params sql 语句执行需要的参数
     * @param <T> 返回指定类型的数据
     * @return
     */
    <T> T selectOne(String coordinate,Object... params);

    /**
     * 查询列表的方法
     *
     * @param <T> 返回指定类型的数据，并将该类型的数据封装到 List 中
     * @param coordinate sql 语句在 mapper.xml 中的坐标，namespace+id
     * @param params sql 语句执行需要的参数
     * @return
     */
    <T> List<T> selectList(String coordinate, Object... params);

    /**
     * 根据 class 的类型创建 T 类型的对象
     *
     * @param type 指定的类型
     * @param <T> T 类型的对象
     * @return
     */
    <T> T getMapper(Class<T> type);
}
