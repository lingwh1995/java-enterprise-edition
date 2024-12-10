package com.dragonsoft.cache.dao;



import com.dragonsoft.cache.domain.Employee;

/**
 * 返回结果为resultMap
 */
public interface IEmployeeDao {

    /**
     * 根据id获取Employee对象
     * @param id
     * @return
     */
    Employee getEmployeeById(String id);

    /**
     * 新增Emp对象,返回boolean
     */
    boolean addEmployee(Employee emp);

}
