package org.bluebridge.common.dto;

import lombok.Data;
import org.bluebridge.enums.OrderEnum;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/17 15:19
 */
@Data
public class SortDTO {

    private String orderBy;
    private String order = OrderEnum.ASC.name();

}
