package org.bluebridge.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.bluebridge.common.constant.SoftDeleteConstant;
import org.bluebridge.common.converter.ProductConverter;
import org.bluebridge.common.query.PageQuery;
import org.bluebridge.common.query.Query;
import org.bluebridge.entity.ProductDO;
import org.bluebridge.dto.ProductCreateDTO;
import org.bluebridge.dto.ProductUpdateDTO;
import org.bluebridge.dto.ProductPatchDTO;
import org.bluebridge.dto.ProductQueryDTO;
import org.bluebridge.common.exception.ProductException;
import org.bluebridge.dao.ProductMapper;
import org.bluebridge.service.ProductService;
import org.bluebridge.vo.ProductVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/13 11:10
 */
@Service
public class ProductServiceImpl implements ProductService {
    
    @Resource
    private ProductMapper productMapper;

    @Resource
    private ProductConverter productConverter;

    @Override
    public int createProduct(ProductCreateDTO productCreateDTO) {
        // 使用 MapStruct 转换DTO为实体
        ProductDO productDO = productConverter.toProductDO(productCreateDTO);
        
        // 保存到数据库
        return productMapper.insertProduct(productDO);
    }
    
    @Override
    public int batchCreateProduct(List<ProductCreateDTO> productCreateDTOList) {
        List<ProductDO> productDOList = productConverter.toProductDOList(productCreateDTOList);

        // 检查列表是否为空
        if(productDOList.isEmpty()){
            throw new ProductException(400, "商品列表不能为空");
        }

        // 批量保存到数据库
        return productMapper.batchInsertProduct(productDOList);
    }

    @Override
    public int deleteProductById(Long id) {
        return productMapper.deleteProductById(id);
    }

    @Override
    public int batchDeleteProduct(List<Long> ids) {
        return productMapper.batchDeleteProduct(ids);
    }

    @Override
    public int softDeleteProductById(Long id) {
        // 逻辑删除商品
        return productMapper.logicDeleteProductById(id);
    }

    @Override
    public int batchSoftDeleteProduct(List<Long> ids) {
        return productMapper.batchLogicDeleteProduct(ids);
    }

    @Override
    public int updateProductById(Long id, ProductUpdateDTO productUpdateDTO) {
        // 1.查询用户是否存在（阿里手册：更新前先查，避免更新不存在的数据）
        ProductDO productDO = productMapper.getProductById(id);
        if (productDO == null) {
            throw new ProductException(404, "商品不存在或已被删除");
        }

        // 2.转换DTO为实体（阿里手册：避免直接操作DTO）
        productDO = productConverter.toProductDO(productUpdateDTO);

        // 3.设置商品ID
        productDO.setId(id);

        // 4.更新商品
        return productMapper.updateProduct(productDO);
    }

    @Override
    public int patchProductById(Long id, ProductPatchDTO productPatchDTO) {
        // 1.查询用户是否存在（阿里手册：更新前先查，避免更新不存在的数据）
        ProductDO productDO = productMapper.getProductById(id);
        if (productDO == null) {
            throw new ProductException(404, "商品不存在或已被删除");
        }

        // 2.转为实体
        productDO = productConverter.toProductDO(productPatchDTO);

        // 3.设置商品ID
        productDO.setId(id);

        // 4.更新商品
        return productMapper.patchProduct(productDO);
    }

    @Override
    public ProductVO getProductById(Long id) {
        ProductDO productDO = productMapper.getProductById(id);
        if (productDO == null) {
            throw new ProductException(404, "商品不存在");
        }
        
        // 检查是否已被逻辑删除
        if (productDO.getIsDeleted() == SoftDeleteConstant.DELETED_VALUE) {
            throw new ProductException(404, "商品不存在");
        }
        
        return productConverter.toProductVO(productDO);
    }
    
    @Override
    public List<ProductVO> listProductByName(String name) {
        List<ProductDO> productDOList = productMapper.listProductByName(name);
        if(productDOList.isEmpty()){
            throw new ProductException(404, "商品不存在");
        }
        return productConverter.toProductVOList(productDOList);
    }

    @Override
    public List<ProductVO> searchProduct(Query<ProductQueryDTO> query) {
        // 查询商品列表
        List<ProductDO> productDOList = productMapper.searchProduct(query);

        // 检查列表是否为空
        if(productDOList.isEmpty()){
            throw new ProductException(404, "商品不存在");
        }

        // 转换为VO列表返回
        return productConverter.toProductVOList(productDOList);
    }

    @Override
    public List<ProductVO> listProduct() {
        List<ProductDO> productDOList = productMapper.listProduct();

        // 检查列表是否为空
        if(productDOList.isEmpty()){
            throw new ProductException(404, "商品不存在");
        }

        return productConverter.toProductVOList(productDOList);
    }

    @Override
    public PageInfo<ProductVO> pageProduct(PageQuery<ProductQueryDTO> pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());

        // 把分页查询参数转换为查询参数
        Query<ProductQueryDTO> query = Query.<ProductQueryDTO>builder()
                .query(pageQuery.getQuery())
                .sortList(pageQuery.getSortList())
                .build();

        List<ProductVO> productVOList = searchProduct(query);

        // 将结果转换为PageInfo返回
        return new PageInfo<>(productVOList);
    }

}