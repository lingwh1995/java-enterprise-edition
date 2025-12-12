package org.bluebridge.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * 分页结果封装类
 * @param <T> 数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo<T> {
    /**
     * 当前页码
     */
    private Integer pageNum;
    
    /**
     * 每页数量
     */
    private Integer pageSize;
    
    /**
     * 总记录数
     */
    private Long total;
    
    /**
     * 总页数
     */
    private Integer pages;
    
    /**
     * 数据列表
     */
    private List<T> list;
    
    /**
     * 是否有下一页
     */
    private Boolean hasNextPage;
    
    /**
     * 是否有上一页
     */
    private Boolean hasPreviousPage;
}