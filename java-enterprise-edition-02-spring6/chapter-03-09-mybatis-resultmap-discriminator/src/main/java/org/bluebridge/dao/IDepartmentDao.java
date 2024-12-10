package org.bluebridge.dao;


import org.bluebridge.domain.Department;

public interface IDepartmentDao {
    /**
     * 根据deptNo获取Department
     * @param deptNo
     * @return
     */
    Department getDepartmentByDeptNo(String deptNo);
}
