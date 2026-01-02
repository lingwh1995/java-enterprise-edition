package org.bluebridge.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.bluebridge.system.domain.entity.DictDataDO;

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
     * 字典数据列表
     */
    private List<DictDataDO> dictDataList;

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