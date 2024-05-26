package org.bluebridge.dao;


import org.bluebridge.domain.Employee;

import java.util.List;


public interface IEmployeeDao {

    /**
     * 根据deptNo查询多个Employee对象
     * @param deptNo
     * @return
     */
     List<Employee> getEmployeesByDeptNo(String deptNo);

}
