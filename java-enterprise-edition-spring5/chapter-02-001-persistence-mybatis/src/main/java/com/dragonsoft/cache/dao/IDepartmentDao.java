package com.dragonsoft.cache.dao;


import com.dragonsoft.cache.domain.Department;

public interface IDepartmentDao {
    /**
     * 根据id获取Department
     * @param id
     * @return
     */
    Department getDeptById(String id);

}
