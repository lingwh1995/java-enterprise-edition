package org.bluebridge.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * @author lingwh
 * @desc 部分更新商品DTO
 * @date 2025/12/13 10:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPatchDTO {

    /** 商品名称 */
    private String name;

    /** 商品库存 */
    private Integer stock;

}