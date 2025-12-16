package org.bluebridge.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.bluebridge.dao.domain.ProductDO;
import org.bluebridge.dto.ProductCreateDTO;
import org.bluebridge.dto.ProductUpdateDTO;
import org.bluebridge.dto.ProductPatchDTO;
import org.bluebridge.dto.ProductQueryDTO;
import org.bluebridge.exception.ProductException;
import org.bluebridge.convertor.ProductConvertor;
import org.bluebridge.dao.mapper.ProductMapper;
import org.bluebridge.service.ProductService;
import org.bluebridge.controller.vo.ProductVO;
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
    
    // 注入ProductMapper
    @Resource
    private ProductMapper productMapper;
    
    // 注入 MapStruct 映射器
    private final ProductConvertor productConvertor = ProductConvertor.INSTANCE;
    
    @Override
    public int createProduct(ProductCreateDTO productCreateDTO) {
        // 使用 MapStruct 转换DTO为实体
        ProductDO productDO = productConvertor.toProductDO(productCreateDTO);
        
        // 保存到数据库
        return productMapper.insertProduct(productDO);
    }
    
    @Override
    public int batchCreateProduct(List<ProductCreateDTO> productCreateDTOList) {
        List<ProductDO> productDOList = productConvertor.toProductDOList(productCreateDTOList);

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
    public int logicDeleteProductById(Long id) {
        // 逻辑删除商品
        return productMapper.logicDeleteProductById(id);
    }

    @Override
    public int batchLogicDeleteProduct(List<Long> ids) {
        return productMapper.batchLogicDeleteProduct(ids);
    }

    @Override
    public int updateProduct(Long id, ProductUpdateDTO productUpdateDTO) {
        // 1.查询用户是否存在（阿里手册：更新前先查，避免更新不存在的数据）
        ProductDO productDO = productMapper.getProductById(id);
        if (productDO == null) {
            throw new ProductException(404, "商品不存在或已被删除");
        }

        // 2.转换DTO为实体（阿里手册：避免直接操作DTO）
        productDO = productConvertor.toProductDO(productUpdateDTO);

        // 3.设置商品ID
        productDO.setId(id);

        // 4.更新商品
        return productMapper.updateProduct(productDO);
    }

    @Override
    public int patchProduct(Long id, ProductPatchDTO productPatchDTO) {
        // 1.查询用户是否存在（阿里手册：更新前先查，避免更新不存在的数据）
        ProductDO productDO = productMapper.getProductById(id);
        if (productDO == null) {
            throw new ProductException(404, "商品不存在或已被删除");
        }

        // 2.转为实体
        productDO = productConvertor.toProductDO(productPatchDTO);

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
        if (productDO.getIsDeleted() == 1) {
            throw new ProductException(404, "商品不存在");
        }
        
        return productConvertor.toProductVO(productDO);
    }
    
    @Override
    public List<ProductVO> listProductByName(String name) {
        List<ProductDO> productDOList = productMapper.listProductByName(name);
        if (productDOList == null) {
            throw new ProductException(404, "商品不存在");
        }
        return productConvertor.toProductVOList(productDOList);
    }

    @Override
    public List<ProductVO> searchProduct(ProductQueryDTO productQueryDTO) {
        // 构造查询条件
        ProductDO productDO = productConvertor.toProductDO(productQueryDTO);

        // 查询商品列表
        List<ProductDO> productDOList = productMapper.searchProduct(productDO);

        // 转换为VO列表返回
        return productConvertor.toProductVOList(productDOList);
    }

    @Override
    public List<ProductVO> listProduct() {
        List<ProductDO> productDOList = productMapper.listProduct();
        return productConvertor.toProductVOList(productDOList);
    }

    @Override
    public PageInfo<ProductVO> pageProduct(
                ProductQueryDTO productQueryDTO,
                Integer pageNum,
                Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductVO> productVOList = searchProduct(productQueryDTO);

        // 将结果转换为PageInfo返回
        return new PageInfo<>(productVOList);
    }

}