package org.bluebridge;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;

public class MybatisHelloworldTest {
    private static final String ENV = "mysql";

    @Test
    public void testMybatisHelloWorld() {
        SqlSession sqlSession = null;
        try {
            // 1. 创建SqlSessionFactoryBuilder对象
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            // 2. 创建SqlSessionFactory对象
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(ENV + "/mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
            // 3. 创建SqlSession对象
            sqlSession = sqlSessionFactory.openSession();
            // 4. 执行sql
            int count = sqlSession.insert("emp.insertEmployee"); // 这个"insertCar"必须是sql的id
            System.out.println("插入几条数据：" + count);
            // 5. 提交（mybatis默认采用的事务管理器是JDBC，默认是不提交的，需要手动提交。）
            sqlSession.commit();
        }catch (Exception e){
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            e.printStackTrace();
        }finally {
            if (sqlSession != null) {
                // 6. 关闭资源（只关闭是不会提交的）
                sqlSession.close();
            }
        }
    }
}
