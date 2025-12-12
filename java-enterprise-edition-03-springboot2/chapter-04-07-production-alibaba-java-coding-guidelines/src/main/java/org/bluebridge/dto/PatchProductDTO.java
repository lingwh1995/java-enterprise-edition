package org.bluebridge.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

/**
 * 商品部分更新DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchProductDTO {
    /**
     * 商品名称
     */
    private String name;
    
    /**
     * 商品描述
     */
    private String description;
    
    /**
     * 商品价格
     */
    private BigDecimal price;
    
    /**
     * 商品库存
     */
    private Integer stock;
    
    /**
     * 商品状态
     */
    private Integer status;
}