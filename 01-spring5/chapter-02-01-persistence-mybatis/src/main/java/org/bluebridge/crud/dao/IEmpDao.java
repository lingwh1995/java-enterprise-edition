package org.bluebridge.crud.dao;


import org.bluebridge.crud.domain.Emp;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Mybatis 增删改
 *
 * 1. 可以返回以下类型数据：void/Integer/Long/Boolean
 * 2. 不需要在 Mapper 中写返回值类型(resultType)
 *
 * @author lingwh
 * @date 2019/3/16 14:08
 */
public interface IEmpDao {

    /**
     * 新增 Emp 对象，返回 void
     */
    void addEmp(Emp emp);

    /**
     * 根据 id 获取对象
     * @param id
     * @return
     */
    Emp getEmpById(String id);

    /**
     * 单条查询结果返回 Map(Map 的 key 是主键)
     * @param id
     * @return
     */
    Map<String,Object> getMapById(String id);

    /**
     * 多条记录查询结果封装到 Lsit<Map> 中
     * @return
     */
    List<Map<String,Object>> getEmpLsitMap();

    /**
     * 多条查询结果返回 Map ，并使用主键作为 Map 的 key
     * @return
     */
    @MapKey("id")
    Map<String,Emp> getEmpsMap();

    /**
     * 根据 id 获取 List<Emp>
     * @return
     */
    List<Emp> getEmpList();

    /**
     * 根据 id 删除对象，返回 Integer
     * @param id
     */
    Integer deleteById(String id);

    /**
     * 根据 id 更新对象，返回 void
     * @param emp
     */
    boolean updateById(Emp emp);

    //---------------------------------------------------------------------------------------------------
    //以上为单个参数，下面为多个参数
    //---------------------------------------------------------------------------------------------------

    /**
     * 根据 id 和 lastName 获取对象
     * 命名参数写法：@Param("id") String id
     * @param id
     * @param lastName
     * @return
     */
    Emp getEmpByIdAndLastName(@Param("id") String id, @Param("lastName") String lastName);

    /**
     * 根据 Emp(使用 POJO 作为参数)获取对象
     * 命名参数写法：@Param("id") String id
     * @param emp
     * @return
     */
    Emp getEmpByEmp(Emp emp);

    /**
     * 根据 Map(使用 Map 作为参数)获取对象
     * 命名参数写法：@Param("id") String id
     * @param map
     * @return
     */
    Emp getEmpByMap(Map<String,String> map);
}
