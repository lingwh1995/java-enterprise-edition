package com.xa8bit.mybatis_a.test;

/**
 * @author ronin
 */
public interface EmployeeMapper {
    /**
     * 根据id查询Employee
     * @param id
     * @return
     */
    Employee getEmployeeById(String id);

    /**
     * 根据lastName查询Employee
     * @param lastName
     * @return
     */
    Employee getEmployeeByLastName(String lastName);
}
