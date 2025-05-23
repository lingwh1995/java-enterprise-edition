package org.bluebridge.builtinparam.dao;


import org.bluebridge.builtinparam.domain.Emp;

import java.util.List;

/**
 * Mybatis两个内置参数
 */
public interface IEmpDao {

    /**
     * 测试mybatis内置参数
     * @param employee
     * @return
     */
    public List<Emp> getEmpsTestInnerParameter(Emp employee);
}
