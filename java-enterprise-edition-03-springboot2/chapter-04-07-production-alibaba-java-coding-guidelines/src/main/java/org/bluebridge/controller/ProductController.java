package org.bluebridge.controller;

import org.bluebridge.dto.CreateProductDTO;
import org.bluebridge.dto.UpdateProductDTO;
import org.bluebridge.dto.PatchProductDTO;
import org.bluebridge.dto.QueryProductDTO;
import org.bluebridge.service.ProductService;
import org.bluebridge.vo.PageInfo;
import org.bluebridge.vo.ProductVO;
import org.bluebridge.vo.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品管理控制器（阿里规范：类名=资源名+Controller，如ProductController）
 * 路径规范：/api/资源复数（版本+复数资源，阿里微服务规范）
 */
@Validated
@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Resource
    private ProductService productService;
    
    /**
     * 创建商品（阿里规范：方法名=create+资源名，如createProduct）
     * URL：/api/products
     * 
     * @param createProductDTO 商品传输对象
     * @return 统一响应结果
     */
    @PostMapping
    public Result<Integer> createProduct(@Valid @RequestBody CreateProductDTO createProductDTO) {
        int i = productService.createProduct(createProductDTO);
        return Result.success(i, "商品创建成功");
    }
    
    /**
     * 批量创建商品（阿里规范：方法名=batch+动词+资源名，如batchCreateProduct）
     * URL：/api/products/batch
     * 
     * @param createProductDTOList 商品传输对象列表
     * @return 统一响应结果
     */
    @PostMapping("/batch")
    public Result<Integer> batchCreateProduct(@Valid @RequestBody List<CreateProductDTO> createProductDTOList) {
        int i = productService.batchCreateProduct(createProductDTOList);
        return Result.success(i, "商品批量创建成功");
    }

    /**
     * 根据ID删除商品（物理删除）（阿里规范：方法名=delete+资源名+By+主键，如deleteProductById）
     * URL：/api/products/1
     *
     * @param id 商品ID
     * @return 统一响应结果
     */
    @DeleteMapping("/{id}")
    public Result<Integer> deleteProductById(
            @PathVariable @NotNull(message = "商品ID不能为空")
            @Min(value = 1, message = "商品ID必须大于0") Long id) {
        int i = productService.deleteProductById(id);
        return Result.success(i, "商品删除成功");
    }

    /**
     * 批量删除商品（物理删除）（阿里规范：方法名=batchDelete+资源名，如batchDeleteProduct）
     * URL：/api/products
     *
     * @param ids 商品ID列表
     * @return 统一响应结果
     */
    @DeleteMapping("/batch")
    public Result<Integer> batchDeleteProduct(@RequestBody List<Long> ids) {
        int i = productService.batchDeleteProduct(ids);
        return Result.success(i, "商品批量删除成功");
    }

    /**
     * 根据ID逻辑删除商品（阿里规范：方法名=patch+资源名+状态相关词汇，如patchProductStatus）
     * URL：/api/products/1/status
     *
     * @param id 商品ID
     * @return 统一响应结果
     */
    @PatchMapping("/{id}/status")
    public Result<Integer> logicDeleteProductById(@PathVariable Long id) {
        int i = productService.logicDeleteProductById(id);
        return Result.success(i, "根据ID逻辑删除商品成功");
    }

    /**
     * 批量逻辑删除商品（阿里规范：方法名=batchPatch+资源名+状态相关词汇，如batchPatchProductStatus）
     * URL：/api/products/batch/status
     *
     * @param ids 商品ID列表
     * @return 统一响应结果
     */
    @DeleteMapping("/batch/status")
    public Result<Integer> batchLogicDeleteProduct(@RequestBody List<Long> ids) {
        int i = productService.batchLogicDeleteProduct(ids);
        return Result.success(i,"商品批量逻辑删除成功");
    }

    /**
     * 根据ID全量更新商品（阿里规范：方法名=update+资源名+By+主键，如updateProductById）
     * URL：/api/products/1
     * 
     * @param id 商品ID
     * @param updateProductDTO 商品传输对象
     * @return 统一响应结果
     */
    @PutMapping("/{id}")
    public Result<Void> updateProduct(
            @PathVariable @NotNull(message = "商品ID不能为空") 
            @Min(value = 1, message = "商品ID必须大于0") Long id, 
            @Valid @RequestBody UpdateProductDTO updateProductDTO) {
        int i = productService.updateProduct(id, updateProductDTO);
        return Result.success("商品更新成功");
    }
    
    /**
     * 根据ID部分更新商品（阿里规范：方法名=patch+资源名，如patchProduct）
     * URL：/api/products/1
     * 
     * @param id 商品ID
     * @param patchProductDTO 商品部分更新传输对象
     * @return 统一响应结果
     */
    @PatchMapping("/{id}")
    public Result<Integer> patchProduct(
            @PathVariable @NotNull(message = "商品ID不能为空") 
            @Min(value = 1, message = "商品ID必须大于0") Long id, 
            @Valid @RequestBody PatchProductDTO patchProductDTO) {
        int i = productService.patchProduct(id, patchProductDTO);
        return Result.success(i, "商品部分更新成功");
    }
    
    /**
     * 主键查询-根据ID查询商品（阿里规范：方法名=get+资源名+By+主键，如getProductById）
     * URL：/api/products/1
     * 
     * @param id 商品ID
     * @return 统一响应结果
     */
    @GetMapping("/{id}")
    public Result<ProductVO> getProductById(
            @PathVariable @NotNull(message = "商品ID不能为空") 
            @Min(value = 1, message = "商品ID必须大于0") Long id) {
        ProductVO productVO = productService.getProductById(id);
        return Result.success(productVO, "查询成功");
    }
    
    /**
     * 唯一条件查询-根据名称精确查询商品（阿里规范：方法名=get+资源名+By+条件，如getProductByName）
     * URL：/api/products/by-name?name=商品名称
     * 
     * @param name 商品名称
     * @return 统一响应结果
     */
    @GetMapping("/by-name")
    public Result<ProductVO> getProductByName(@RequestParam String name) {
        ProductVO productVO = productService.getProductByName(name);
        return Result.success(productVO, "查询成功");
    }
    
    /**
     * 列表查询-获取所有商品（阿里规范：方法名=list+资源名，如listProduct）
     * URL：/api/products
     * 
     * @return 统一响应结果
     */
    @GetMapping
    public Result<List<ProductVO>> listProduct() {
        List<ProductVO> products = productService.listProduct();
        return Result.success(products, "查询成功");
    }
    
    /**
     * 条件查询-根据查询条件获取商品列表
     * URL：/api/products
     * 
     * @param name 商品名称（模糊匹配）
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param status 商品状态
     * @param sortBy 排序字段
     * @param sortOrder 排序方式
     * @return 统一响应结果
     */
    @GetMapping("/search")
    public Result<List<ProductVO>> listProduct(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DecimalMin("0.0") BigDecimal minPrice,
            @RequestParam(required = false) @DecimalMin("0.0") BigDecimal maxPrice,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) @Pattern(regexp = "createTime|price") String sortBy,
            @RequestParam(required = false) @Pattern(regexp = "asc|desc") String sortOrder) {
        
        QueryProductDTO queryProductDTO = new QueryProductDTO();
        queryProductDTO.setName(name);
        queryProductDTO.setMinPrice(minPrice);
        queryProductDTO.setMaxPrice(maxPrice);
        queryProductDTO.setStatus(status);
        queryProductDTO.setSortBy(sortBy);
        queryProductDTO.setSortOrder(sortOrder);
        
        List<ProductVO> products = productService.listProductByCondition(queryProductDTO);
        return Result.success(products, "查询成功");
    }
    
    /**
     * 分页查询-分页获取商品列表（阿里规范：方法名=page+资源名，如pageProduct）
     * URL：/api/products/page
     * 
     * @param queryProductDTO 查询条件
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 统一响应结果
     */
    @PostMapping("/page")
    public Result<PageInfo<ProductVO>> pageProduct(
            @RequestBody QueryProductDTO queryProductDTO,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "createTime") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        PageInfo<ProductVO> pageInfo = productService.pageProduct(queryProductDTO, pageNum, pageSize, sortBy, sortOrder);
        return Result.success(pageInfo, "查询成功");
    }
}