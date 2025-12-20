package com.xa8bit.mybatis_a.mapper;

import com.xa8bit.mybatis_a.sqlsession.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 *
 * @author ronin
 */
public class MapperProxy<E> implements InvocationHandler{

    private SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public E invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 如果是jdk的方法,则直接放行
        if(Object.class.equals(method.getDeclaringClass())){
            return (E)method.invoke(this,args);
        }
        // 如果返回值是List或者List的子类
        if(Collection.class.isAssignableFrom(method.getReturnType())){
            return (E)sqlSession.selectList(method.getDeclaringClass().getName() + "." + method.getName(),
                    args);
        }else{
            return (E)sqlSession.selectOne(method.getDeclaringClass().getName()+"."+method.getName(),
                    args);
        }
    }

}
