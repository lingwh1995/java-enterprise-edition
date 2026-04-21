package org.bluebridge.utils;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * MyBatis工具类 ThreadLocal 版
 *      1.可以在Service和Dao层中通过threadlocal获取到同一个SqlSession对象
 *      2.同时也有一个缓存效果，这样有利于资源的节省
 *
 * 需要注意的是：
 *      这个threadlocal中存放的SqlSession对象一定要回收，否则会引起内存泄露
 *
 */
public class SqlSessionUtilThreadLocal {

    private static SqlSessionFactory sqlSessionFactory;

    private SqlSessionUtilThreadLocal() {}

    private static ThreadLocal<SqlSession> local = new ThreadLocal();

    /**
     * 类加载时初始化sqlSessionFactory对象
     */
    static {
        try {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开会话
     * @return
     */
    public static SqlSession openSession() {
        SqlSession sqlSession = local.get();
        if (sqlSession == null) {
            //开启会话并获取会话对象
            sqlSession = sqlSessionFactory.openSession();
            //将sqlSession绑定到当前线程上
            local.set(sqlSession);
        }
        return sqlSession;
    }

    /**
     * 关闭SqlSession对象(从当前线程中移除SqlSession对象。)
     * @param sqlSession
     */
    public static void close(SqlSession sqlSession) {
        if (sqlSession != null) {
            sqlSession.close();
            // 注意移除SqlSession对象和当前线程的绑定关系，防止内存泄露
            local.remove();
        }
    }
}
