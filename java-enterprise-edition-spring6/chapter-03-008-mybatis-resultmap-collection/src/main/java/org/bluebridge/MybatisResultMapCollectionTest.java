package org.bluebridge;

import org.bluebridge.dao.IDeptDao;
import org.bluebridge.domain.Department;
import org.bluebridge.domain.Employee;
import org.bluebridge.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class MybatisResultMapCollectionTest {

    /**
     *  使用ResultMap的Collecion完成多表一对多关联查询
     *      根据id获取Department信息和该部门所有员工信息
     */
    @Test
    public void getDeptAndEmployeesByDeptIdUseCollectionTest() {
        SqlSession sqlSession = null;
        try{
            sqlSession = SqlSessionUtil.openSession();
            IDeptDao deptDao = sqlSession.getMapper(IDeptDao.class);

            Department dept = deptDao.getDeptAndEmployeesByDeptIdUseCollection("1");
            System.out.println("dept:"+dept);

            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
    }

    /**
     *  测试使用ResultMap的Collecion完成多表一对多关联查询 + 分步查询
     *      根据id获取Department信息和该部门所有员工信息
     */
    @Test
    public void getDeptAndEmployeesByDeptIdUseCollectionByStepTest() {
        SqlSession sqlSession = null;
        try{
            sqlSession = SqlSessionUtil.openSession();
            IDeptDao deptDao = sqlSession.getMapper(IDeptDao.class);

            Department dept = deptDao.getDeptAndEmployeesByDeptIdUseCollectionByStep("1");
            //System.out.println(dept);

            //下面代码用来测试懒加载
            List<Employee> employees = dept.getEmployees();
            System.out.println("employees:" + employees);

            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
    }


}
