package org.bluebridge.common.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.common.enums.ResponseStatusEnum;
import org.bluebridge.common.exception.BusinessException;
import org.bluebridge.common.domain.response.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.NoPermissionException;

/**
 * @author lingwh
 * @desc 全局异常处理类
 * @date 2025/11/22 17:35
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            NotLoginException.class,
            NoPermissionException.class,
            SaTokenException.class
    })
    public SaResult handleSaTokenException(Exception e) {
        // 日志记录异常信息
        log.error("Sa-Token 异常：{}", e.getMessage());

        ResponseStatusEnum responseStatus = null;
        if (e instanceof NotLoginException) {
            NotLoginException nle = (NotLoginException) e;
            // 使用 switch 语句判断具体的未登录原因
            switch (nle.getType()) {
                case NotLoginException.NOT_TOKEN:
                    responseStatus = ResponseStatusEnum.TOKEN_MISSING;
                    break;
                case NotLoginException.INVALID_TOKEN:
                    responseStatus = ResponseStatusEnum.TOKEN_INVALID;
                    break;
                case NotLoginException.TOKEN_TIMEOUT:
                    responseStatus = ResponseStatusEnum.TOKEN_EXPIRED;
                    break;
                case NotLoginException.BE_REPLACED:
                    responseStatus = ResponseStatusEnum.TOKEN_REPLACED;
                    break;
                case NotLoginException.KICK_OUT:
                    responseStatus = ResponseStatusEnum.TOKEN_KICK_OUT;
                    break;
                default:
                    // 涵盖其他可能的类型，如 NotLoginException.TOKEN_FREEZE
                    responseStatus = ResponseStatusEnum.UNAUTHENTICATED;
                    break;
            }
        } else if (e instanceof NoPermissionException) {
            responseStatus = ResponseStatusEnum.FORBIDDEN;
        } else {
            // 对于其他所有 Sa-Token 异常（如 NoRoleException），统一视为未授权
            responseStatus = ResponseStatusEnum.UNAUTHENTICATED;
        }

        return SaResult.error(responseStatus.getMessage())
                .setCode(responseStatus.getCode());
    }

    /**
     * 全局异常处理，捕获所有未被其他异常处理程序捕获的异常
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        // 添加堆栈信息
        log.error("业务异常: {}", e.getMessage(), e);
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 全局异常处理，捕获所有未被其他异常处理程序捕获的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        // 添加堆栈信息
        log.error("系统内部错误: {}", e.getMessage(), e);
        return Result.error(500, "系统内部错误，请稍后重试!");
    }

}
