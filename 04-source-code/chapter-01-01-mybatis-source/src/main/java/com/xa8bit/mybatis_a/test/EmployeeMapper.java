package com.xa8bit.mybatis_a.test;

/**
 * 员工 Mapper 接口，提供员工查询方法
 *
 * @author lingwh
 * @date 2025/12/20 11:45
 */
public interface EmployeeMapper {

    /**
     * 根据 id 查询 Employee
     *
     * @param id
     * @return
     */
    Employee getEmployeeById(String id);

    /**
     * 根据 lastName 查询 Employee
     *
     * @param lastName
     * @return
     */
    Employee getEmployeeByLastName(String lastName);
}
