package org.bluebridge.cache.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lingwh
 * @desc 字典类型
 * @date 2025/11/15 19:19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictQueryDTO {

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型 0:系统字典 1:业务字典
     */
    private Integer dictType;

}