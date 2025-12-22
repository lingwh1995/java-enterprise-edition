package org.bluebridge.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bluebridge.common.enums.CrudTypeEnum;

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
     * 成功响应结果（无数据）
     * @param message 响应消息
     * @return Result<Void>
     */
    public static Result<Void> success(String message) {
        Result<Void> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
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
        return result;
    }
    
    /**
     * 失败响应结果
     * @param message 响应消息
     * @return Result<Void>
     */
    public static Result<Void> error(Integer code, String message) {
        Result<Void> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 失败响应结果
     * @param message 响应消息
     * @return Result<Void>
     */
    public static Result<Void> error(String message) {
        Result<Void> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    /**
     * 成功响应结果（带数据）
     * @param data 响应数据
     * @return Result<T>
     */
    public static <T> Result<T> data(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("ok");
        result.setData(data);
        return result;
    }

    /**
     * crud操作结果
     * @param rows
     * @param crudTypeEnum
     * @return
     */
    public static Result<Integer> build(Integer rows, CrudTypeEnum crudTypeEnum) {
        Result<Integer> result = new Result<>();
        result.setCode(200);
        result.setData(rows);
        result.setMessage(crudTypeEnum.getDesc() + (rows > 0 ? "成功！": "失败！"));
        return result;
    }

    /**
     * crud操作结果
     * @param data
     * @param crudTypeEnum
     * @return
     * @param <T>
     */
    public static <T> Result<T> build(T data, CrudTypeEnum crudTypeEnum) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(crudTypeEnum.getDesc() + "成功！");
        result.setData(data);
        return result;
    }

}