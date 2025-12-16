package org.bluebridge.service;

import org.apache.ibatis.annotations.Param;
import org.bluebridge.dto.ProductCreateDTO;
import org.bluebridge.dto.ProductUpdateDTO;
import org.bluebridge.dto.ProductPatchDTO;
import org.bluebridge.dto.ProductQueryDTO;
import org.bluebridge.controller.vo.PageInfo;
import org.bluebridge.controller.vo.ProductVO;

import java.util.List;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/13 11:00
 */
public interface ProductService {

    /**
     * 创建商品
     * @param productCreateDTO
     * @return
     */
    int createProduct(ProductCreateDTO productCreateDTO);
    
    /**
     * 批量创建商品
     * @param productCreateDTOList
     * @return
     */
    int batchCreateProduct(List<ProductCreateDTO> productCreateDTOList);

    /**
     * 根据ID删除商品（逻辑删除）
     * @param id
     * @return
     */
    int deleteProductById(Long id);

    /**
     * 批量删除商品（物理删除）
     * @param ids 商品ID列表
     */
    int batchDeleteProduct(List<Long> ids);

    /**
     * 根据ID逻辑删除商品
     * @param id 商品ID
     */
    int logicDeleteProductById(Long id);

    /**
     * 批量逻辑删除商品
     * @param ids 商品ID列表
     */
    int batchLogicDeleteProduct(List<Long> ids);

    /**
     * 根据ID全量更新商品
     * @param id 商品ID
     * @param productUpdateDTO 商品更新传输对象
     * @return 商品视图对象
     */
    int updateProduct(Long id, ProductUpdateDTO productUpdateDTO);
    
    /**
     * 根据ID部分更新商品
     * @param id 商品ID
     * @param productPatchDTO 商品部分更新传输对象
     * @return 商品视图对象
     */
    int patchProduct(Long id, ProductPatchDTO productPatchDTO);
    
    /**
     * 根据ID获取商品详情
     * @param id 商品ID
     * @return 商品视图对象
     */
    ProductVO getProductById(Long id);
    
    /**
     * 根据唯一条件查询商品（示例：根据名称精确查询）
     * @param name 商品名称
     * @return 商品视图对象
     */
    List<ProductVO> listProductByName(String name);

    /**
     * 根据查询条件获取商品列表
     * @param productQueryDTO 商品查询条件
     * @return 商品视图对象列表
     */
    List<ProductVO> searchProduct(ProductQueryDTO productQueryDTO);

    /**
     * 获取商品列表
     * @return 商品视图对象列表
     */
    List<ProductVO> listProduct();

    /**
     * 分页查询商品
     * @param queryDTO 查询条件
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param sortBy 排序字段
     * @param sortOrder 排序顺序（asc/desc）
     * @return 分页结果
     */
    PageInfo<ProductVO> pageProduct(
            ProductQueryDTO queryDTO,
            Integer pageNum,
            Integer pageSize,
            String sortBy,
            String sortOrder);

}