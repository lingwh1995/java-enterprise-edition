package org.bluebridge.service.impl;

import org.bluebridge.dto.CreateProductDTO;
import org.bluebridge.dto.UpdateProductDTO;
import org.bluebridge.dto.PatchProductDTO;
import org.bluebridge.dto.QueryProductDTO;
import org.bluebridge.entity.Product;
import org.bluebridge.exception.ProductException;
import org.bluebridge.convertor.ProductConvertor;
import org.bluebridge.mapper.ProductMapper;
import org.bluebridge.service.ProductService;
import org.bluebridge.vo.PageInfo;
import org.bluebridge.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品服务实现类
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
        Product product = productConvertor.toProduct(createProductDTO);
        
        // 保存到数据库
        return productMapper.insertProduct(product);
    }
    
    @Override
    public int batchCreateProduct(List<CreateProductDTO> createProductDTOList) {
        // 转换DTO列表为实体列表
        List<Product> productList = createProductDTOList.stream()
                .map(productConvertor::toProduct)
                .collect(Collectors.toList());
        
        // 批量保存到数据库
        return productMapper.batchInsertProduct(productList);
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
        Product product = productMapper.getProductById(id);
        if (product == null) {
            throw new ProductException(404, "商品不存在");
        }

        // 逻辑删除商品
        return productMapper.logicDeleteProductById(id);
    }

    @Override
    public int batchLogicDeleteProduct(List<Long> ids) {
        return productMapper.batchLogicDeleteProduct(ids);
    }

    @Override
    public int updateProduct(Long id, UpdateProductDTO updateProductDTO) {
        // 查找商品
        Product product = productMapper.getProductById(id);
        if (product == null) {
            throw new ProductException(404, "商品不存在");
        }

        // 检查是否已被逻辑删除
        if (product.getIsDeleted() == 1) {
            throw new ProductException(404, "商品不存在");
        }

        // 更新商品信息
        product = productConvertor.toProduct(updateProductDTO);

        // 保存更新
        return productMapper.updateProduct(product);
    }

    @Override
    public int patchProduct(Long id, PatchProductDTO patchProductDTO) {
        // 查找商品
        Product product = productMapper.getProductById(id);
        if (product == null) {
            throw new ProductException(404, "商品不存在");
        }

        // 检查是否已被逻辑删除
        if (product.getIsDeleted() == 1) {
            throw new ProductException(404, "商品不存在");
        }

        // 部分更新商品信息
        if (patchProductDTO.getName() != null) {
            product.setName(patchProductDTO.getName());
        }
        if (patchProductDTO.getDescription() != null) {
            product.setDescription(patchProductDTO.getDescription());
        }
        if (patchProductDTO.getPrice() != null) {
            product.setPrice(patchProductDTO.getPrice());
        }
        if (patchProductDTO.getStock() != null) {
            product.setStock(patchProductDTO.getStock());
        }
        if (patchProductDTO.getStatus() != null) {
            product.setStatus(patchProductDTO.getStatus());
        }
        product.setUpdateTime(LocalDateTime.now());

        // 保存更新
        return productMapper.patchProduct(product);
    }

    @Override
    public ProductVO getProductById(Long id) {
        Product product = productMapper.getProductById(id);
        if (product == null) {
            throw new ProductException(404, "商品不存在");
        }
        
        // 检查是否已被逻辑删除
        if (product.getIsDeleted() == 1) {
            throw new ProductException(404, "商品不存在");
        }
        
        //return productMapper.toProductVO(product);
        return null;
    }
    
    @Override
    public ProductVO getProductByName(String name) {
        Product product = productMapper.getProductByName(name);
        if (product == null) {
            throw new ProductException(404, "商品不存在");
        }
        // return productMapper.toProductVO(product);
        return null;
    }

    @Override
    public List<ProductVO> listProductByCondition(QueryProductDTO queryDTO) {
        // 构造查询条件
        Product queryProduct = new Product();
        if (queryDTO.getName() != null && !queryDTO.getName().isEmpty()) {
            queryProduct.setName(queryDTO.getName());
        }
        if (queryDTO.getStatus() != null) {
            queryProduct.setStatus(queryDTO.getStatus());
        }
        // 注意：价格区间查询需要在Mapper中特殊处理，这里简化处理

        // 查询商品列表
        List<Product> products = productMapper.listProductByCondition(queryProduct);

        // 转换为VO列表返回
        // return products.stream().map(productMapper::toProductVO).collect(Collectors.toList());
        return new ArrayList<>();
    }

    @Override
    public List<ProductVO> listProduct() {
        List<Product> products = productMapper.listProduct();
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