package org.bluebridge.mapper;

import org.apache.ibatis.annotations.Param;
import org.bluebridge.domain.Car;

import java.util.List;
import java.util.Map;

public interface CarMapper {

    /**
     * 使用 #{} 设置参数
     * @param id
     * @return
     */
    Car selectCarByIdSetParamsUseHashSymbol(Long id);

    /**
     * 使用 ${} 设置参数
     * @param id
     * @return
     */
    Car selectCarByIdSetParamsUseDollarSymbol(Long id);

    /**
     * 使用 ${} 设置排序条件
     * @param sortOrder 排序条件
     * @return
     */
    List<Car> selectAllCarsSetParamsUseDollarSymbol(String sortOrder);

    /**
     * 模糊查询实现方式一：使用mysql的concat()函数实现模糊查询
     * @param brand
     * @return
     */
    List<Car> selectCarByBrandFuzzyQueryUseMysqlConcat(String brand);

    /**
     * 模糊查询实现方式二：使用 双引号 + #{} 拼接实现模糊查询
     * @param brand
     * @return
     */
    List<Car> selectCarByBrandFuzzyQueryUseDoubleQuotationMarkAndHashSymbolConcat(String brand);

    /**
     * 模糊查询实现方式三：使用 单引号 + || + #{} 拼接实现模糊查询
     * @param brand
     * @return
     */
    List<Car> selectCarByBrandFuzzyQueryUseSingleQuotationMarkAndHashSymbolConcat(String brand);

    /**
     * 模糊查询实现方式四：使用 mybatis的bind标签 拼接实现模糊查询
     * @param brand
     * @return
     */
    List<Car> selectCarByBrandFuzzyQueryUseMybatisBindTagConcat(String brand);

    /**
     * 模糊查询实现方式五：直接传入拼接好的模糊查询参数 实现模糊查询
     * @param brand
     * @return
     */
    List<Car> selectCarByBrandFuzzyQueryUseCompleteFuzzyQueryParam(String brand);

    /**
     * Mysql插入数据时设置主键方式一(写法一): 使用Mysql自动设置主键，sql语句中不用出现主键
     * @param car
     * @return
     */
    int insertUseMysqlAutoSetPrimaryKeyWhenInsert_1(Car car);

    /**
     * Mysql插入数据时设置主键方式一(写法二): 使用Mysql自动设置主键，sql语句中不用出现主键
     * @param car
     * @return
     */
    int insertUseMysqlAutoSetPrimaryKeyWhenInsert_2(Car car);

    /**
     * Mysql插入数据时设置主键方式二(写法一): 使用Mybatis读取Mysql生成的主键，在sql语句中使用，sql语句中要出现主键
     * @param car
     * @return
     */
    int insertUseMybatisReadMysqlGeneratedPrimaryKeyWhenInsert_1(Car car);

    /**
     * Mysql插入数据时设置主键方式二(写法二): 使用Mybatis读取Mysql生成的主键，在sql语句中使用，sql语句中要出现主键
     * @param car
     * @return
     */
    int insertUseMybatisReadMysqlGeneratedPrimaryKeyWhenInsert_2(Car car);

    /**
     * Mybatis 使用@Param传递多个参数
     * @param brand
     * @param guidePrice
     * @return
     */
    List<Car> selectCarByBrandAndGuidePriceTransferMultiplyParamsUseParamAnnotation(@Param("brand") String brand, @Param("guidePrice") String guidePrice);

    /**
     * Mybatis 使用Map传递多个参数
     * @param params
     * @return
     */
    List<Car> selectCarByBrandAndGuidePriceTransferMultiplyParamsUseMap(Map<String,String> params);

    /**
     * Mybatis 使用Map传递多个参数
     * @param params
     * @return
     */
    List<Car> selectCarByBrandAndGuidePriceTransferMultiplyParamsUseParamAnnotationMap(@Param("params") Map<String,String> params);


    /**
     * Mybatis 读取使用@Param传递的多个参数
     * @param brand
     * @param guidePrice
     * @return
     */
    List<Car> selectCarByBrandAndGuidePriceReadTransferMultiplyParamsUseParamAnnotation(@Param("brand") String brand, @Param("guidePrice") String guidePrice);


    /**
     * Mybatis 读取使用Map传递的多个参数
     * @param params
     * @return
     */
    List<Car> selectCarByBrandAndGuidePriceReadTransferMultiplyParamsUseMap(Map<String,String> params);

    /**
     * 根据 id 删除汽车信息
     *
     * @param id 汽车信息对应的 id
     * @return 影响数据库中的条数
     */
    int deleteById(Long id);

    /**
     * 修改汽车信息
     *
     * @param car 修改之后的汽车信息
     * @return 影响数据库中的条数
     */
    int update(Car car);

    /**
     * 查询所有的汽车信息
     *
     * @return 所有的汽车信息
     */
    List<Car> selectAll();

}
