package org.bluebridge.dto;

/**
 * 订单唯一条件查询DTO
 *
 * @author YourName
 * @since 2025-12-12
 */
public class OrderUniqueQueryDTO {

    /**
     * 订单编号
     */
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}