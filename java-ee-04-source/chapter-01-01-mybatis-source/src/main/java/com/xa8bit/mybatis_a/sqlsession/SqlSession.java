package com.xa8bit.mybatis_a.sqlsession;


import java.util.List;

/**
 * @author ronin
 */
public interface SqlSession {

    /**
     * 查找单个数据:实际上调用的查询列表的方法,返回列表中的第一个元素
     * @param coordinate sql语句在mapper.xml中的坐标,namespace+id
     * @param params sql语句执行需要的参数
     * @param <T> 返回指定类型的数据
     * @return
     */
    <T> T selectOne(String coordinate,Object... params);

    /**
     * 查询列表的方法
     * @param <T> 返回指定类型的数据,并将该类型的数据封装到List中
     * @param coordinate sql语句在mapper.xml中的坐标,namespace+id
     * @param params sql语句执行需要的参数
     * @return
     */
    <T> List<T> selectList(String coordinate, Object... params);

    /**
     * 根据class的类型创建T类型的对象
     * @param type 指定的类型
     * @param <T> T类型的对象
     * @return
     */
    <T> T getMapper(Class<T> type);

}
