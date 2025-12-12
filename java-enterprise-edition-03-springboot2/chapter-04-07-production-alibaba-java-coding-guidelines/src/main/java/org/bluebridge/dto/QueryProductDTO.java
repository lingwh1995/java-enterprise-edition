package org.bluebridge.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

/**
 * 商品查询条件DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryProductDTO {
    /**
     * 商品名称（模糊匹配）
     */
    private String name;
    
    /**
     * 最低价格
     */
    private BigDecimal minPrice;
    
    /**
     * 最高价格
     */
    private BigDecimal maxPrice;
    
    /**
     * 商品状态
     */
    private Integer status;
    
    /**
     * 排序字段（createTime, price）
     */
    private String sortBy;
    
    /**
     * 排序方式（asc, desc）
     */
    private String sortOrder;
}