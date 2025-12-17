package org.bluebridge.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lingwh
 * @desc 分页实体类
 * @date 2025/12/17 16:15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageEntity<T> {

    private T entity;
    private int pageNum;
    private int pageSize;

}
