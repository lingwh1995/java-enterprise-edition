package org.bluebridge.service.impl;

import org.bluebridge.dto.CreateProductDTO;
import org.bluebridge.dto.UpdateProductDTO;
import org.bluebridge.dto.PatchProductDTO;
import org.bluebridge.dto.QueryProductDTO;
import org.bluebridge.entity.ProductDO;
import org.bluebridge.exception.ProductException;
import org.bluebridge.convertor.ProductConvertor;
import org.bluebridge.mapper.ProductMapper;
import org.bluebridge.service.ProductService;
import org.bluebridge.vo.PageInfo;
import org.bluebridge.vo.ProductVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public int createProduct(CreateProductDTO createProductDTO) {
        // 使用 MapStruct 转换DTO为实体
        ProductDO productDO = productConvertor.toProductDO(createProductDTO);
        
        // 保存到数据库
        return productMapper.insertProduct(productDO);
    }
    
    @Override
    public int batchCreateProduct(List<CreateProductDTO> createProductDTOList) {
        // 转换DTO列表为实体列表
        List<ProductDO> productDOList = createProductDTOList.stream()
                .map(productConvertor::toProductDO)
                .collect(Collectors.toList());
        
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
    public int updateProduct(Long id, UpdateProductDTO updateProductDTO) {
        // 1.查询用户是否存在（阿里手册：更新前先查，避免更新不存在的数据）
        ProductDO productDO = productMapper.getProductById(id);
        if (productDO == null) {
            throw new ProductException(404, "商品不存在或已被删除");
        }

        // 2.转换DTO为实体（阿里手册：避免直接操作DTO）
        productDO = productConvertor.toProductDO(updateProductDTO);

        // 3.设置商品ID
        productDO.setId(id);

        // 4.更新商品
        return productMapper.updateProduct(productDO);
    }

    @Override
    public int patchProduct(Long id, PatchProductDTO patchProductDTO) {
        // 1.查询用户是否存在（阿里手册：更新前先查，避免更新不存在的数据）
        ProductDO productDO = productMapper.getProductById(id);
        if (productDO == null) {
            throw new ProductException(404, "商品不存在或已被删除");
        }

        // 2.转为实体
        productDO = productConvertor.toProductDO(patchProductDTO);

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
    public ProductVO getProductByName(String name) {
        ProductDO productDO = productMapper.getProductByName(name);
        if (productDO == null) {
            throw new ProductException(404, "商品不存在");
        }
        // return productMapper.toProductVO(product);
        return null;
    }

    @Override
    public List<ProductVO> listProductByCondition(QueryProductDTO queryDTO) {
        // 构造查询条件
        ProductDO productDO = new ProductDO();
        if (queryDTO.getName() != null && !queryDTO.getName().isEmpty()) {
            productDO.setName(queryDTO.getName());
        }
        if (queryDTO.getStatus() != null) {
            productDO.setStatus(queryDTO.getStatus());
        }
        // 注意：价格区间查询需要在Mapper中特殊处理，这里简化处理

        // 查询商品列表
        List<ProductDO> productDOList = productMapper.listProductByCondition(productDO);

        // 转换为VO列表返回
        // return products.stream().map(productMapper::toProductVO).collect(Collectors.toList());
        return new ArrayList<>();
    }

    @Override
    public List<ProductVO> listProduct() {
        List<ProductDO> productDOList = productMapper.listProduct();
        // return products.stream().map(productMapper::toProductVO).collect(Collectors.toList());
        return new ArrayList<>();
    }

    @Override
    public PageInfo<ProductVO> pageProduct(
                QueryProductDTO queryDTO,
                Integer pageNum,
                Integer pageSize,
                String sortBy,
                String sortOrder) {
        List<ProductVO> allProducts = listProductByCondition(queryDTO);
        
        // 计算分页信息
        int total = allProducts.size();
        int pages = (int) Math.ceil((double) total / pageSize);
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, total);
        
        // 获取当前页数据
        List<ProductVO> pageList = new ArrayList<>();
        if (startIndex < total) {
            pageList = allProducts.subList(startIndex, endIndex);
        }
        
        // 构建分页结果
        PageInfo<ProductVO> pageInfo = new PageInfo<>();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal((long) total);
        pageInfo.setPages(pages);
        pageInfo.setList(pageList);
        pageInfo.setHasNextPage(pageNum < pages);
        pageInfo.setHasPreviousPage(pageNum > 1);
        
        return pageInfo;
    }

}