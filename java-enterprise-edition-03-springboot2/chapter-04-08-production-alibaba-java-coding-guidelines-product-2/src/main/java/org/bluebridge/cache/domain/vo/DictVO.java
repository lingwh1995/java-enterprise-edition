package org.bluebridge.cache.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lingwh
 * @desc 字典类型
 * @date 2025/11/15 19:19
 */
@Data
public class DictVO {

    /**
     * 主键
     */
    private Integer id;

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

    /**
     * 字典状态 0:正常 1:停用
     */
    private Integer dictStatus;

    /**
     * 字典描述
     */
    private String dictDesc;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 字典数据列表
     */
    private List<DictDataVO> dictDataList;

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