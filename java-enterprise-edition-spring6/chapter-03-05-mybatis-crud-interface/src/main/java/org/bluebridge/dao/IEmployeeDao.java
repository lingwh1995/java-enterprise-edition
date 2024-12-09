package org.bluebridge.dao;

import org.bluebridge.domain.Employee;

import java.util.List;

public interface IEmployeeDao {

    /**
     * 增
     * @param employee
     * @return
     */
    int insertEmployee(Employee employee);

    /**
     * 删
     * @param id
     * @return
     */
    int deleteEmployeeById(String id);

    /**
     * 改
     * @param employee
     * @return
     */
    int updateEmployee(Employee employee);

    /**
     * 查
     * @param id
     * @return
     */
    Employee getEmployeeById(String id);

    /**
     * 查
     * @return
     */
    List<Employee> getEmployees();
}
