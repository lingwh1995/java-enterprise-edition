package org.bluebridge.dto;

import java.util.List;

/**
 * 订单批量更新状态DTO
 *
 * @author YourName
 * @since 2025-12-12
 */
public class OrderBatchUpdateStatusDTO {

    /**
     * 订单ID列表
     */
    private List<Long> orderIds;

    /**
     * 订单状态：0-待支付，1-已支付，2-已取消，3-已完成
     */
    private Integer status;

    public List<Long> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<Long> orderIds) {
        this.orderIds = orderIds;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}