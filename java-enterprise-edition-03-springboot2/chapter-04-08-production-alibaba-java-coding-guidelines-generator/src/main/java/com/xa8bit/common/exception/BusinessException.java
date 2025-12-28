package com.xa8bit.common.exception;

import com.xa8bit.common.enums.ResponseStatusEnum;
import lombok.Getter;

/**
 * 业务异常类
 * @author lingwh
 * @date 2025/12/24
 */
@Getter
public class BusinessException extends RuntimeException {

    /** 错误码 */
    private final Integer code;
    /** 扩展数据 */
    private final Object data;

    /**
     * 构造一个BusinessException异常实例
     * @param message 异常描述信息
     */
    public BusinessException(String message) {
        super(message);
        // 设置异常状态码为BAD_REQUEST
        this.code = ResponseStatusEnum.BAD_REQUEST.getCode();
        // 初始化数据对象为null
        this.data = null;
    }

    /**
     * 构造BusinessException异常实例
     * @param responseStatusEnum 响应状态枚举，包含异常的消息和错误码
     */
    public BusinessException(ResponseStatusEnum responseStatusEnum) {
        super(responseStatusEnum.getMessage());
        this.code = responseStatusEnum.getCode();
        this.data = null;
    }

    /**
     * 构造BusinessException异常实例
     * @param responseStatusEnum 响应状态枚举，包含异常码信息
     * @param message 异常描述信息
     */
    public BusinessException(ResponseStatusEnum responseStatusEnum, String message) {
        super(message);
        this.code = responseStatusEnum.getCode();
        this.data = null;
    }

    /**
     * 构造BusinessException实例
     *
     * @param code 业务异常代码，用于标识异常类型
     * @param message 异常描述信息，将传递给父类构造函数
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.data = null;
    }

    /**
     * 构造BusinessException异常实例
     * @param code 异常代码，用于标识具体的业务异常类型
     * @param message 异常描述信息，提供关于异常的详细说明
     * @param cause 导致此异常的原始异常，用于异常链追踪
     */
    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.data = null;
    }

    /**
     * 构造BusinessException异常实例
     * @param code 异常代码，用于标识具体的错误类型
     * @param message 异常描述信息，提供错误的详细说明
     * @param cause 导致此异常的原始异常，用于异常链追踪
     * @param data 与异常相关的附加数据，可用于传递错误上下文信息
     */
    public BusinessException(Integer code, String message, Throwable cause, Object data) {
        super(message, cause);
        this.code = code;
        this.data = data;
    }

    /**
     * 构造BusinessException异常实例
     * @param responseStatusEnum 响应状态枚举，用于设置异常的错误码
     * @param message 异常消息描述
     * @param cause 异常原因，用于链式异常处理
     * @param data 附加数据，用于携带异常相关的业务数据
     */
    public BusinessException(ResponseStatusEnum responseStatusEnum, String message, Throwable cause, Object data) {
        super(message, cause);
        this.code = responseStatusEnum.getCode();
        this.data = data;
    }

}