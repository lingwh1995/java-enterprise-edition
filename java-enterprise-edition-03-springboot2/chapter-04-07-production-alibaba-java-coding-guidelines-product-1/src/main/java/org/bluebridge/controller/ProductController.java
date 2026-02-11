package org.bluebridge.controller;

import com.github.pagehelper.PageInfo;
import org.bluebridge.common.enums.OperationTypeEnum;
import org.bluebridge.common.domain.query.Query;
import org.bluebridge.common.domain.query.Sort;
import org.bluebridge.common.domain.query.PageQuery;
import org.bluebridge.common.util.SortUtils;
import org.bluebridge.domain.dto.ProductCreateDTO;
import org.bluebridge.domain.dto.ProductPatchDTO;
import org.bluebridge.domain.dto.ProductUpdateDTO;
import org.bluebridge.domain.dto.ProductQueryDTO;
import org.bluebridge.service.ProductService;
import org.bluebridge.domain.vo.ProductVO;
import org.bluebridge.common.domain.response.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author lingwh
 * @desc 商品管理控制器（阿里规范：类名 = 资源名 + Controller，如 ProductController）
 *       路径规范：/api/资源复数（版本+复数资源，阿里微服务规范）
 * @date 2025/12/16 17:02
 */
@Validated
@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Resource
    private ProductService productService;
    
    /**
     * 创建商品（阿里规范：方法名 = create + 资源名，如 createProduct）
     * URL：/api/products
     * 
     * @param productCreateDTO 商品创建传输对象
     * @return 统一响应结果
     */
    @PostMapping
    public Result<Integer> createProduct(@Valid @RequestBody ProductCreateDTO productCreateDTO) {
        int rows = productService.createProduct(productCreateDTO);
        return Result.buildRowsResult(rows, OperationTypeEnum.CREATE);
    }
    
    /**
     * 批量创建商品（阿里规范：方法名 = batch + 动词 + 资源名，如 batchCreateProduct）
     * URL：/api/products/batch
     * 
     * @param productCreateDTOList 商品创建传输对象列表
     * @return 统一响应结果
     */
    @PostMapping("/batch")
    public Result<Integer> batchCreateProduct(@Valid @RequestBody List<ProductCreateDTO> productCreateDTOList) {
        int rows = productService.batchCreateProduct(productCreateDTOList);
        return Result.buildRowsResult(rows, OperationTypeEnum.BATCH_CREATE);
    }

    /**
     * 根据ID删除商品（物理删除）（阿里规范：方法名 = delete + 资源名 + By + 主键，如 deleteProductById）
     * URL：/api/products/1
     *
     * @param id 商品ID
     * @return 统一响应结果
     */
    @DeleteMapping("/{id}")
    public Result<Integer> deleteProductById(
            @PathVariable @NotNull(message = "商品ID不能为空")
            @Min(value = 1, message = "商品ID必须大于0") Long id) {
        int rows = productService.deleteProductById(id);
        return Result.buildRowsResult(rows, OperationTypeEnum.DELETE);
    }

    /**
     * 批量删除商品（物理删除）（阿里规范：方法名 = batchDelete + 资源名，如 batchDeleteProduct）
     * URL：/api/products
     *
     * @param ids 商品ID列表
     * @return 统一响应结果
     */
    @DeleteMapping("/batch")
    public Result<Integer> batchDeleteProduct(@RequestBody List<Long> ids) {
        int rows = productService.batchDeleteProduct(ids);
        return Result.buildRowsResult(rows, OperationTypeEnum.BATCH_DELETE);
    }

    /**
     * 根据ID逻辑删除商品（阿里规范：方法名 = patch + 资源名 + 状态相关词汇，如 patchProductStatus）
     * URL：/api/products/1/status
     *
     * @param id 商品ID
     * @return 统一响应结果
     */
    @PatchMapping("/{id}/status")
    public Result<Integer> softDeleteProductById(
            @PathVariable @NotNull(message = "商品ID不能为空")
            @Min(value = 1, message = "商品ID必须大于0") Long id) {
        int rows = productService.softDeleteProductById(id);
        return Result.buildRowsResult(rows, OperationTypeEnum.SOFT_DELETE);
    }

    /**
     * 批量逻辑删除商品（阿里规范：方法名 = batchPatch + 资源名 + 状态相关词汇，如 batchPatchProductStatus）
     * URL：/api/products/batch/status
     *
     * @param ids 商品ID列表
     * @return 统一响应结果
     */
    @PatchMapping("/batch/status")
    public Result<Integer> batchSoftDeleteProduct(@RequestBody List<Long> ids) {
        int rows = productService.batchSoftDeleteProduct(ids);
        return Result.buildRowsResult(rows, OperationTypeEnum.BATCH_SOFT_DELETE);
    }

    /**
     * 根据ID全量更新商品（阿里规范：方法名 = update + 资源名 + By + 主键，如 updateProductById）
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
        int rows = productService.updateProductById(id, productUpdateDTO);
        return Result.buildRowsResult(rows, OperationTypeEnum.UPDATE);
    }
    
    /**
     * 根据ID部分更新商品（阿里规范：方法名 = patch + 资源名，如 patchProduct）
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
        int rows = productService.patchProductById(id, productPatchDTO);
        return Result.buildRowsResult(rows, OperationTypeEnum.PATCH);
    }
    
    /**
     * 主键查询-根据ID查询商品（阿里规范：方法名 = get + 资源名 + By + 主键，如 getProductById）
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
        return Result.buildDataResult(productVO, OperationTypeEnum.QUERY_ONE);
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
    @GetMapping
    public Result<List<ProductVO>> listProduct(
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
                .conditions(productQueryDTO)
                .sortList(sortList)
                .build();

        List<ProductVO> productVOList = productService.listProduct(query);
        return Result.buildDataResult(productVOList, OperationTypeEnum.QUERY_LIST);
    }

    /**
     * 分页查询-分页获取商品列表（阿里规范：方法名 = page + 资源名，如 pageProduct）
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
                .conditions(productQueryDTO)
                .sortList(sortList)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();

        PageInfo<ProductVO> pageInfo = productService.pageProduct(pageQuery);
        return Result.buildDataResult(pageInfo, OperationTypeEnum.QUERY_PAGE);
    }

    /**
     * 分页查询-分页获取商品列表（阿里规范：方法名 = page + 资源名，如 pageProduct）
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
                .conditions(productQueryDTO)
                .sortList(sortList)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();

        PageInfo<ProductVO> pageInfo = productService.pageProduct(pageQuery);
        return Result.buildDataResult(pageInfo, OperationTypeEnum.QUERY_PAGE);
    }

}