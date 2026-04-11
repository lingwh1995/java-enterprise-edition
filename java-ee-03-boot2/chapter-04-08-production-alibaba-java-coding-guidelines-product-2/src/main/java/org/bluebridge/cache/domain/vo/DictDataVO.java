package org.bluebridge.cache.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lingwh
 * @desc 字典数据
 * @date 2025/11/15 19:20
 */
@Data
public class DictDataVO {

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

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}