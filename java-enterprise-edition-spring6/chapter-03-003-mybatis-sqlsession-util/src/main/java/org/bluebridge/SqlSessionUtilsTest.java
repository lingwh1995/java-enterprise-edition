package org.bluebridge;

import org.apache.ibatis.session.SqlSession;
import org.bluebridge.utils.SqlSessionUtil;
import org.bluebridge.utils.SqlSessionUtilThreadLocal;
import org.junit.Test;

public class SqlSessionUtilsTest {

    /**
     * 测试 SqlSessionUtil 普通版
     */
    @Test
    public void fun1() {
        SqlSession sqlSession1 = SqlSessionUtil.openSession();
        System.out.println("sqlSession1 = " + sqlSession1);

        SqlSession sqlSession2 = SqlSessionUtil.openSession();
        System.out.println("sqlSession2 = " + sqlSession2);
    }

    /**
     * 测试 SqlSessionUtil ThreadLocal 普通版
     */
    @Test
    public void fun2() {
        SqlSession sqlSession1 = SqlSessionUtilThreadLocal.openSession();
        System.out.println("sqlSession1 = " + sqlSession1);
        SqlSession sqlSession2 = SqlSessionUtilThreadLocal.openSession();
        System.out.println("sqlSession2 = " + sqlSession2);

        //关闭SqlSession
        SqlSessionUtilThreadLocal.close(sqlSession2);

        SqlSession sqlSession3 = SqlSessionUtilThreadLocal.openSession();
        System.out.println("sqlSession3 = " + sqlSession3);
    }

}
