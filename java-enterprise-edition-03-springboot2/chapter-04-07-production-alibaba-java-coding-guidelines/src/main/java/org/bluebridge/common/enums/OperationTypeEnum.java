package org.bluebridge.common.enums;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/22 20:34
 */
public enum OperationTypeEnum {

    CREATE("新增"),
    BATCH_CREATE("批量新增"),
    UPDATE("全量更新"),
    BATCH_UPDATE("批量全量更新"),
    PATCH("部分更新"),
    BATCH_PATCH("批量部分更新"),
    DELETE("删除"),
    DELETE_CONDITION("条件删除"),
    BATCH_DELETE("批量删除"),
    SOFT_DELETE("逻辑删除"),
    BATCH_SOFT_DELETE("批量逻辑删除"),
    QUERY_ONE("查询单个"),
    QUERY_LIST("查询列表"),
    QUERY_LIST_CONDITION("条件查询列表"),
    QUERY_PAGE("分页查询");

    private final String desc;

    OperationTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
