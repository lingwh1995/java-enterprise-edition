package org.bluebridge.controller.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * @author lingwh
 * @desc 统一API响应结果封装类
 * @date 2025/12/13 11:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    /**
     * 响应码
     */
    private Integer code;
    
    /**
     * 响应消息
     */
    private String message;
    
    /**
     * 响应数据
     */
    private T data;
    
    /**
     * 请求是否成功
     */
    private Boolean success;
    
    /**
     * 成功响应结果（无数据）
     * @param message 响应消息
     * @return Result<Void>
     */
    public static Result<Void> success(String message) {
        Result<Void> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        result.setSuccess(true);
        return result;
    }
    
    /**
     * 成功响应结果（带数据）
     * @param data 响应数据
     * @param message 响应消息
     * @return Result<T>
     */
    public static <T> Result<T> success(T data, String message) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        result.setSuccess(true);
        return result;
    }
    
    /**
     * 失败响应结果
     * @param code 响应码
     * @param message 响应消息
     * @return Result<Void>
     */
    public static Result<Void> failure(Integer code, String message) {
        Result<Void> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setSuccess(false);
        return result;
    }

}