package org.bluebridge.mapper;

import org.bluebridge.domain.Car;

import java.util.List;

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
     * 新增 car
     *
     * @param car 需要新增的 car 信息
     * @return 影响数据库中的条数
     */
    int insert(Car car);

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
