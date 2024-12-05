package com.xa8bit.mybatis_a.mapper;

/**
 * @author ronin
 */
public class MapperStatement {
    /**crud标签的类型 SELECT | UPDATE| DELETE | INSERT*/
    private String tagType;
    /**Mapper.xml文件的名称空间*/
    private String namespace;
    /**crud标签的id*/
    private String id;
    /**crud标签的返回值类型*/
    private String resultType;
    /**crud标签里面的sql*/
    private String sql;

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
