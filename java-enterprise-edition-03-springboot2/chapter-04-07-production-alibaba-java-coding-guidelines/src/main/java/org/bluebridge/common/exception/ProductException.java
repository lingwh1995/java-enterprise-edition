package org.bluebridge.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品业务异常类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductException extends RuntimeException {

    /**
     * 异常码
     */
    private Integer code;
    
    /**
     * 构造方法
     * @param code 异常码
     * @param message 异常信息
     */
    public ProductException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    
    /**
     * 构造方法
     * @param code 异常码
     * @param message 异常信息
     * @param cause 异常原因
     */
    public ProductException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}