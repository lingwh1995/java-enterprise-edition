package com.xa8bit.mybatis_a.mapper;

import com.xa8bit.mybatis_a.sqlsession.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Mapper 接口的动态代理，将方法调用转发给 SqlSession 执行
 *
 * @author lingwh
 * @date 2025/12/20 18:45
 */
public class MapperProxy<E> implements InvocationHandler{

    private SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public E invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 如果是 jdk 的方法，则直接放行
        if(Object.class.equals(method.getDeclaringClass())){
            return (E)method.invoke(this,args);
        }
        // 如果返回值是 List 或者 List 的子类
        if(Collection.class.isAssignableFrom(method.getReturnType())){
            return (E)sqlSession.selectList(method.getDeclaringClass().getName() + "." + method.getName(),
                    args);
        }else{
            return (E)sqlSession.selectOne(method.getDeclaringClass().getName()+"."+method.getName(),
                    args);
        }
    }

}
