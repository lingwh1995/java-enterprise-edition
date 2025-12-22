package org.bluebridge.controller;

import com.github.pagehelper.PageInfo;
import org.bluebridge.common.query.Query;
import org.bluebridge.common.query.Sort;
import org.bluebridge.common.query.PageQuery;
import org.bluebridge.common.util.SortUtils;
import org.bluebridge.dto.ProductCreateDTO;
import org.bluebridge.dto.ProductPatchDTO;
import org.bluebridge.dto.ProductUpdateDTO;
import org.bluebridge.dto.ProductQueryDTO;
import org.bluebridge.service.ProductService;
import org.bluebridge.vo.ProductVO;
import org.bluebridge.common.response.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author lingwh
 * @desc 商品管理控制器（阿里规范：类名=资源名+Controller，如ProductController）
 *       路径规范：/api/资源复数（版本+复数资源，阿里微服务规范）
 * @date 2025/12/16 17:02
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
     * @param productCreateDTO 商品创建传输对象
     * @return 统一响应结果
     */
    @PostMapping
    public Result<Integer> createProduct(@Valid @RequestBody ProductCreateDTO productCreateDTO) {
        int i = productService.createProduct(productCreateDTO);
        return Result.success(i, "商品创建成功");
    }
    
    /**
     * 批量创建商品（阿里规范：方法名=batch+动词+资源名，如batchCreateProduct）
     * URL：/api/products/batch
     * 
     * @param productCreateDTOList 商品创建传输对象列表
     * @return 统一响应结果
     */
    @PostMapping("/batch")
    public Result<Integer> batchCreateProduct(@Valid @RequestBody List<ProductCreateDTO> productCreateDTOList) {
        int i = productService.batchCreateProduct(productCreateDTOList);
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
    public Result<Integer> softDeleteProductById(@PathVariable Long id) {
        int i = productService.softDeleteProductById(id);
        return Result.success(i, "根据ID逻辑删除商品成功");
    }

    /**
     * 批量逻辑删除商品（阿里规范：方法名=batchPatch+资源名+状态相关词汇，如batchPatchProductStatus）
     * URL：/api/products/batch/status
     *
     * @param ids 商品ID列表
     * @return 统一响应结果
     */
    @PatchMapping("/batch/status")
    public Result<Integer> batchSoftDeleteProduct(@RequestBody List<Long> ids) {
        int i = productService.batchSoftDeleteProduct(ids);
        return Result.success(i,"商品批量逻辑删除成功");
    }

    /**
     * 根据ID全量更新商品（阿里规范：方法名=update+资源名+By+主键，如updateProductById）
     * URL：/api/products/1
     * 
     * @param id 商品ID
     * @param productUpdateDTO 商品传输对象
     * @return 统一响应结果
     */
    @PutMapping("/{id}")
    public Result<Integer> updateProductById(
            @PathVariable @NotNull(message = "商品ID不能为空") 
            @Min(value = 1, message = "商品ID必须大于0") Long id, 
            @Valid @RequestBody ProductUpdateDTO productUpdateDTO) {
        int i = productService.updateProductById(id, productUpdateDTO);
        return Result.success(i, "商品更新成功");
    }
    
    /**
     * 根据ID部分更新商品（阿里规范：方法名=patch+资源名，如patchProduct）
     * URL：/api/products/1
     * 
     * @param id 商品ID
     * @param productPatchDTO 商品部分更新传输对象
     * @return 统一响应结果
     */
    @PatchMapping("/{id}")
    public Result<Integer> patchProductById(
            @PathVariable @NotNull(message = "商品ID不能为空") 
            @Min(value = 1, message = "商品ID必须大于0") Long id, 
            @Valid @RequestBody ProductPatchDTO productPatchDTO) {
        int i = productService.patchProductById(id, productPatchDTO);
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
    public Result<List<ProductVO>> listProductByName(
            @RequestParam @NotBlank(message = "商品名称不能为空") String name) {
        List<ProductVO> productVOList = productService.listProductByName(name);
        return Result.success(productVOList, "查询成功");
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
     * @param orderBy 排序字段
     * @param order 排序方式
     * @return 统一响应结果
     */
    @GetMapping("/search")
    public Result<List<ProductVO>> searchProduct(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DecimalMin("0.0") BigDecimal minPrice,
            @RequestParam(required = false) @DecimalMin("100.0") BigDecimal maxPrice,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false, defaultValue = "create_time") @Pattern(regexp = "create_time") String orderBy,
            @RequestParam(required = false, defaultValue = "desc") @Pattern(regexp = "asc|desc") String order) {

        // 构建排序条件列表
        List<Sort> sortList = SortUtils.toSortList(orderBy, order);

        // 构建查询参数
        ProductQueryDTO productQueryDTO = ProductQueryDTO.builder()
                .name(name)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .status(status)
                .build();

        // 构建排序查询参数
        Query<ProductQueryDTO> query = Query.<ProductQueryDTO>builder()
                .query(productQueryDTO)
                .sortList(sortList)
                .build();

        List<ProductVO> products = productService.searchProduct(query);
        return Result.success(products, "查询成功");
    }

    /**
     * 分页查询-分页获取商品列表（阿里规范：方法名=page+资源名，如pageProduct）
     * URL：/api/products/page
     *
     * @param name 商品名称（模糊匹配）
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param status 商品状态
     * @param orderBy 排序字段
     * @param order 排序方式
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 统一响应结果
     */
    @GetMapping("/page")
    public Result<PageInfo<ProductVO>> pageProduct(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DecimalMin("0.0") BigDecimal minPrice,
            @RequestParam(required = false) @DecimalMin("100.0") BigDecimal maxPrice,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false, defaultValue = "create_time") @Pattern(regexp = "create_time") String orderBy,
            @RequestParam(required = false, defaultValue = "desc") @Pattern(regexp = "asc|desc") String order,
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "页码必须大于0") Integer pageNum,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "每页数量必须大于0") Integer pageSize) {

        // 构建排序条件列表
        List<Sort> sortList = SortUtils.toSortList(orderBy, order);

        // 构建查询参数
        ProductQueryDTO productQueryDTO = ProductQueryDTO.builder()
                .name(name)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .status(status)
                .build();

        // 构建分页排序参数
        PageQuery<ProductQueryDTO> pageQuery = PageQuery.<ProductQueryDTO>builder()
                .query(productQueryDTO)
                .sortList(sortList)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();

        PageInfo<ProductVO> pageInfo = productService.pageProduct(pageQuery);
        return Result.success(pageInfo, "查询成功");
    }

    /**
     * 分页查询-分页获取商品列表（阿里规范：方法名=page+资源名，如pageProduct）
     * URL：/api/products/page
     *
     * @param productQueryDTO 查询条件
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param orderBy 排序字段
     * @param order 排序方式
     * @return 统一响应结果
     */
    @PostMapping("/page")
    public Result<PageInfo<ProductVO>> pageProduct(
            @RequestBody ProductQueryDTO productQueryDTO,
            @RequestParam(required = false, defaultValue = "create_time") @Pattern(regexp = "create_time") String orderBy,
            @RequestParam(required = false, defaultValue = "desc") @Pattern(regexp = "asc|desc") String order,
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "页码必须大于0") Integer pageNum,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "每页数量必须大于0") Integer pageSize) {

        // 构建排序条件列表
        List<Sort> sortList = SortUtils.toSortList(orderBy, order);

        // 构建分页排序参数
        PageQuery<ProductQueryDTO> pageQuery = PageQuery.<ProductQueryDTO>builder()
                .query(productQueryDTO)
                .sortList(sortList)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();

        PageInfo<ProductVO> pageInfo = productService.pageProduct(pageQuery);
        return Result.success(pageInfo, "查询成功");
    }

}