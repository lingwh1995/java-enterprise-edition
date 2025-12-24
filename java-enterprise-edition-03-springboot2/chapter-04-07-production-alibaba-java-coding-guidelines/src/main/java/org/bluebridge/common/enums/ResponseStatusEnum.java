package org.bluebridge.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lingwh
 * @desc 响应状态枚举类
 * @date 2025/11/24 18:30
 */
@Getter
@AllArgsConstructor
public enum ResponseStatusEnum {

    /**
     * 成功
     */
    SUCCESS(200, "成功"),

    /**
     * 请求参数错误
     */
    BAD_REQUEST(400, "请求参数错误"),

    /**
     * 认证相关错误 (401)
     */
    UNAUTHENTICATED(401, "用户认证失败"),
    USER_NOT_LOGIN(401, "用户未登录，请先登录后再操作"),
    INVALID_CREDENTIALS(401, "用户名或密码错误"),
    TOKEN_MISSING(401, "Token不存在"),
    //TOKEN_EXPIRED(401, "Token过期"),
    TOKEN_EXPIRED(401, "会话已过期，请重新登录系统!"),
    //TOKEN_INVALID(401, "Token无效"),
    TOKEN_INVALID(401, "身份验证已失效，请重新登录系统!"),
    //TOKEN_REPLACED(401, "Token被顶下线"),
    TOKEN_REPLACED(401, "账号在其他地方登录，您已被强制下线!"),
    //TOKEN_KICK_OUT(401, "Token被强制下线"),
    TOKEN_KICK_OUT(401, "您已被管理员强制下线!"),

    /**
     * 权限相关错误 (403)
     */
    FORBIDDEN(403, "无权限访问"),

    /**
     * 资源相关错误 (404-405)
     */
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不支持"),

    /**
     * 请求频率限制 (429)
     */
    TOO_MANY_REQUESTS(429, "请求过于频繁"),

    /**
     * 服务器内部错误 (500-503)
     */
    INTERNAL_SERVER_ERROR(500, "系统内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用");

    private final int code;
    private final String message;

    /**
     * 根据状态码获取枚举值
     */
    public static ResponseStatusEnum valueOfCode(int code) {
        for (ResponseStatusEnum status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return INTERNAL_SERVER_ERROR;
    }

}
