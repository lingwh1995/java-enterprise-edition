package mybatis_a.utils;

import mybatis_a.entity.Configuration;
import mybatis_a.mapper.MapperStatement;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * @author ronin
 */
public class JdbcUtils {

    /**存放与当前线程绑定的Connection*/
    private static final ThreadLocal<Connection> connectionContainer = new ThreadLocal<>();

    /**
     * 私有构造,防止通过new调用
     */
    private JdbcUtils() {}

    /**
     * 获取连接对象
     * @return 获取连接对象
     * @throws SQLException 如果参数有误,则抛出此异常
     */
    public static Connection getConnection(Configuration configuration) throws SQLException, ClassNotFoundException {
        final Map<String, String> dbConfigMap = configuration.getDbConfigMap();
        Class.forName(dbConfigMap.get("driver"));
        Connection connection;
        if(Objects.isNull(connection = connectionContainer.get())) {
            connection = DriverManager.getConnection(dbConfigMap.get("url"),dbConfigMap.get("username"), dbConfigMap.get("password"));
            connectionContainer.set(connection);
            return connection;
        }
        return connectionContainer.get();
    }

    /**
     * 开启事物
     */
    public static void startTransaction() throws SQLException {
        Connection connection;
        if(Objects.isNull(connection = connectionContainer.get())){
            return;
        }
        connection.setAutoCommit(true);
    }

    /**
     * 关闭事物
     */
    public static void commitTransaction() throws SQLException {
        Connection connection;
        if(Objects.isNull(connection = connectionContainer.get())){
            return;
        }
        connection.close();
    }

    /**
     * 关闭连接
     */
    public static void close() throws SQLException {
        Connection connection;
        if(Objects.isNull(connection = connectionContainer.get())){
            return;
        }
        try {
            connection.close();
        } finally {
            connectionContainer.remove();
        }
    }

    /**
     * 执行查询操作
     * @param configuration 全局配置文件
     * @param ms 封装了每一个增删改查标签的Model
     * @param params 方法执行需要的参数
     * @param <E> 目标返回 类型
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <E> List<E> executeQuery(Configuration configuration, MapperStatement ms, Object... params) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String sql = ms.getSql().replace("#{id}", "?");
        PreparedStatement ps = getConnection(configuration).prepareStatement(sql);
        if(!Objects.isNull(params)){
            for (int i = 0; i < params.length; i++) {
                ps.setString((i+1),params[i].toString());
            }
        }
        ResultSet rs = ps.executeQuery();
        Class<?> t = Class.forName(ms.getResultType());
        E e = (E)t.newInstance();
        ArrayList<E> result = new ArrayList<>();
        while (rs.next()){
            for (Field field : t.getDeclaredFields()) {
                field.setAccessible(true);
                //通过反射给实体设置值
                field.set(e,rs.getString(field.getName()));
            }
            result.add(e);
        }
        return result;
    }
}
