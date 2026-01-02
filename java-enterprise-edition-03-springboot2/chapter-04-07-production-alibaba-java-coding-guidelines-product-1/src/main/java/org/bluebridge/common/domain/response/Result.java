package org.bluebridge.common.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bluebridge.common.enums.OperationTypeEnum;

/**
 * @author lingwh
 * @desc 统一API响应结果封装类
 * @date 2025/12/13 11:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    /** 响应码 */
    private Integer code;

    /** 响应消息 */
    private String message;

    /** 响应数据 */
    private T data;


    /**
     * 创建一个包含数据的成功结果对象
     *
     * @param <T> 数据的泛型类型
     * @param data 要包装在结果对象中的数据
     * @return Result<T> 包含指定数据的成功结果对象，状态码为200，消息为"ok"
     */
    public static <T> Result<T> data(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("ok");
        result.setData(data);
        return result;
    }

    /**
     * 创建一个成功的Result对象
     *
     * @param message 成功消息内容
     * @return Result<Void> 包含成功状态的Result对象，状态码为200
     */
    public static Result<Void> success(String message) {
        Result<Void> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        return result;
    }

    /**
     * 创建一个成功的Result对象
     *
     * @param <T> 泛型类型，表示返回数据的类型
     * @param message 成功消息字符串
     * @param data 返回的具体数据对象
     * @return Result<T> 包含成功状态码(200)、消息和数据的Result对象
     */
    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 创建一个表示错误的Result对象
     *
     * @param message 错误消息描述
     * @return 返回一个Result<Void>对象，其中code为500，message为传入的错误消息
     */
    public static Result<Void> error(String message) {
        Result<Void> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    /**
     * 创建一个错误结果对象
     *
     * @param code 错误码，用于标识具体的错误类型
     * @param message 错误消息，用于描述错误的详细信息
     * @return 返回一个包含错误码和错误消息的Result<Void>对象
     */
    public static Result<Void> error(Integer code, String message) {
        Result<Void> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 创建一个包含错误信息的结果对象
     *
     * @param <T> 结果数据的泛型类型
     * @param code 错误码，用于标识错误类型
     * @param message 错误消息，用于描述错误详情
     * @param data 返回的数据对象，可以为null
     * @return 包含错误码、错误消息和数据的结果对象
     */
    public static <T> Result<T> error(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 构建操作结果对象
     * 根据操作行数和操作类型构建包含成功状态的结果对象
     *
     * @param rows 操作影响的行数
     * @param operationTypeEnum 操作类型枚举
     * @return Result<Integer> 包含操作结果的结果对象，其中数据为影响的行数
     */
    public static Result<Integer> buildRowsResult(Integer rows, OperationTypeEnum operationTypeEnum) {
        Result<Integer> result = new Result<>();
        result.setCode(200);
        result.setData(rows);
        String operationDesc = operationTypeEnum.getDesc();
        // 根据影响行数设置不同的成功消息
        if (rows > 0) {
            result.setMessage(operationDesc + "成功！影响" + rows + "条记录！");
        } else {
            result.setMessage(operationDesc + "完成，但未影响任何记录！");
        }
        return result;
    }

    /**
     * 构建操作结果对象
     *
     * @param <T> 操作结果数据类型
     * @param data 操作返回的数据
     * @param operationTypeEnum 操作类型枚举
     * @return Result<T> 包含操作结果的Result对象
     */
    public static <T> Result<T> buildDataResult(T data, OperationTypeEnum operationTypeEnum) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(operationTypeEnum.getDesc() + "成功！");
        result.setData(data);
        return result;
    }

}