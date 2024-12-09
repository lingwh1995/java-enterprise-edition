package org.bluebridge;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;

public class CreateSqlSessionTest {
    private static final String ENV = "mysql";

    @Test
    public void testMybatisHelloWorld() {
        SqlSession sqlSession = null;
        try {
            // 1. 创建SqlSessionFactoryBuilder对象
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            // 2. 读取配置文件
                //  方式一：Resources.getResourceAsStream 默认就是从类的根路径下开始查找资源
            //InputStream is = Resources.getResourceAsStream(ENV + "/mybatis-config.xml");
                //  方式二：使用当前线程的类加载器加载资源
            //InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(ENV + "/mybatis-config.xml");
                //  方式三：使用系统类加载器加载资源
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(ENV + "/mybatis-config.xml");

            // 3. 创建SqlSessionFactory对象
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
            // 4. 创建SqlSession对象
            sqlSession = sqlSessionFactory.openSession();
            System.out.println("创建的SqlSession对象 : " + sqlSession);
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
