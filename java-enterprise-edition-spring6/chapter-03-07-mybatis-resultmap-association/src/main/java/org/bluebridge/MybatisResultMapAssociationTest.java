package org.bluebridge;

import org.bluebridge.dao.IEmployeeDao;
import org.bluebridge.domain.Employee;
import org.bluebridge.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class MybatisResultMapAssociationTest {

    /**
     * 测试使用ResultMap的association完成多表一对一查询
     *      根据id获取employee信息和关联的部门信息
     */
    @Test
    public void getEmployeeAndDepartmentByIdUseAssociationTest() {
        SqlSession sqlSession = null;
        try{
            sqlSession = SqlSessionUtil.openSession();
            IEmployeeDao employeeDao = sqlSession.getMapper(IEmployeeDao.class);

            Employee employee = employeeDao.getEmployeeAndDepartmentByIdUseAssociation("1");
            System.out.println("employee:"+employee);

            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
    }

    /**
     * 测试使用ResultMap的association完成多表一对一查询 + 分布查询
     *      根据id获取employee信息和关联的部门信息
     */
    @Test
    public void getEmployeeAndDepartmentByIdUseAssociationByStepTest() {
        SqlSession sqlSession = null;
        try{
            sqlSession = SqlSessionUtil.openSession();
            IEmployeeDao employeeDao = sqlSession.getMapper(IEmployeeDao.class);

            Employee employee = employeeDao.getEmployeeAndDepartmentByIdUseAssociationByStep("1");
            //System.out.println("employee:"+employee);

            //下面一行代码用于测试association分布查询懒加载
            System.out.println(employee.getDepartment());

            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
    }

}
