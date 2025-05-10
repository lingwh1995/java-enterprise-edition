package org.bluebridge.resultmap.dao;


import org.bluebridge.resultmap.domain.Employee;

import java.util.List;

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
     * 根据id获取List<Employee>对象
     * @param id
     * @return
     */
    List<Employee> getEmployeeListById(String id);

    /**
     * 根据id获取Employee和Department对象(多表联查结果封装到Javabean中)
     * 使用属性级联封装
     * @param id
     * @return
     */
    Employee getEmployeeAndDepartmentByIdUseCascade(String id);

    /**
     * 根据id获取Employee和Department对象(多表联查结果封装到Javabean中)
     * 使用Association
     * @param id
     * @return
     */
    Employee getEmployeeAndDepartmentByIdUseAssociation(String id);

    /**
     * 根据id获取Employee和Department对象(多表联查结果封装到Javabean中)
     * 使用Asscoation进行分步骤查询
     * @param id
     * @return
     */
    Employee getEmployeeAndDepartmentByIdStepUseAssociate(String id);


    /**
     * 根据id获取Employee和Department对象(多表联查结果封装到Javabean中)
     * 使用Asscoation进行分步骤查询+使用discriminator(鉴别器)对数据进行处理
     * @param id
     * @return
     */
    Employee getEmployeeAndDepartmentByIdStepUseDiscriminator(String id);

}
