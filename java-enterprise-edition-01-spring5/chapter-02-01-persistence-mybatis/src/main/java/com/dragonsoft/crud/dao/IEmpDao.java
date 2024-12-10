package com.dragonsoft.crud.dao;


import com.dragonsoft.crud.domain.Emp;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Mybatis增删改:
 *      1.可以返回以下类型数据:void/Integer/Long/Boolean
 *      2.不需要在Mapper中写返回值类型(resultType)
 */
public interface IEmpDao {

    /**
     * 新增Emp对象,返回void
     */
    void addEmp(Emp emp);

    /**
     * 根据id获取对象
     * @param id
     * @return
     */
    Emp getEmpById(String id);

    /**
     * 单条查询结果返回Map(Map的key是主键)
     * @param id
     * @return
     */
    Map<String,Object> getMapById(String id);

    /**
     * 多条记录查询结果封装到Lsit<Map>中
     * @return
     */
    List<Map<String,Object>> getEmpLsitMap();

    /**
     * 多条查询结果返回Map,并使用主键作为Map的key
     * @return
     */
    @MapKey("id")
    Map<String,Emp> getEmpsMap();

    /**
     * 根据id获取List<Emp>
     * @return
     */
    List<Emp> getEmpList();

    /**
     * 根据id删除对象,返回Integer
     * @param id
     */
    Integer deleteById(String id);

    /**
     * 根据id更新对象,返回void
     * @param emp
     */
    boolean updateById(Emp emp);

    //---------------------------------------------------------------------------------------------------
    //以上为单个参数，下面为多个参数
    //---------------------------------------------------------------------------------------------------

    /**
     * 根据id和lastName获取对象
     * 命名参数写法:@Param("id") String id
     * @param id
     * @param lastName
     * @return
     */
    Emp getEmpByIdAndLastName(@Param("id") String id, @Param("lastName") String lastName);

    /**
     * 根据Emp(使用POJO作为参数)获取对象
     * 命名参数写法:@Param("id") String id
     * @param emp
     * @return
     */
    Emp getEmpByEmp(Emp emp);

    /**
     * 根据Map(使用Map作为参数)获取对象
     * 命名参数写法:@Param("id") String id
     * @param map
     * @return
     */
    Emp getEmpByMap(Map<String,String> map);
}
