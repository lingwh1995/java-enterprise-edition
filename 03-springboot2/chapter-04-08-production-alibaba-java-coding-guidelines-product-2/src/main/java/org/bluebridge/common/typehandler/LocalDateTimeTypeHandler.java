package org.bluebridge.common.typehandler;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * LocalDateTime 类型处理器 - 用于将 Mysql 数据库中的 Date 和 TimeStamp 类型映射为 Java 中的 LocalDateTime 类型
 *
 * @author lingwh
 * @date 2025/11/23 18:09
 */
@MappedTypes(LocalDateTime.class)
@MappedJdbcTypes(JdbcType.TIMESTAMP)
public class LocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
        ps.setTimestamp(i, Timestamp.from(parameter.atZone(ZoneId.systemDefault()).toInstant()));
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);
        if (timestamp != null) {
            return timestamp.toLocalDateTime();
        }
        return null;
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnIndex);
        if (timestamp != null) {
            return timestamp.toLocalDateTime();
        }
        return null;
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp timestamp = cs.getTimestamp(columnIndex);
        if (timestamp != null) {
            return timestamp.toLocalDateTime();
        }
        return null;
    }
}
