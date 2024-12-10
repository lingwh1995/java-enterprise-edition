package com.dragonsoft.resultmap.dao;

import com.dragonsoft.resultmap.domain.Department;

public interface IDept {
    /**
     * 根据id获取Department
     * @param id
     * @return
     */
    Department getDeptById(String id);

    /**
     * 根据id获取Department和该对象包含的List<Employee>(多表联查结果封装到Javabean中)
     * 使用collection标签
     * @param id
     * @return
     */
    Department getDeptAndEmployeesByIdUseCascade(String id);

    /**
     * 根据id获取Department和该对象包含的List<Employee>(多表联查结果封装到Javabean中)
     * 使用collection标签进行分步骤查询和设置懒加载
     * @param id
     * @return
     */
    Department getDeptAndEmployeesStepById(String id);
}
