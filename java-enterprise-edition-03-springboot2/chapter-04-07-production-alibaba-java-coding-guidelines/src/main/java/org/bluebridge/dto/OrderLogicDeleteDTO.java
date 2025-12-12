package org.bluebridge.dto;

/**
 * 订单逻辑删除DTO
 *
 * @author YourName
 * @since 2025-12-12
 */
public class OrderLogicDeleteDTO {

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 删除状态：1-已删除，0-未删除
     */
    private Integer deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}