package com.xa8bit.common.domain.query;

import lombok.Data;
import com.xa8bit.common.enums.OrderEnum;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/17 15:19
 */
@Data
public class Sort {

    /** 排序字段 */
    private String orderBy;

    /** 排序方向 */
    private String order = OrderEnum.ASC.name();

}
