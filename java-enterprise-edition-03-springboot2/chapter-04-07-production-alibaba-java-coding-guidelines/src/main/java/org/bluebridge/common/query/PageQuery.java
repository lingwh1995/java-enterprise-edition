package org.bluebridge.common.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lingwh
 * @desc 分页参数传输对象，仅负责分页参数的传输
 * @date 2025/12/17 16:15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageQuery<T> {

    /** 查询参数 */
    private T query;

    /** 排序条件 */
    private List<Sort> sortList;

    /** 页码 */
    private int pageNum;

    /** 每页数量 */
    private int pageSize;

}