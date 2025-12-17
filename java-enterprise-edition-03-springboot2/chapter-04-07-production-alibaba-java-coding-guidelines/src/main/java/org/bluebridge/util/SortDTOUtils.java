package org.bluebridge.util;

import org.bluebridge.dto.SortDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author lingwh
 * @desc 排序DTO工具类
 * @date 2025/12/17 15:43
 */
public class SortDTOUtils {

    /**
     * 将orderBy和order转换成List<SortDTO>对象
     * @param orderBy 排序字段，多个字段用逗号分隔
     * @param order 排序方向，多个方向用逗号分隔
     * @return
     */
    public static List<SortDTO> toSortDTOList(String orderBy, String order) {
        List<SortDTO> sortDTOList = new ArrayList<>();
        if (orderBy != null && orderBy != null) {
            String[] orderBys = orderBy.split(",");
            String[] orders = order.split(",");

            // 基于Stream把字符串数组转换成SortDTO对象
            sortDTOList = IntStream.range(0, Math.min(orderBys.length, orders.length))
                    .mapToObj(i -> {
                        SortDTO dto = new SortDTO();
                        dto.setOrderBy(orderBys[i]);
                        dto.setOrder(orders[i]);
                        return dto;
                    })
                    .collect(Collectors.toList());
        }
        return sortDTOList;
    }

}
