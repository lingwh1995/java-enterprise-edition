package com.dragonsoft.interfacemapper.test;

import com.dragonsoft.interfacemapper.dao.IEmployeeDao;
import com.dragonsoft.interfacemapper.domain.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;


/**
 * 测试mybatis接口式编程
 */
public class MybatisInterfaceMapperTest {
    @Test
    public void fun1() throws IOException {
        /**
         * 1.加载配置文件
         */
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        /**
         * 2.获取SqlSession对象
         */
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        /**
         * 3.获取SqlSession对象
         */
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            /**
             * 4.获取接口实现类对象
             */
            IEmployeeDao employeeDaoImpl = sqlSession.getMapper(IEmployeeDao.class);
            System.out.println("------------------------------");
            System.out.println(employeeDaoImpl.getClass());
            System.out.println("------------------------------");
            /**
             * 5.根据id获取对象
             */
            Employee emplyee= employeeDaoImpl.getEmplyeeById("4");
            System.out.println("emplyee:"+emplyee);

            /**
             * 5.根据id获取lastName
             */
            String lastName = employeeDaoImpl.getEmplyeeLastNameById("4");
            System.out.println("lastName:"+lastName);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**
             * 6.关闭sqlSession
             */
            sqlSession.close();
        }
    }
}
