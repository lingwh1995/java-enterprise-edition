package org.bluebridge.common.constant;

/**
 * @author lingwh
 * @desc 逻辑删除状态枚举
 * @date 2025/12/19 11:23
 */
public enum SoftDeleteStatus {
    
    /**
     * 未删除状态
     */
    NOT_DELETED(0, "未删除"),
    
    /**
     * 已删除状态
     */
    DELETED(1, "已删除");

    private final int code;
    private final String description;

    SoftDeleteStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据code获取删除状态枚举
     *
     * @param code 状态码
     * @return 删除状态枚举
     */
    public static SoftDeleteStatus fromCode(int code) {
        for (SoftDeleteStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效的删除状态码: " + code);
    }
}