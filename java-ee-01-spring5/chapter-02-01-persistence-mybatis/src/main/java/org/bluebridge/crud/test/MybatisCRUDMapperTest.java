package org.bluebridge.crud.test;

import org.bluebridge.crud.dao.IEmpDao;
import org.bluebridge.crud.domain.Emp;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 测试mybatis接口式编程
 */
public class MybatisCRUDMapperTest {

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
    public void insert() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**
             * 4.获取接口实现类对象
             */
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**
             * 5.插入数据
             */
            //empDaoImpl.addEmp(new Emp(null,"zhangsan","ronin@163.com","男"));

            /**
             * 保存时如果一个字段的值为null
             * Cause: org.apache.ibatis.type.TypeException: Error setting null for parameter #2 with JdbcType OTHER
             */
            empDaoImpl.addEmp(new Emp(null,null,"ronin@163.com","男"));
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

    /**
     * 根据id删除一个Emp对象,不返回任何值
     * @throws IOException
     */
    @Test
    public void deleteById() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**
             * 4.获取接口实现类对象
             */
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**
             * 5.插入数据
             */
            Integer integer = empDaoImpl.deleteById("1");
            System.out.println(integer);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**
             * 6.关闭sqlSession
             */
            sqlSession.close();
        }
    }


    /**
     * 根据id更新一个Emp对象,不返回任何值
     * @throws IOException
     */
    @Test
    public void updateById() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**
             * 4.获取接口实现类对象
             */
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**
             * 5.更新数据
             */
            boolean b = empDaoImpl.updateById(new Emp("10005", "zhangsan1", "ronin@163.com", "男"));
            System.out.println(b);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**
             * 6.关闭sqlSession
             */
            sqlSession.close();
        }
    }

    /**
     * 根据id查询Emp对象
     * @throws IOException
     */
    @Test
    public void getEmpById() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**
             * 4.获取接口实现类对象
             */
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**
             * 5.根据id获取对象
             */
            Emp emp = empDaoImpl.getEmpById("4");
            System.out.println("emp:"+emp);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**
             * 6.关闭sqlSession
             */
            sqlSession.close();
        }
    }

    /**
     * 单条查询结果返回Map
     * @throws IOException
     */
    @Test
    public void getMapById() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**
             * 4.获取接口实现类对象
             */
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**
             * 5.根据id获取对象
             */
            Map<String,Object> map = empDaoImpl.getMapById("1");
            System.out.println(map);
            System.out.println("map:"+map);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**
             * 6.关闭sqlSession
             */
            sqlSession.close();
        }
    }

    /**
     * 多条查询结果返回Map,并使用主键作为Map的key
     * @throws IOException
     */
    @Test
    public void getEmpsMap() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**
             * 4.获取接口实现类对象
             */
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**
             * 5.根据id获取对象
             */
            Map<String,Emp> map = empDaoImpl.getEmpsMap();
            System.out.println(map);
            System.out.println("map:"+map);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**
             * 6.关闭sqlSession
             */
            sqlSession.close();
        }
    }

    /**
     * 多条查询结果返回List<Map<String,Object>,并使用主键作为Map的key
     * @throws IOException
     */
    @Test
    public void getEmpLsitMap() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**
             * 4.获取接口实现类对象
             */
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**
             * 5.根据id获取对象
             */
            List<Map<String, Object>> map = empDaoImpl.getEmpLsitMap();
            System.out.println(map);
            System.out.println("map:"+map);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**
             * 6.关闭sqlSession
             */
            sqlSession.close();
        }
    }

    /**
     * 根据id查询Emp对象
     * @throws IOException
     */
    @Test
    public void getEmpListById() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**
             * 4.获取接口实现类对象
             */
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**
             * 5.根据id获取对象
             */
            List<Emp> empList = empDaoImpl.getEmpList();
            System.out.println("empList:"+empList);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**
             * 6.关闭sqlSession
             */
            sqlSession.close();
        }
    }

    //---------------------------------------------------------------------------------------------------
    //以上为单个参数，下面为多个参数
    //---------------------------------------------------------------------------------------------------

    /**
     * 根据id和lastName查询Emp对象
     * @throws IOException
     */
    @Test
    public void getEmpByIdAndLastName() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**
             * 4.获取接口实现类对象
             */
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**
             * 5.根据id获取对象
             */
            Emp emp = empDaoImpl.getEmpByIdAndLastName("2","zhangsan");
            System.out.println("emp:"+emp);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**
             * 6.关闭sqlSession
             */
            sqlSession.close();
        }
    }

    /**
     * 根据Emp(使用POJO作为参数)查询Emp对象
     * @throws IOException
     */
    @Test
    public void getEmpByEmp() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**
             * 4.获取接口实现类对象
             */
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**
             * 5.根据Emp获取对象
             */
            Emp emp = empDaoImpl.getEmpByEmp(new Emp("2","zhangsan","",""));
            System.out.println("emp:"+emp);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**
             * 6.关闭sqlSession
             */
            sqlSession.close();
        }
    }

    /**
     * 根据Map(使用Map作为参数)查询Emp对象
     * @throws IOException
     */
    @Test
    public void getEmpByMap() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**
             * 4.获取接口实现类对象
             */
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**
             * 5.根据Emp获取对象
             */
            Map<String,String> param = new HashMap<String,String>();
            param.put("id","2");
            param.put("lastName","zhangsan");

            Emp emp = empDaoImpl.getEmpByMap(param);
            System.out.println("emp:"+emp);
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
