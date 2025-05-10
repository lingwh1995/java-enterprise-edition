package org.bluebridge.multiplexsql.test;

import org.bluebridge.multiplexsql.dao.IEmpDao;
import org.bluebridge.multiplexsql.domain.Emp;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


/**
 * 测试mybatis抽取可重用的sql片段
 */
public class MybatisMultiplexSqlTest {

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
    public void batchInsertByCommonsql() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**
             * 4.获取接口实现类对象
             */
            IEmpDao empDao = sqlSession.getMapper(IEmpDao.class);
            /**
             * 5.插入数据
             */
            List<Emp> emps = Arrays.asList(new Emp("18", "aa", "aa.@163.com", "男"),
                    new Emp("28", "bb", "bb.@163.com", "男"),
                    new Emp("38", "cc", "cc.@163.com", "男"));
            int count = empDao.batchInsertByCommonsql(emps);
            System.out.println("插入的记录条数:"+count);
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
