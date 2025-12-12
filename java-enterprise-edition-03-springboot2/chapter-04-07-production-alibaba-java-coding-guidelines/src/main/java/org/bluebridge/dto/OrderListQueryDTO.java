package org.bluebridge.dto;

/**
 * 订单列表查询DTO
 *
 * @author YourName
 * @since 2025-12-12
 */
public class OrderListQueryDTO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单状态
     */
    private Integer status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}