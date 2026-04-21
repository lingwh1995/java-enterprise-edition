package org.bluebridge;

import org.bluebridge.dao.IEmployeeDao;
import org.bluebridge.domain.Employee;
import org.bluebridge.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class MybatisResultMapDiscriminatorTest {

    /**
     * 部门编号不等于10时就返回空的部门对象，只有等于10时才会封装部分信息到员工信息中
     * 使用ResultMap的Association完成多表关联查询 + 使用discriminator改变封装行为测试
     */
    @Test
    public void getEmployeeAndDepartmentByIdUseAssociationDiscriminatorTest() {
        SqlSession sqlSession = null;
        try{
            sqlSession = SqlSessionUtil.openSession();
            IEmployeeDao employeeDao = sqlSession.getMapper(IEmployeeDao.class);

            Employee employee = employeeDao.getEmployeeAndDepartmentByIdUseAssociationDiscriminator("2");
            System.out.println("employee:"+employee);

            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
    }

}
