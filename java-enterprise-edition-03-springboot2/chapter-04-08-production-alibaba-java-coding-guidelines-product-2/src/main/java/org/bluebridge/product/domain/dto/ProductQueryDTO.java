package org.bluebridge.product.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author lingwh
 * @desc 查询商品DTO
 * @date 2025/12/13 10:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductQueryDTO {

    /** 商品名称（模糊匹配） */
    private String name;
    
    /** 最低价格 */
    private BigDecimal minPrice;
    
    /** 最高价格 */
    private BigDecimal maxPrice;
    
    /** 商品状态 */
    private Integer status;
}