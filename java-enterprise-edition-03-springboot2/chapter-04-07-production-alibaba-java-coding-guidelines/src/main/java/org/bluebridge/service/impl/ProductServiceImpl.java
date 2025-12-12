package org.bluebridge.service.impl;

import org.bluebridge.dto.CreateProductDTO;
import org.bluebridge.dto.UpdateProductDTO;
import org.bluebridge.dto.PatchProductDTO;
import org.bluebridge.dto.QueryProductDTO;
import org.bluebridge.entity.Product;
import org.bluebridge.exception.ProductException;
import org.bluebridge.service.ProductService;
import org.bluebridge.vo.PageInfo;
import org.bluebridge.vo.ProductVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 商品服务实现类
 */
@Service
public class ProductServiceImpl implements ProductService {
    
    // 模拟数据库存储
    private final ConcurrentHashMap<Long, Product> productMap = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    @Override
    public ProductVO createProduct(CreateProductDTO createProductDTO) {
        // 转换DTO为实体
        Product product = new Product();
        product.setId(idGenerator.getAndIncrement());
        product.setName(createProductDTO.getName());
        product.setDescription(createProductDTO.getDescription());
        product.setPrice(createProductDTO.getPrice());
        product.setStock(createProductDTO.getStock());
        product.setStatus(1); // 默认上架
        product.setCreateTime(LocalDateTime.now());
        product.setUpdateTime(LocalDateTime.now());
        product.setIsDeleted(0); // 未删除
        
        // 保存到模拟数据库
        productMap.put(product.getId(), product);
        
        // 转换为VO返回
        return convertToVO(product);
    }
    
    @Override
    public List<ProductVO> batchCreateProduct(List<CreateProductDTO> createProductDTOList) {
        List<ProductVO> result = new ArrayList<>();
        for (CreateProductDTO createProductDTO : createProductDTOList) {
            result.add(createProduct(createProductDTO));
        }
        return result;
    }

    @Override
    public void deleteProductById(Long id) {
        Product product = productMap.get(id);
        if (product == null) {
            throw new ProductException(404, "商品不存在");
        }

        // 物理删除商品
        productMap.remove(id);
    }

    @Override
    public void batchDeleteProduct(List<Long> ids) {
        for (Long id : ids) {
            deleteProductById(id);
        }
    }

    @Override
    public void logicDeleteProductById(Long id, Integer isDeleted) {
        Product product = productMap.get(id);
        if (product == null) {
            throw new ProductException(404, "商品不存在");
        }

        // 逻辑删除商品
        product.setIsDeleted(isDeleted);
        product.setUpdateTime(LocalDateTime.now());
        productMap.put(id, product);
    }

    @Override
    public void batchLogicDeleteProduct(List<Long> ids, Integer isDeleted) {
        for (Long id : ids) {
            logicDeleteProductById(id, isDeleted);
        }
    }

    @Override
    public ProductVO updateProduct(Long id, UpdateProductDTO productDTO) {
        // 查找商品
        Product product = productMap.get(id);
        if (product == null) {
            throw new ProductException(404, "商品不存在");
        }

        // 检查是否已被逻辑删除
        if (product.getIsDeleted() == 1) {
            throw new ProductException(404, "商品不存在");
        }

        // 更新商品信息
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setUpdateTime(LocalDateTime.now());

        // 保存更新
        productMap.put(id, product);

        // 转换为VO返回
        return convertToVO(product);
    }

    @Override
    public ProductVO patchProduct(Long id, PatchProductDTO patchProductDTO) {
        // 查找商品
        Product product = productMap.get(id);
        if (product == null) {
            throw new ProductException(404, "商品不存在");
        }

        // 检查是否已被逻辑删除
        if (product.getIsDeleted() == 1) {
            throw new ProductException(404, "商品不存在");
        }

        // 部分更新商品信息（只更新非null字段）
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
        productMap.put(id, product);

        // 转换为VO返回
        return convertToVO(product);
    }

    @Override
    public ProductVO getProductById(Long id) {
        Product product = productMap.get(id);
        if (product == null) {
            throw new ProductException(404, "商品不存在");
        }
        
        // 检查是否已被逻辑删除
        if (product.getIsDeleted() == 1) {
            throw new ProductException(404, "商品不存在");
        }
        
        return convertToVO(product);
    }
    
