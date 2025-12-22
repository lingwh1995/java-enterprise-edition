package org.bluebridge.common.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lingwh
 * @desc 查询参数传输对象，包含查询条件和排序条件
 * @date 2025/12/17 16:15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Query<T> {

    /** 查询参数 */
    private T query;

    /** 排序条件 */
    private List<Sort> sortList;

}