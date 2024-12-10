package org.bluebridge.utils;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;

public class SqlSessionUtil {

    private static SqlSessionFactory sqlSessionFactory = null; // 默认使用数据库环境对应的SqlSessionFactory
    private static ThreadLocal<SqlSession> local = new ThreadLocal<>(); // 保证一个线程对应一个SqlSession

    static {
        try {
            // 获取默认使用数据库环境对应的SqlSessionFactory对象
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 私有化构造器，防止创建工具类实例对象
     */
    private SqlSessionUtil() {}

    /**
     * 使用工厂对象开启本线程和数据库的会话
     *
     * @return 与数据库的会话对象
     */
    public static SqlSession openSession() {
        SqlSession sqlSession = local.get(); // 获取当前线程对应的数据库会话对象
        // 如果当前线程没有对应的数据库会话对象，则创建一个会话对象
        if (sqlSession == null) {
            sqlSession = sqlSessionFactory.openSession();
            // 保存本线程对应的数据库会话对象
            // 将sqlSession对象绑定到当前线程上。
            local.set(sqlSession);
        }
        return sqlSession;
    }

    /**
     * 关闭会话对象
     * 关闭SqlSession对象并从当前线程中移除SqlSession对象
     */
    public static void close() {
        SqlSession sqlSession = local.get(); // 获取当前线程对应的数据库会话对象
        // 如果当前线程有相应的数据库会话对象就进行关闭
        if (sqlSession != null) {
            sqlSession.close();
            // 注意移除SqlSession对象和当前线程的绑定关系。
            // 因为Tomcat服务器支持线程池。也就是说：用过的线程对象t1，可能下一次还会使用这个t1线程。
            local.remove();
        }
    }
}
