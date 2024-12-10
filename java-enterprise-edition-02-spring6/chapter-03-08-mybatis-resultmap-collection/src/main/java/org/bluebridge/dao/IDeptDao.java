package org.bluebridge.dao;


import org.bluebridge.domain.Department;

public interface IDeptDao {
    /**
     * 使用ResultMap的Collecion完成多表一对多关联查询
     *      根据id获取Department信息和该部门所有员工信息
     * @param id
     * @return
     */
    Department getDeptAndEmployeesByDeptIdUseCollection(String id);

    /**
     * 使用ResultMap的Collecion完成多表一对多关联查询 + 分步查询
     *      根据id获取Department信息和该部门所有员工信息
     * @param id
     * @return
     */
    Department getDeptAndEmployeesByDeptIdUseCollectionByStep(String id);
}
