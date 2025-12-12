package org.bluebridge.dto;


import org.bluebridge.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单创建DTO
 *
 * @author YourName
 * @since 2025-12-12
 */
public class OrderCreateDTO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 收货地址
     */
    private String shippingAddress;

    /**
     * 订单项列表
     */
    private List<OrderItem> orderItems;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}