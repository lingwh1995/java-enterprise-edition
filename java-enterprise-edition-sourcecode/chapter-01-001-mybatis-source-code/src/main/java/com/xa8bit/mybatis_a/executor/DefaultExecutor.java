package com.xa8bit.mybatis_a.executor;

import com.xa8bit.mybatis_a.entity.Configuration;
import com.xa8bit.mybatis_a.mapper.MapperStatement;
import com.xa8bit.mybatis_a.utils.JdbcUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ronin
 */
public class DefaultExecutor implements Executor {
    private final Configuration configuration;

    public DefaultExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> query(MapperStatement ms, Object... params) {
        List<E> result = new ArrayList<>();
        try {
            result = JdbcUtils.executeQuery(configuration, ms, params);
        } catch (SQLException | ClassNotFoundException |IllegalAccessException |InstantiationException e) {
            e.printStackTrace();
        }
        return result;
    }
}
