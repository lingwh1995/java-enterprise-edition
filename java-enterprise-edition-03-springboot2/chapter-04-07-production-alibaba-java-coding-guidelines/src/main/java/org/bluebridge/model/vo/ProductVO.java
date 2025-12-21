package org.bluebridge.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author lingwh
 * @desc 商品视图对象（用于向前端返回展示数据）
 * @date 2025/12/13 11:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO {

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

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}