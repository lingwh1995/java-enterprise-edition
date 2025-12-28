package com.xa8bit.common.util;

import com.xa8bit.common.domain.query.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author lingwh
 * @desc 排序工具类
 * @date 2025/12/18 10:30
 */
public class SortUtils {

    /**
     * 将orderBy和order转换成List<SortDTO>对象
     *
     * @param orderBy 排序字段，多个字段用逗号分隔
     * @param order   排序方向，多个方向用逗号分隔
     * @return 排序DTO列表
     */
    public static List<Sort> toSortList(String orderBy, String order) {
        List<Sort> sortList = new ArrayList<>();
        if (orderBy != null && order != null) {
            String[] orderBys = orderBy.split(",");
            String[] orders = order.split(",");

            // 基于Stream把字符串数组转换成SortDTO对象
            sortList = IntStream.range(0, Math.min(orderBys.length, orders.length))
                    .mapToObj(i -> {
                        Sort dto = new Sort();
                        dto.setOrderBy(orderBys[i]);
                        dto.setOrder(orders[i]);
                        return dto;
                    })
                    .collect(Collectors.toList());
        }
        return sortList;
    }

    /**
     * 将orderBy和order转换成排序SQL语句
     *
     * @param orderBy 排序字段，多个字段用逗号分隔
     * @param order   排序方向，多个方向用逗号分隔
     * @return 排序SQL语句
     */
    public static String toSortSQL(String orderBy, String order) {
        List<Sort> sortList = toSortList(orderBy, order);
        return sortList.stream()
                .map(dto -> dto.getOrderBy() + " " + dto.getOrder())
                .collect(Collectors.joining(", "));
    }

}