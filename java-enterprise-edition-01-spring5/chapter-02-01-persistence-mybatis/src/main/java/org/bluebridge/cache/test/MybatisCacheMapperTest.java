package org.bluebridge.cache.test;

import org.bluebridge.cache.dao.IDepartmentDao;
import org.bluebridge.cache.dao.IEmployeeDao;
import org.bluebridge.cache.domain.Department;
import org.bluebridge.cache.domain.Employee;
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
public class MybatisCacheMapperTest {

    /**
     * 获取SqlSessionFactory
     * @return
     * @throws IOException
     */
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mysql/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }


    /**
     * 两级缓存：
     * 一级缓存：（本地缓存）：sqlSession级别的缓存。一级缓存是一直开启的；SqlSession级别的一个Map
     * 		与数据库同一次会话期间查询到的数据会放在本地缓存中。
     * 		以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库；
     *
     * 		一级缓存失效情况（没有使用到当前一级缓存的情况，效果就是，还需要再向数据库发出查询）：
     * 		1、sqlSession不同。
     * 		2、sqlSession相同，查询条件不同.(当前一级缓存中还没有这个数据)
     * 		3、sqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)
     * 		4、sqlSession相同，手动清除了一级缓存（缓存清空）
     *
     * 二级缓存：（全局缓存）：基于namespace级别的缓存：一个namespace对应一个二级缓存：
     * 		工作机制：
     * 		1、一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中；
     * 		2、如果会话关闭；一级缓存中的数据会被保存到二级缓存中；新的会话查询信息，就可以参照二级缓存中的内容；
     * 		3、sqlSession===EmployeeMapper==>Employee
     * 						DepartmentMapper===>Department
     * 			不同namespace查出的数据会放在自己对应的缓存中（map）
     * 			效果：数据会从二级缓存中获取
     * 				查出的数据都会被默认先放在一级缓存中。
     * 				只有会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存中
     * 		使用：
     * 			1）、开启全局二级缓存配置：<setting name="cacheEnabled" value="true"/>
     * 			2）、去mapper.xml中配置使用二级缓存：
     * 				<cache></cache>
     * 			3）、我们的POJO需要实现序列化接口
     *
     * 和缓存有关的设置/属性：
     * 			1）、cacheEnabled=true：false：关闭缓存（二级缓存关闭）(一级缓存一直可用的)
     * 			2）、每个select标签都有useCache="true"：
     * 					false：不使用缓存（一级缓存依然使用，二级缓存不使用）
     * 			3）、【每个增删改标签的：flushCache="true"：（一级二级都会清除）】
     * 					增删改执行完成后就会清楚缓存；
     * 					测试：flushCache="true"：一级缓存就清空了；二级也会被清除；
     * 					查询标签：flushCache="false"：
     * 						如果flushCache=true;每次查询之后都会清空缓存；缓存是没有被使用的；
     * 			4）、sqlSession.clearCache();只是清楚当前session的一级缓存；
     * 			5）、localCacheScope：本地缓存作用域：（一级缓存SESSION）；当前会话的所有数据保存在会话缓存中；
     * 								STATEMENT：可以禁用一级缓存；
     *
     *第三方缓存整合：
     *		1）、导入第三方缓存包即可；
     *		2）、导入与第三方缓存整合的适配包；官方有；
     *		3）、mybatis.mapper.xml中使用自定义缓存
     *		<cache type="org.mybatis.mapper.caches.ehcache.EhcacheCache"></cache>
     *
     * @throws IOException
     *
     */

    /**
     * 两级缓存：
     * 一级缓存：（本地缓存）：sqlSession级别的缓存。一级缓存是一直开启的；SqlSession级别的一个Map
     * 		与数据库同一次会话期间查询到的数据会放在本地缓存中。
     * 		以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库；
     *
     * 		一级缓存失效情况（没有使用到当前一级缓存的情况，效果就是，还需要再向数据库发出查询）：
     * 		1、sqlSession不同。
     * 		2、sqlSession相同，查询条件不同.(当前一级缓存中还没有这个数据)
     * 		3、sqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)
     * 		4、sqlSession相同，手动清除了一级缓存（缓存清空）
     */
    @Test
    public void testFirstLevelCache() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            /**
             * 一级缓存生效
             */
            IEmployeeDao employeeDao = openSession.getMapper(IEmployeeDao.class);
            Employee emp01 = employeeDao.getEmployeeById("4");
            System.out.println(emp01);
            Employee emp02 = employeeDao.getEmployeeById("4");
            System.out.println(emp02);
            System.out.println(emp01==emp02);

            /**
             * 一级缓存失效:1.sqlSession不同
             */
            SqlSession openSession2 = sqlSessionFactory.openSession();
            IEmployeeDao employeeDao2 = openSession2.getMapper(IEmployeeDao.class);
            Employee emp03 = employeeDao2.getEmployeeById("4");
            System.out.println(emp03);
            System.out.println(emp01==emp03);


            /**
             * 一级缓存失效:2.sqlSession相同，查询条件不同
             */


            /**
             * 一级缓存失效:3.sqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)
             */
            SqlSession openSession3 = sqlSessionFactory.openSession();
            IEmployeeDao employeeDao3 = openSession3.getMapper(IEmployeeDao.class);
            Employee emp04 = employeeDao3.getEmployeeById("4");
            System.out.println(emp04);
            //两次查询之间进行数据增删改操作
            employeeDao3.addEmployee(new Employee(null,"xx","ronin@163.com","男"));
            Employee emp05 = employeeDao3.getEmployeeById("4");
            System.out.println(emp05);
            System.out.println(emp04==emp05);


            //4、sqlSession相同，手动清除了一级缓存（缓存清空）
            IEmployeeDao employeeDao4 = openSession.getMapper(IEmployeeDao.class);
            Employee emp06 = employeeDao4.getEmployeeById("4");
            System.out.println(emp06);
            //手动清除缓存
            openSession.clearCache();
            Employee emp07 = employeeDao4.getEmployeeById("4");
            System.out.println(emp06);
            System.out.println(emp06==emp07);

        }finally{
            openSession.close();
        }
    }

    /**
     * 二级缓存：（全局缓存）：基于namespace级别的缓存：一个namespace对应一个二级缓存：
     * 		工作机制：
     * 		1、一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中；
     * 		2、如果会话关闭；一级缓存中的数据会被保存到二级缓存中；新的会话查询信息，就可以参照二级缓存中的内容；
     * 		3、sqlSession===EmployeeMapper==>Employee
     * 						DepartmentMapper===>Department
     * 			不同namespace查出的数据会放在自己对应的缓存中（map）
     * 			效果：数据会从二级缓存中获取
     * 				查出的数据都会被默认先放在一级缓存中。
     * 				只有会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存中
     * 		使用：
     * 			1）、开启全局二级缓存配置：<setting name="cacheEnabled" value="true"/>
     * 			2）、去mapper.xml中配置使用二级缓存：
     * 				<cache></cache>
     * 			3）、我们的POJO需要实现序列化接口
     */
    @Test
    public void testSecondLevelCache() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        SqlSession openSession2 = sqlSessionFactory.openSession();
        try{
            //1、
            IEmployeeDao mapper = openSession.getMapper(IEmployeeDao.class);
            IEmployeeDao mapper2 = openSession2.getMapper(IEmployeeDao.class);

            Employee emp01 = mapper.getEmployeeById("4");
            System.out.println(emp01);
            openSession.close();

            //第二次查询是从二级缓存中拿到的数据，并没有发送新的sql
            //mapper2.addEmp(new Employee(null, "aaa", "nnn", "0"));
            Employee emp02 = mapper2.getEmployeeById("4");
            System.out.println(emp02);
            //<cache/>标签中配置readOnly="true":返回true,配置readOnly="false":返回false
            System.out.println(emp01 == emp02);
            openSession2.close();
        }finally{

        }
    }


    /**
     * 和缓存有关的设置/属性：
     * 		1）、cacheEnabled=true：false：关闭缓存（二级缓存关闭）(一级缓存一直可用的)
     * 		2）、每个select标签都有useCache="true"：
     * 				false：不使用缓存（一级缓存依然使用，二级缓存不使用）
     * 		3）、【每个增删改标签的：flushCache="true"：（一级二级都会清除）】
     * 				增删改执行完成后就会清除缓存；
     * 				测试：flushCache="true"：一级缓存就清空了；二级也会被清除；
     * 				查询标签：flushCache="false"：
     * 					如果flushCache=true;每次查询之后都会清空缓存；缓存是没有被使用的；
     * 		4）、sqlSession.clearCache();只是清楚当前session的一级缓存；
     * 		5）、localCacheScope：本地缓存作用域：（一级缓存SESSION）；当前会话的所有数据保存在会话缓存中；
     * 							STATEMENT：可以禁用一级缓存；
     */
    @Test
    public void testCacheSettings() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        SqlSession openSession2 = sqlSessionFactory.openSession();
        try{
            IDepartmentDao mapper = openSession.getMapper(IDepartmentDao.class);
            IDepartmentDao mapper2 = openSession2.getMapper(IDepartmentDao.class);
            Department dept01 = mapper.getDeptById("4");
            System.out.println(dept01);
            openSession.close();

            Department dept02 = mapper2.getDeptById("4");
            System.out.println(dept02);
            System.out.println(dept01 == dept02);
            openSession2.close();
            //第二次查询是从二级缓存中拿到的数据，并没有发送新的sql
        }finally{

        }
    }


    /**
     *  第三方缓存整合：
     *		1）、导入第三方缓存包即可；
     *		2）、导入与第三方缓存整合的适配包；官方有；
     *		3）、mybatis.mapper.xml中使用自定义缓存
     *		<cache type="org.mybatis.mapper.caches.ehcache.EhcacheCache"></cache>
     */
    @Test
    public void testThirdCache() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        SqlSession openSession2 = sqlSessionFactory.openSession();
        try{
            IDepartmentDao mapper = openSession.getMapper(IDepartmentDao.class);
            IDepartmentDao mapper2 = openSession2.getMapper(IDepartmentDao.class);
            Department dept01 = mapper.getDeptById("4");
            System.out.println(dept01);
            openSession.close();

            Department dept02 = mapper2.getDeptById("4");
            System.out.println(dept02);
            System.out.println(dept01 == dept02);
            openSession2.close();
            //第二次查询是从二级缓存中拿到的数据，并没有发送新的sql
        }finally{

        }
    }
}
