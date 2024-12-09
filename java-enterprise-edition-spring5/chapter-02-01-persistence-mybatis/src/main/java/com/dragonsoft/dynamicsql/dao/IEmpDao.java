package com.dragonsoft.dynamicsql.dao;


import com.dragonsoft.dynamicsql.domain.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mybatis动态sql
 */
public interface IEmpDao {
    /**
     * 使用<if test=""></if>标签进行条件判断
     * 要求:携带了哪个字段，查询条件就带上这个条件的值
     * @param emp
     * @return
     */
    List<Emp> getEmpsConditionsByIfTag(Emp emp);
    /**
     * 使用<if test=""></if>标签进行条件判断,并使用<where></where>标签封装查询条件
     * 要求:携带了哪个字段，查询条件就带上这个条件的值
     * @param emp
     * @return
     */
    List<Emp> getEmpsConditionsByIfAndWhereTag(Emp emp);
    /**
     * 使用<choose></choose>标签进行条件判断
     * 要求:携带了哪个字段，查询条件就带上这个条件的值
     * @param emp
     * @return
     */
    List<Emp> getEmpsConditionsByChooseTag(Emp emp);
    /**
     * 使用<set></set>标签封装更新操作
     * 要求:携带了哪个字段，查询条件就带上这个条件的值
     * @param emp
     * @return
     */
    int updateEmpsConditionsBySetTage(Emp emp);

    /**
     * 使用<trim></trim>标签封装更新操作
     * 要求:携带了哪个字段，查询条件就带上这个条件的值
     * @param emp
     * @return
     */
    int updateEmpsConditionsByTrimTage1(Emp emp);

    /**
     * 使用<trim></trim>标签封装更新操作
     * 要求:携带了哪个字段，查询条件就带上这个条件的值
     * @param emp
     * @return
     */
    int updateEmpsConditionsByTrimTage2(Emp emp);

    /**
     * 使用<foreach></foreach>标签进行遍历操作
     *      拼接出:SELECT * FROM TBL_EMPLOYEE WHERE ID IN(?,?,?)形式的SQL
     * @param ids
     * @return
     */
    List<Emp> getEmpsConditionsByForeachTag1(@Param("ids") List<String> ids);

    /**
     * 使用<foreach></foreach>标签进行遍历操作：
     *      拼接出:SELECT * FROM TBL_EMPLOYEE WHERE ID IN(?,?,?)形式的SQL
     * @param ids
     * @return
     */
    List<Emp> getEmpsConditionsByForeachTag2(@Param("ids") List<String> ids);

    /**
     * 注意:此程序运行会因为数据类型不匹配而报错,是个坑，要注意
     * 使用<foreach></foreach>标签进行遍历操作
     * @param ids
     * @return
     */
    List<Emp> getEmpsConditionsByForeachTag3(@Param("ids") List<Integer> ids);

    /**
     * 使用<foreach></foreach>标签实现批量保存
     * @param emps
     * @return
     */
    int batchInsertByForeachTag1(@Param("emps") List<Emp> emps);

    /**
     * 使用<foreach></foreach>标签实现批量保存
     * @param emps
     * @return
     */
    int batchInsertByForeachTag2(@Param("emps") List<Emp> emps);
}
