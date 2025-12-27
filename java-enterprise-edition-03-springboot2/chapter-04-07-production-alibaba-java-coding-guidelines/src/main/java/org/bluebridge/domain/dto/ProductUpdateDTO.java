package org.bluebridge.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author lingwh
 * @desc 更新商品DTO
 * @date 2025/12/13 10:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDTO {

    /** 商品名称 */
    @Size(max = 100, message = "商品名称长度不能超过100个字符")
    private String name;
    
    /** 商品描述 */
    @Size(max = 500, message = "商品描述长度不能超过500个字符")
    private String description;
    
    /** 商品价格 */
    @Positive(message = "商品价格必须大于0")
    private BigDecimal price;
    
    /** 商品库存 */
    @Min(value = 0, message = "商品库存不能小于0")
    private Integer stock;

}