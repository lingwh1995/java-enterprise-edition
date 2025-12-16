package org.bluebridge.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.bluebridge.dto.ProductCreateDTO;
import org.bluebridge.entity.ProductDO;

import java.util.List;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/13 10:50
 */
public interface ProductMapper {
    
    /**
     * 插入商品
     * @param productDO 商品实体
     * @return 影响行数
     */
    int insertProduct(ProductDO productDO);
    
    /**
     * 批量插入商品
     * @param productDOList 商品列表
     * @return 影响行数
     */
    int batchInsertProduct(@Param("products") List<ProductDO> productDOList);
    
    /**
     * 根据ID删除商品(物理删除)
     * @param id 商品ID
     * @return 影响行数
     */
    int deleteProductById(Long id);
    
    /**
     * 批量删除商品(物理删除)
     * @param ids 商品ID列表
     * @return 影响行数
     */
    int batchDeleteProduct(@Param("ids") List<Long> ids);
    
    /**
     * 根据ID逻辑删除商品
     * @param id 商品ID
     * @return 影响行数
     */
    int logicDeleteProductById(Long id);
    
    /**
     * 批量逻辑删除商品
     * @param ids 商品ID列表
     * @return 影响行数
     */
    int batchLogicDeleteProduct(@Param("ids") List<Long> ids);
    
    /**
     * 根据ID更新商品信息
     * @param productDO 商品实体
     * @return 影响行数
     */
    int updateProduct(ProductDO productDO);
    
    /**
     * 根据ID部分更新商品信息
     * @param productDO 商品实体
     * @return 影响行数
     */
    int patchProduct(ProductDO productDO);
    
    /**
     * 根据ID查询商品
     * @param id 商品ID
     * @return 商品实体
     */
    ProductDO getProductById(Long id);
    
    /**
     * 根据名称查询商品
     * @param name 商品名称
     * @return 商品实体
     */
    List<ProductDO> listProductByName(String name);
    
    /**
     * 根据条件查询商品列表
     * @param productDO 查询条件
     * @return 商品列表
     */
    List<ProductDO> listProductByCondition(ProductDO productDO);
    
    /**
     * 查询所有未删除商品
     * @return 商品列表
     */
    List<ProductDO> listProduct();

}