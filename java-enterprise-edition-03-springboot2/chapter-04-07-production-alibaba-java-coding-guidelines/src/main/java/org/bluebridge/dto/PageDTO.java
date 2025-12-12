package org.bluebridge.dto;

/**
 * 分页参数DTO
 *
 * @author YourName
 * @since 2025-12-12
 */
public class PageDTO {

    /**
     * 当前页码
     */
    private Integer pageNum = 1;

    /**
     * 每页显示数量
     */
    private Integer pageSize = 10;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}