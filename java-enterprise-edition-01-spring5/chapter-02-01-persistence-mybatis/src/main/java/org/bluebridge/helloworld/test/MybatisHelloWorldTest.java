package org.bluebridge.helloworld.test;

import org.bluebridge.helloworld.domain.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MybatisHelloWorldTest {

    private static final String ENV = "mysql";
    /**
     * 1、根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象 有数据源一些运行环境信息
     * 2、sql映射文件；配置了每一个sql，以及sql的封装规则等。
     * 3、将sql映射文件注册在全局配置文件中
     * 4、写代码：
     * 		1）、根据全局配置文件得到SqlSessionFactory；
     * 		2）、使用sqlSession工厂，获取到sqlSession对象使用他来执行增删改查
     * 			一个sqlSession就是代表和数据库的一次会话，用完关闭
     * 		3）、使用sql的唯一标志来告诉MyBatis执行哪个sql。sql都是保存在sql映射文件中的。
     *
     * @throws IOException
     */
    private SqlSession getSqlSession() throws IOException {
        // 1.加载配置文件
        InputStream inputStream = Resources.getResourceAsStream( ENV + "/mybatis-config.xml");
        // 2.获取SqlSession对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory.openSession();
    }

    @Test
    public void fun1() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try{
            Employee employee =  sqlSession.selectOne("emp.getEmployeeById", "1");
            System.out.println(employee);
            sqlSession.commit();
        }catch (Exception e){
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            e.printStackTrace();
        }finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }


}
