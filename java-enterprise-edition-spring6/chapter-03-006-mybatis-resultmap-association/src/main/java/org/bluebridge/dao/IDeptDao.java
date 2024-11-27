package org.bluebridge.dao;


import org.bluebridge.domain.Department;

public interface IDeptDao {
    /**
     * 根据deptNo获取Department
     * @param deptNo
     * @return
     */
    Department getDeptByDeptNo(String deptNo);
}
