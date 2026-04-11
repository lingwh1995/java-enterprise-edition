package org.bluebridge.interfacemapper.dao;

import org.bluebridge.interfacemapper.domain.Employee;

public interface IEmployeeDao {
    /**
     * 根据id获取对象
     * @param id
     * @return
     */
    Employee getEmplyeeById(String id);

    /**
     * 根据id获取lastname
     * @param id
     * @return
     */
    String getEmplyeeLastNameById(String id);
}
