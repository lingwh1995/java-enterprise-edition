package org.bluebridge.service;

import com.github.pagehelper.PageInfo;
import org.bluebridge.common.domain.query.PageQuery;
import org.bluebridge.common.domain.query.Query;
import org.bluebridge.domain.dto.ProductCreateDTO;
import org.bluebridge.domain.dto.ProductUpdateDTO;
import org.bluebridge.domain.dto.ProductPatchDTO;
import org.bluebridge.domain.dto.ProductQueryDTO;
import org.bluebridge.domain.vo.ProductVO;

import java.util.List;

/**
 * @author lingwh
 * @desc 商品服务接口
 * @date 2025/12/13 11:10
 */
public interface ProductService {

    /**
     * 创建商品
     * @param productCreateDTO 商品创建传输对象
     * @return 影响行数
     */
    int createProduct(ProductCreateDTO productCreateDTO);

    /**
     * 批量创建商品
     * @param productCreateDTOList 商品创建传输对象列表
     * @return 影响行数
     */
    int batchCreateProduct(List<ProductCreateDTO> productCreateDTOList);

    /**
     * 根据ID删除商品（物理删除）
     * @param id 商品ID
     * @return 影响行数
     */
    int deleteProductById(Long id);

    /**
     * 批量删除商品（物理删除）
     * @param ids 商品ID列表
     * @return 影响行数
     */
    int batchDeleteProduct(List<Long> ids);

    /**
     * 根据ID逻辑删除商品
     * @param id 商品ID
     * @return 影响行数
     */
    int softDeleteProductById(Long id);

    /**
     * 批量逻辑删除商品
     * @param ids 商品ID列表
     * @return 影响行数
     */
    int batchSoftDeleteProduct(List<Long> ids);

    /**
     * 根据ID全量更新商品
     * @param id               商品ID
     * @param productUpdateDTO 商品更新传输对象
     * @return 影响行数
     */
    int updateProductById(Long id, ProductUpdateDTO productUpdateDTO);

    /**
     * 根据ID部分更新商品
     * @param id               商品ID
     * @param productPatchDTO  商品部分更新传输对象
     * @return 影响行数
     */
    int patchProductById(Long id, ProductPatchDTO productPatchDTO);

    /**
     * 根据ID查询商品
     * @param id 商品ID
     * @return 商品展示对象
     */
    ProductVO getProductById(Long id);

    /**
     * 根据名称精确查询商品列表
     * @param name 商品名称
     * @return 商品展示对象列表
     */
    List<ProductVO> listProductByName(String name);

    /**
     * 获取所有商品列表
     * @return 商品展示对象列表
     */
    List<ProductVO> listProduct();

    /**
     * 根据查询条件搜索商品
     * @param query 查询参数传输对象
     * @return 商品展示对象列表
     */
    List<ProductVO> searchProduct(Query<ProductQueryDTO> query);

    /**
     * 分页查询商品
     * @param pageQuery 分页查询参数传输对象
     * @return 分页信息
     */
    PageInfo<ProductVO> pageProduct(PageQuery<ProductQueryDTO> pageQuery);

}