package com.xa8bit.mybatis_a.executor;

import com.xa8bit.mybatis_a.mapper.MapperStatement;

import java.util.List;

/**
 * 执行器接口，负责真正执行 SQL 查询操作
 *
 * @author lingwh
 * @date 2025/12/20 10:50
 */
public interface Executor {

    /**
     * Executor 会真正的执行查询操作
     *
     * @param mapperStatement 封装了 Mapper.xml 中<select></select>等标签中保存的信息
     * @param params 指定的 sql 语句执行对应要传入的参数
     * @param <E> 返回指定的类型
     * @return
     */
    <E> List<E> query(MapperStatement mapperStatement, Object... params);
}
