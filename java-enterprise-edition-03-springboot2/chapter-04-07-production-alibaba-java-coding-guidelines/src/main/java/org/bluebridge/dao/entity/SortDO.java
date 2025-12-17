package org.bluebridge.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/17 11:27
 */
@AllArgsConstructor
@Data
public class SortDO<T> {

    // 排序对象
    private T t ;

    // 排序字段
    private String sortBy;

    // 排序顺序
    private String sortOrder;

}
