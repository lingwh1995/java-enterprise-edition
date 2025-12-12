package org.bluebridge.dto;

import java.util.List;

/**
 * 订单批量删除DTO
 *
 * @author YourName
 * @since 2025-12-12
 */
public class OrderBatchDeleteDTO {

    /**
     * 订单ID列表
     */
    private List<Long> orderIds;

    public List<Long> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<Long> orderIds) {
        this.orderIds = orderIds;
    }
}