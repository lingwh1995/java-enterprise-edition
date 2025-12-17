package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bluebridge.dto.SortDTO;

import java.util.List;

/**
 * @author lingwh
 * @desc 排序实体类
 * @date 2025/12/17 11:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SortEntity<T> {

    // 排序对象
    private T entity;

    // 排序条件
    private List<SortDTO> sortDTOList;

}
