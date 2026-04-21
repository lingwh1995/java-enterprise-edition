package org.bluebridge;

import org.bluebridge.dao.IEmployeeDao;
import org.bluebridge.domain.Employee;
import org.bluebridge.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class MybatisCrudInterfaceTest {

    /**
     * 接口式编程
     *      新增测试
     */
    @Test
    public void insertTest() {
        SqlSession sqlSession = null;
        try{
            sqlSession = SqlSessionUtil.openSession();
            IEmployeeDao employeeDao = sqlSession.getMapper(IEmployeeDao.class);

            Employee employee = new Employee();
            employee.setLastName("lisi");
            employee.setEmail("2926517283@qq.com");
            employee.setGender("男");
            employee.setDeptNo("80");
            
            int i = employeeDao.insertEmployee(employee);
            System.out.println("受影响的数据:" + i);

            sqlSession.commit();
        }catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
    }

    /**
     * 接口式编程
     *      删除测试
     */
    @Test
    public void deleteTest() {
        SqlSession sqlSession = null;
        try{
            sqlSession = SqlSessionUtil.openSession();
            IEmployeeDao employeeDao = sqlSession.getMapper(IEmployeeDao.class);

            int i = employeeDao.deleteEmployeeById("7");
            System.out.println("受影响的数据:" + i);

            sqlSession.commit();
        }catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
    }

    /**
     * 接口式编程
     *      修改测试
     */
    @Test
    public void updateTest() {
        SqlSession sqlSession = null;
        try{
            sqlSession = SqlSessionUtil.openSession();
            IEmployeeDao employeeDao = sqlSession.getMapper(IEmployeeDao.class);

            Employee employee = employeeDao.getEmployeeById("1");
            employee.setLastName("修改后的姓名");
            int i = employeeDao.updateEmployee(employee);
            System.out.println("受影响的数据:" + i);

            sqlSession.commit();
        }catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
    }

    /**
     * 接口式编程
     *      查询单个测试
     */
    @Test
    public void selectOneTest() {
        SqlSession sqlSession = null;
        try{
            sqlSession = SqlSessionUtil.openSession();
            IEmployeeDao employeeDao = sqlSession.getMapper(IEmployeeDao.class);

            Employee employee = employeeDao.getEmployeeById("1");
            System.out.println(employee);

            sqlSession.commit();
        }catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
    }

    /**
     * 接口式编程
     *      查询多个测试
     */
    @Test
    public void selectListTest() {
        SqlSession sqlSession = null;
        try{
            sqlSession = SqlSessionUtil.openSession();
            IEmployeeDao employeeDao = sqlSession.getMapper(IEmployeeDao.class);

            List<Employee> employees = employeeDao.getEmployees();
            System.out.println(employees);

            sqlSession.commit();
        }catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
    }

}
