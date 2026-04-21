package org.bluebridge.cache.dao;


import org.bluebridge.cache.domain.Department;

public interface IDepartmentDao {
    /**
     * 根据id获取Department
     * @param id
     * @return
     */
    Department getDeptById(String id);

}
