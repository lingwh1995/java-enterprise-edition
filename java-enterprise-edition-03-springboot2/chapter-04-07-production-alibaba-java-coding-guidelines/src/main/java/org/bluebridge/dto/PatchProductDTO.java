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

    // 商品名称
    private String name;

    // 商品库存
    private Integer stock;

}