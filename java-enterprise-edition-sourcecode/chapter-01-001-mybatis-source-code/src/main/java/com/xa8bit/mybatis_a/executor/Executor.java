package com.xa8bit.mybatis_a.executor;

import com.xa8bit.mybatis_a.mapper.MapperStatement;

import java.util.List;

/**
 * @author ronin
 */
public interface Executor {
    /**
     * Executor会真正的执行查询操作
     * @param mapperStatement 封装了Mapper.xml中<select></select>等标签中保存的信息
     * @param params 指定的sql语句执行对应要传入的参数
     * @param <E> 返回指定的类型
     * @return
     */
    <E> List<E> query(MapperStatement mapperStatement, Object... params);
}
