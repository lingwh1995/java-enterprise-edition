package com.dragonsoft.dynamicsql.test;

import com.dragonsoft.dynamicsql.dao.IEmpDao;
import com.dragonsoft.dynamicsql.domain.Emp;
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
 * 测试mybatis动态sql
 */
public class MybatisDynamicSqlTest {

    /**
     * 获取SqlSession
     * @return
     * @throws IOException
     */
    public SqlSession getSqlSession() throws IOException{
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
        //获取可以自动提交的openSession对象,传入true
        //SqlSession sqlSession = sqlSessionFactory.openSession(true);
        //获取需要手动提交的openSession对象,传入fasle或者什么都不传
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    }

    /**
     * 使用<if></if>标签进行条件判断
     * 要求:携带了哪个字段，查询条件就带上这个条件的值
     * @throws IOException
     */
    @Test
    public void getEmpsConditionsByIfTag() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**4.获取接口实现类对象*/
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**5.查询数据*/
            Emp emp = new Emp();
            emp.setId("1");
            List<Emp> emps = empDaoImpl.getEmpsConditionsByIfTag(emp);
            System.out.println(emps.size());
            System.out.println("------------------------");
            emps.forEach(System.out::println);
            System.out.println("------------------------");
            /**6.手动提交数据*/
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**7.关闭sqlSession*/
            sqlSession.close();
        }
    }

    /**
     * 使用<if></if>标签进行条件判断,<where></where>标签封装查询参数
     * 要求:携带了哪个字段，查询条件就带上这个条件的值
     * @throws IOException
     */
    @Test
    public void getEmpsConditionsByIfAndWhereTag() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**4.获取接口实现类对象*/
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**5.查询数据*/
            Emp emp = new Emp();
            emp.setId("1");
            emp.setLastName("z");
            List<Emp> emps = empDaoImpl.getEmpsConditionsByIfAndWhereTag(emp);
            System.out.println(emps.size());
            System.out.println("------------------------");
            emps.forEach(System.out::println);
            System.out.println("------------------------");
            /**6.手动提交数据*/
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**7.关闭sqlSession*/
            sqlSession.close();
        }
    }

    /**
     * 使用<choose></choose>标签进行条件判断
     * 要求:携带了哪个字段，查询条件就带上这个条件的值
     * @throws IOException
     */
    @Test
    public void getEmpsConditionsByChooseTage() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**4.获取接口实现类对象*/
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**5.查询数据*/
            Emp emp = new Emp();
            //emp.setId("1");
            List<Emp> emps = empDaoImpl.getEmpsConditionsByChooseTag(emp);
            System.out.println(emps.size());
            System.out.println("------------------------");
            emps.forEach(System.out::println);
            System.out.println("------------------------");
            /**6.手动提交数据*/
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**7.关闭sqlSession*/
            sqlSession.close();
        }
    }

    /**
     * 使用<set></set>标签封装更新操作,根据Id进行更新
     * @throws IOException
     */
    @Test
    public void updateEmpsConditionsBySetTage() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**4.获取接口实现类对象*/
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**5.查询数据*/
            Emp emp = new Emp();
            emp.setId("1");
            emp.setGender("8");
            int count = empDaoImpl.updateEmpsConditionsBySetTage(emp);
            System.out.println("------------------------");
            System.out.println("受影响的条数:"+count);
            System.out.println("------------------------");
            /**6.手动提交数据*/
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**7.关闭sqlSession*/
            sqlSession.close();
        }
    }

    /**
     * 使用<trim></trim>标签封装更新操作,根据Id进行更新
     * @throws IOException
     */
    @Test
    public void updateEmpsConditionsByTrimTage1() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**4.获取接口实现类对象*/
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**5.查询数据*/
            Emp emp = new Emp();
            emp.setId("1");
            emp.setGender("8");
            int count = empDaoImpl.updateEmpsConditionsByTrimTage1(emp);
            System.out.println("------------------------");
            System.out.println("受影响的条数:"+count);
            System.out.println("------------------------");
            /**6.手动提交数据*/
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**7.关闭sqlSession*/
            sqlSession.close();
        }
    }

    /**
     * 使用<trim></trim>标签封装更新操作,根据Id进行更新
     * @throws IOException
     */
    @Test
    public void updateEmpsConditionsByTrimTage2() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**4.获取接口实现类对象*/
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**5.查询数据*/
            Emp emp = new Emp();
            emp.setId("1");
            emp.setGender("8");
            int count = empDaoImpl.updateEmpsConditionsByTrimTage2(emp);
            System.out.println("------------------------");
            System.out.println("受影响的条数:"+count);
            System.out.println("------------------------");
            /**6.手动提交数据*/
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**7.关闭sqlSession*/
            sqlSession.close();
        }
    }


    /**
     * 使用<foreach></foreach>实现遍历操作
     *      拼接出:SELECT * FROM TBL_EMPLOYEE WHERE ID IN(?,?,?)形式的SQL
     * @throws IOException
     */
    @Test
    public void getEmpsConditionsByForeachTag1() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**4.获取接口实现类对象*/
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**5.查询数据*/
            List<Emp> emps = empDaoImpl.getEmpsConditionsByForeachTag1(Arrays.asList("1", "2","3"));
            System.out.println("------------------------");
            emps.forEach(System.out::println);
            System.out.println("------------------------");
            /**6.手动提交数据*/
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**7.关闭sqlSession*/
            sqlSession.close();
        }
    }

    /**
     * 使用<foreach></foreach>实现遍历操作
     *      拼接出:SELECT * FROM TBL_EMPLOYEE WHERE ID IN(?,?,?)形式的SQL
     * @throws IOException
     */
    @Test
    public void getEmpsConditionsByForeachTag2() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**4.获取接口实现类对象*/
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**5.查询数据*/
            List<Emp> emps = empDaoImpl.getEmpsConditionsByForeachTag2(Arrays.asList("1", "2","3"));
            System.out.println("------------------------");
            emps.forEach(System.out::println);
            System.out.println("------------------------");
            /**6.手动提交数据*/
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**7.关闭sqlSession*/
            sqlSession.close();
        }
    }

    /**
     * 注意:此程序运行会因为数据类型不匹配而报错,是个坑，要注意
     * 使用<foreach></foreach>实现遍历操作
     * @throws IOException
     */
    @Test
    public void getEmpsConditionsByForeachTag3() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**4.获取接口实现类对象*/
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**5.查询数据*/
            List<Emp> emps = empDaoImpl.getEmpsConditionsByForeachTag3(Arrays.asList(1, 2,3));
            System.out.println("------------------------");
            emps.forEach(System.out::println);
            System.out.println("------------------------");
            /**6.手动提交数据*/
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**7.关闭sqlSession*/
            sqlSession.close();
        }
    }

    /**
     * 使用<foreach></foreach>实现遍历批量保存操作
     * @throws IOException
     */
    @Test
    public void batchInsertByForeachTag1() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**4.获取接口实现类对象*/
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**5.查询数据*/
            List<Emp> emps = Arrays.asList(new Emp("18", "aa", "aa.@163.com", "男"),
                    new Emp("28", "bb", "bb.@163.com", "男"),
                    new Emp("38", "cc", "cc.@163.com", "男"));
            int count = empDaoImpl.batchInsertByForeachTag1(emps);
            System.out.println("------------------------");
            System.out.println("插入的语句条数:"+count);
            System.out.println("------------------------");
            /**6.手动提交数据*/
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**7.关闭sqlSession*/
            sqlSession.close();
        }
    }

    /**
     * 使用<foreach></foreach>实现遍历批量保存操作
     * @throws IOException
     */
    @Test
    public void batchInsertByForeachTag2() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        try {
            /**4.获取接口实现类对象*/
            IEmpDao empDaoImpl = sqlSession.getMapper(IEmpDao.class);
            /**5.查询数据*/
            List<Emp> emps = Arrays.asList(new Emp("18", "aa", "aa.@163.com", "男"),
                    new Emp("28", "bb", "bb.@163.com", "男"),
                    new Emp("38", "cc", "cc.@163.com", "男"));
            int count = empDaoImpl.batchInsertByForeachTag2(emps);
            System.out.println("------------------------");
            System.out.println("插入的语句条数:"+count);
            System.out.println("------------------------");
            /**6.手动提交数据*/
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            /**7.关闭sqlSession*/
            sqlSession.close();
        }
    }


}
