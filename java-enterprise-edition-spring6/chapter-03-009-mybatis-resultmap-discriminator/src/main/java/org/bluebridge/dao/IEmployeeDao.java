package org.bluebridge.dao;


import org.bluebridge.domain.Employee;


public interface IEmployeeDao {

    /**
     * 使用ResultMap的Association完成多表关联查询 + 使用discriminator改变封装行为
     * @param id
     * @return
     */
    Employee getEmployeeAndDepartmentByIdUseAssociationDiscriminator(String id);

}
