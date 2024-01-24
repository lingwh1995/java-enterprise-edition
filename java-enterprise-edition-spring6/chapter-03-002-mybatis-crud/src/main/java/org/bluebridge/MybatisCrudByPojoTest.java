package org.bluebridge;

import org.bluebridge.domain.Employee;
import org.bluebridge.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class MybatisCrudByPojoTest {

    /**
     * 使用Map传递参数实现新增功能
     */
    @Test
    public void testMybatisInsertByPojo() {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.openSession();
            Employee employee = new Employee();
            employee.setLastName("zhangsan");
            employee.setEmail("1458687169@qq.com");
            employee.setGender("男");
            int count = sqlSession.insert("crudByPojo.insertEmployee",employee);
            System.out.println("插入几条数据：" + count);
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

    /**
     * 使用Map传递参数实现更新功能
     */
    @Test
    public void testMybatisUpdateByPojo() {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.openSession();
            Employee employee = new Employee();
            employee.setId("7");
            employee.setLastName("zs");
            employee.setEmail("1458687169@qq.com1");
            employee.setGender("男1");
            int count = sqlSession.update("crudByPojo.updateEmployee",employee);
            System.out.println("更新几条数据：" + count);
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

    /**
     * 使用Map传递参数实现删除功能
     */
    @Test
    public void testMybatisDeleteByPojo() {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.openSession();
            int count = sqlSession.delete("crudByPojo.deleteEmployee",6);
            System.out.println("删除几条数据：" + count);
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

    /**
     * 使用Map传递参数实现查询一个Employee功能
     */
    @Test
    public void testMybatisSelectOne() {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.openSession();
            Employee result = (Employee)sqlSession.selectOne("crudByPojo.selectOneEmployee", 7);
            System.out.println("查询到的数据：" + result);
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

    /**
     * 查询多个Employee功能
     */
    @Test
    public void testMybatisSelectList() {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.openSession();
            List<Employee> list = sqlSession.selectList("crudByPojo.selectEmployees");
            System.out.println("查询到的数据：" + list);
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
