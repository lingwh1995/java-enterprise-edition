package org.bluebridge.service;

import org.bluebridge.dto.CreateProductDTO;
import org.bluebridge.dto.UpdateProductDTO;
import org.bluebridge.dto.PatchProductDTO;
import org.bluebridge.dto.QueryProductDTO;
import org.bluebridge.vo.PageInfo;
import org.bluebridge.vo.ProductVO;

import java.util.List;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/13 11:00
 */
public interface ProductService {

    /**
     * 创建商品
     * @param createProductDTO
     * @return
     */
    int createProduct(CreateProductDTO createProductDTO);
    
    /**
     * 批量创建商品
     * @param createProductDTOList
     * @return
     */
    int batchCreateProduct(List<CreateProductDTO> createProductDTOList);

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
     * @param updateProductDTO 商品传输对象
     * @return 商品视图对象
     */
    int updateProduct(Long id, UpdateProductDTO updateProductDTO);
    
    /**
     * 根据ID部分更新商品
     * @param id 商品ID
     * @param patchProductDTO 商品部分更新传输对象
     * @return 商品视图对象
     */
    int patchProduct(Long id, PatchProductDTO patchProductDTO);
    
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
    ProductVO getProductByName(String name);

    /**
     * 根据查询条件获取商品列表
     * @param queryDTO 查询条件
     * @return 商品视图对象列表
     */
    List<ProductVO> listProductByCondition(QueryProductDTO queryDTO);

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
            QueryProductDTO queryDTO,
            Integer pageNum,
            Integer pageSize,
            String sortBy,
            String sortOrder);

}