package org.bluebridge.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * 创建商品数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {
    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    @Size(max = 100, message = "商品名称长度不能超过100个字符")
    private String name;
    
    /**
     * 商品描述
     */
    @Size(max = 500, message = "商品描述长度不能超过500个字符")
    private String description;
    
    /**
     * 商品价格
     */
    @NotNull(message = "商品价格不能为空")
    @Positive(message = "商品价格必须大于0")
    private BigDecimal price;
    
    /**
     * 商品库存
     */
    @NotNull(message = "商品库存不能为空")
    @Min(value = 0, message = "商品库存不能小于0")
    private Integer stock;
}