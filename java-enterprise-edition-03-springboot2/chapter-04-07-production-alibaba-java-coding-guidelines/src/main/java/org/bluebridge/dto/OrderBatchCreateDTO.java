package org.bluebridge.dto;

import org.bluebridge.entity.Order;

import java.util.List;

/**
 * 订单批量创建DTO
 *
 * @author YourName
 * @since 2025-12-12
 */
public class OrderBatchCreateDTO {

    /**
     * 订单列表
     */
    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}