package com.dragonsoft.builtinparam.test;

import com.dragonsoft.builtinparam.dao.IEmpDao;
import com.dragonsoft.builtinparam.domain.Emp;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * 测试mybatis两个内置参数_databaseId _parameter
 */
public class MybatisBuiltinParamTest {

    /**
     * 获取SqlSession
     * @return
     * @throws IOException
     */
    public SqlSession getSqlSession() throws IOException{
        /**
         * 1.加载配置文件
         */
        InputStream inputStream = Resources.getResourceAsStream("mysql/mybatis-config.xml");
        /**
         * 2.获取SqlSession对象
         */
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        /**
         * 3.获取SqlSession对象
         */
        //获取可以自动提交的openSession对象,传入true
        //SqlSession sqlSession = sqlSessionFactory.openSession(true);
        //获取需要手动提交的openSession对象,传入fasle或者什么都不传
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    }

    /**
     * 新增一个Emp对象,不返回任何值
     * @throws IOException
     */
    @Test
    public void getEmpsTestInnerParameter() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**
             * 4.获取接口实现类对象
             */
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**
             * 5.查询数据
             */
            Emp emp = new Emp();
            emp.setId("1");
            emp.setLastName("z");
            List<Emp> emps = empDaoImpl.getEmpsTestInnerParameter(emp);
            emps.forEach(System.out::println);
            /**
             * 6.手动提交数据
             */
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**
             * 7.关闭sqlSession
             */
            sqlSession.close();
        }
    }

}