    @Override
    public ProductVO getProductByName(String name) {
        for (Product product : productMap.values()) {
            if (name.equals(product.getName()) && product.getIsDeleted() == 0) {
                return convertToVO(product);
            }
        }
        throw new ProductException(404, "商品不存在");
    }

    @Override
    public List<ProductVO> listProductByCondition(QueryProductDTO queryDTO) {
        return productMap.values().stream()
                .filter(product -> product.getIsDeleted() == 0) // 过滤掉已逻辑删除的商品
                .filter(product -> {
                    // 名称模糊匹配
                    if (queryDTO.getName() != null && !queryDTO.getName().isEmpty()) {
                        if (!product.getName().contains(queryDTO.getName())) {
                            return false;
                        }
                    }
                    // 价格区间过滤
                    if (queryDTO.getMinPrice() != null && product.getPrice().compareTo(queryDTO.getMinPrice()) < 0) {
                        return false;
                    }
                    if (queryDTO.getMaxPrice() != null && product.getPrice().compareTo(queryDTO.getMaxPrice()) > 0) {
                        return false;
                    }
                    // 状态过滤
                    if (queryDTO.getStatus() != null && !queryDTO.getStatus().equals(product.getStatus())) {
                        return false;
                    }
                    return true;
                })
                .sorted(getComparator(queryDTO)) // 排序
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductVO> listProduct() {
        return productMap.values().stream()
                .filter(product -> product.getIsDeleted() == 0) // 过滤掉已逻辑删除的商品
                .map(this::convertToVO)
                .collect(Collectors.toList());
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
    
    /**
     * 根据查询条件获取比较器
     * @param queryDTO 查询条件
     * @return 比较器
     */
    private Comparator<Product> getComparator(QueryProductDTO queryDTO) {
        Comparator<Product> comparator = Comparator.comparing(Product::getId); // 默认按ID排序
        
        if (queryDTO.getSortBy() != null && !queryDTO.getSortBy().isEmpty()) {
            switch (queryDTO.getSortBy()) {
                case "createTime":
                    comparator = Comparator.comparing(Product::getCreateTime);
                    break;
                case "price":
                    comparator = Comparator.comparing(Product::getPrice);
                    break;
                default:
                    comparator = Comparator.comparing(Product::getId);
                    break;
            }
        }
        
        // 如果是降序，则反转比较器
        if ("desc".equalsIgnoreCase(queryDTO.getSortOrder())) {
            comparator = comparator.reversed();
        }
        
        return comparator;
    }
    
    /**
     * 将Product实体转换为ProductVO视图对象
     * @param product 商品实体
     * @return 商品视图对象
     */
    private ProductVO convertToVO(Product product) {
        ProductVO productVO = new ProductVO();
        productVO.setId(product.getId());
        productVO.setName(product.getName());
        productVO.setDescription(product.getDescription());
        productVO.setPrice(product.getPrice());
        productVO.setStock(product.getStock());
        productVO.setStatus(product.getStatus());
        productVO.setCreateTime(product.getCreateTime());
        productVO.setUpdateTime(product.getUpdateTime());
        productVO.setIsDeleted(product.getIsDeleted());
        return productVO;
    }
    
    // 初始化一些测试数据
    {
        // 添加几个测试商品
        Product product1 = new Product();
        product1.setId(idGenerator.getAndIncrement());
        product1.setName("苹果手机");
        product1.setDescription("最新款苹果手机");
        product1.setPrice(new BigDecimal("8999.00"));
        product1.setStock(100);
        product1.setStatus(1);
        product1.setCreateTime(LocalDateTime.now());
        product1.setUpdateTime(LocalDateTime.now());
        product1.setIsDeleted(0);
        productMap.put(product1.getId(), product1);
        
        Product product2 = new Product();
        product2.setId(idGenerator.getAndIncrement());
        product2.setName("华为手机");
        product2.setDescription("最新款华为手机");
        product2.setPrice(new BigDecimal("7999.00"));
        product2.setStock(50);
        product2.setStatus(1);
        product2.setCreateTime(LocalDateTime.now());
        product2.setUpdateTime(LocalDateTime.now());
        product2.setIsDeleted(0);
        productMap.put(product2.getId(), product2);
    }
}