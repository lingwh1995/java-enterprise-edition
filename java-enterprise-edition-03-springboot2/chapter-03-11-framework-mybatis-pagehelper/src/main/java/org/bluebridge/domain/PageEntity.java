package org.bluebridge.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lingwh
 * @desc 封装分页信息的实体类
 * @date 2025/11/23 12:42
 */
@Data
public class PageEntity<T> implements Serializable {

    private int currentPage = 1;
    private int pageSize = 10;
    private T entity;

}
