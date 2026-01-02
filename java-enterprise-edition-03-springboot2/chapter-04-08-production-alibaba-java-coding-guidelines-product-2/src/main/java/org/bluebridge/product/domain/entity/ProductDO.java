package org.bluebridge.product.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bluebridge.common.domain.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * @author lingwh
 * @desc 商品实体类
 * @date 2025/12/13 10:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductDO extends BaseEntity {

    /** 商品ID */
    private Long id;

    /** 商品名称 */
    private String name;
    
    /** 商品描述 */
    private String description;
    
    /** 商品价格 */
    private BigDecimal price;
    
    /** 商品库存 */
    private Integer stock;
    
    /** 商品状态（0：下架，1：上架） */
    private Integer status;

}