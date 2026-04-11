package org.bluebridge.multiplexsql.dao;


import org.bluebridge.multiplexsql.domain.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mybatis抽取可重用sql片段
 */
public interface IEmpDao {

    int batchInsertByCommonsql(@Param("emps") List<Emp> emps);
}
