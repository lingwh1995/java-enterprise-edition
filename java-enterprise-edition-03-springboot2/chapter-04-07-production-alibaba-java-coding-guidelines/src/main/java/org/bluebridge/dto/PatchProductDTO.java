package org.bluebridge.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

/**
 * @author lingwh
 * @desc 部分更新商品DTO
 * @date 2025/12/13 10:20
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