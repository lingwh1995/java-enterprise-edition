package com.xa8bit.mybatis_a.sqlsession;

import com.xa8bit.mybatis_a.entity.Configuration;
import com.xa8bit.mybatis_a.executor.DefaultExecutor;
import com.xa8bit.mybatis_a.executor.Executor;
import com.xa8bit.mybatis_a.mapper.MapperProxy;
import com.xa8bit.mybatis_a.mapper.MapperStatement;

import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.List;

/**
 * mybatis暴露给外部的接口,实现增删改查功能:
 *      1.对象提供数据访问的api
 *      2.对内将请求转发给Executor
 * @author ronin
 */
public class DefaultSqlSession implements SqlSession {
    private final Configuration config;
    /**
     * 注意:这个执行器不能定义为final类型
     */
    private Executor executor;

    public DefaultSqlSession(Configuration config) {
        this.config = config;
        executor = new DefaultExecutor(config);
    }

    @Override
    public <T> T selectOne(String coordinate,Object... params) {
        List<T> resultList = this.selectList(coordinate, params);
        if(resultList.isEmpty()){
            return (T)Collections.emptyList();
        }
        if(resultList.size() == 1){
            return resultList.get(0);
        }
        if(resultList.size() > 1){
            throw new RuntimeException("too many result......");
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String coordinate, Object... params) {
        MapperStatement mapperStatement = config.getMapStatements().get(coordinate);
        return executor.query(mapperStatement,params);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        MapperProxy mapperProxy = new MapperProxy(this);
        return (T)Proxy.newProxyInstance(type.getClassLoader(),new Class[]{type},mapperProxy);
    }
}
