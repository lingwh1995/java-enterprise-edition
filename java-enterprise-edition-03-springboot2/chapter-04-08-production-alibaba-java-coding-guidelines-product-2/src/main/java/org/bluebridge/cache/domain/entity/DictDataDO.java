package org.bluebridge.cache.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bluebridge.common.domain.entity.BaseEntity;

/**
 * @author lingwh
 * @desc 字典数据
 * @date 2025/11/15 19:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictDataDO extends BaseEntity {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典值
     */
    private String dictValue;

    /**
     * 是否默认 0:是 1:否
     */
    private Integer isDefault;

    /**
     * 字典数据状态 0:正常 1:停用
     */
    private Integer dictStatus;

    /**
     * 字典数据描述
     */
    private String dictDataDesc;

    /**
     * 排序
     */
    private Integer sortOrder;

}