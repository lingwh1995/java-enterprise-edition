package org.bluebridge.generator.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.jdbc.ConnectionImpl;
import org.bluebridge.common.enums.ResponseStatusEnum;
import org.bluebridge.common.exception.BusinessException;
import org.bluebridge.common.domain.query.PageQuery;
import org.bluebridge.common.domain.query.Query;
import org.bluebridge.generator.constant.MetaDataConstant;
import org.bluebridge.generator.converter.DataSourceConverter;
import org.bluebridge.generator.domain.dto.DataSourceConnectionDTO;
import org.bluebridge.generator.domain.dto.DataSourceQueryDTO;
import org.bluebridge.generator.domain.dto.DataSourceUpdateDTO;
import org.bluebridge.generator.domain.dto.DataSourceCreateDTO;
import org.bluebridge.generator.domain.entity.ColumnMetaData;
import org.bluebridge.generator.domain.entity.DataSourceDO;
import org.bluebridge.generator.domain.entity.DataSourceMetaData;
import org.bluebridge.generator.domain.entity.TableMetaData;
import org.bluebridge.generator.mapper.DataSourceMapper;
import org.bluebridge.generator.service.DataSourceService;
import org.bluebridge.generator.domain.vo.DataSourceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.*;
import java.util.*;

/**
 * @author lingwh
 * @desc
 * @date 2025/11/23 12:28
 */
@Slf4j
@Service
public class DataSourceServiceImpl implements DataSourceService {

    // 数据源元信息模板类名后缀
    private static final String DATABASE_METADATA_TEMPLATE_SUFFIX = "DataSourceMetaDataTemplate";

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private DataSourceMapper dataSourceMapper;

    @Resource
    private DataSourceConverter dataSourceConverter;

    @Override
    public int createDataSource(DataSourceCreateDTO dataSourceCreateDTO) {
        // 测试连接是否可用
        DataSourceConnectionDTO dataSourceConnectionDTO = dataSourceConverter.toDataSourceConnectionDTO(dataSourceCreateDTO);
        boolean connectionAvailable = this.testDataSourceConnection(dataSourceConnectionDTO);
        dataSourceCreateDTO.setEnable(connectionAvailable);

        DataSourceDO dataSourceDO = dataSourceConverter.toDataSourceDO(dataSourceCreateDTO);
        return dataSourceMapper.insertDataSource(dataSourceDO);
    }

    @Override
    public int deleteDataSourceById(Integer id) {
        DataSourceDO dataSourceDO = dataSourceMapper.selectDataSourceById(id);
        if (dataSourceDO == null) {
            throw new BusinessException(ResponseStatusEnum.NOT_FOUND, "数据源不存在或已删除，ID: " + id);
        }

        return dataSourceMapper.deleteDataSourceById(id);
    }

    @Override
    public int updateDataSource(Integer id, DataSourceUpdateDTO dataSourceUpdateDTO) {
        DataSourceDO dataSourceDO = dataSourceMapper.selectDataSourceById(id);
        if (dataSourceDO == null) {
            throw new BusinessException(ResponseStatusEnum.NOT_FOUND, "数据源不存在或已删除，ID: " + id);
        }
        
        // 测试连接是否可用
        DataSourceConnectionDTO dataSourceConnectionDTO = dataSourceConverter.toDataSourceConnectionDTO(dataSourceUpdateDTO);
        boolean connectionAvailable = this.testDataSourceConnection(dataSourceConnectionDTO);
        dataSourceUpdateDTO.setEnable(connectionAvailable);

        dataSourceDO = dataSourceConverter.toDataSourceDO(dataSourceUpdateDTO);
        dataSourceDO.setId(id);

        return dataSourceMapper.updateDataSource(dataSourceDO);
    }

    @Override
    public DataSourceVO getDataSourceById(Integer id) {
        DataSourceDO dataSourceDO = dataSourceMapper.selectDataSourceById(id);
        if (dataSourceDO == null) {
            throw new BusinessException(ResponseStatusEnum.NOT_FOUND, "数据源不存在或已删除，ID: " + id);
        }
        return dataSourceConverter.toDataSourceVO(dataSourceDO);
    }

    @Override
    public List<DataSourceVO> listDataSource() {
        List<DataSourceDO> dataSourceDOList = dataSourceMapper.selectDataSourceList(null);
        return dataSourceConverter.toDataSourceVOList(dataSourceDOList);
    }

    @Override
    public List<DataSourceVO> searchDataSource(Query<DataSourceQueryDTO> query) {
        List<DataSourceDO> dataSourceDOList = dataSourceMapper.selectDataSourceList(query);
        return dataSourceConverter.toDataSourceVOList(dataSourceDOList);
    }

    @Override
    public PageInfo<DataSourceVO> page(PageQuery<DataSourceQueryDTO> pageQuery) {
        // 基于PageHelper实现分页查询
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());

        // 把分页查询参数转换为查询参数
        Query<DataSourceQueryDTO> query = Query.<DataSourceQueryDTO>builder()
                .conditions(pageQuery.getConditions())
                .sortList(pageQuery.getSortList())
                .build();

        List<DataSourceDO> dataSourceDOList = dataSourceMapper.selectDataSourceList(query);
        PageInfo<DataSourceDO> dataSourceDOPageInfo = new PageInfo<>(dataSourceDOList);

        return dataSourceConverter.toDataSourceVOPageInfo(dataSourceDOPageInfo);
    }

    /**
     * 测试数据源连接
     * @param dataSourceConnectionDTO
     * @return
     */
    private boolean testDataSourceConnection(DataSourceConnectionDTO dataSourceConnectionDTO) {
        DataSourceMetaDataTemplate dataSourceMetaDataTemplate = getDataSourceMetaDataTemplate(dataSourceConnectionDTO.getType());
        // 使用try-with-resources确保连接被正确关闭
        try (Connection connection = dataSourceMetaDataTemplate.createConnection(dataSourceConnectionDTO)) {
            log.info("测试数据库连接成功，连接信息: {}", connection);
            // 连接成功，connection会自动关闭
            return true;
        } catch (Exception e) {
            log.error("测试数据库连接失败", e);
            return false;
        }
    }

    /**
     * 根据数据源信息获取数据源元数据模板
     * @param dataSourceType
     * @return
     */
    private DataSourceMetaDataTemplate getDataSourceMetaDataTemplate(String dataSourceType) {
        // 拼接数据源模板类名
        StringBuilder dataSourceMetaDataTemplateNameBuilder
                = new StringBuilder()
                // 包名
                .append(this.getClass().getPackageName())
                // 分隔符
                .append(".")
                // 类名
                .append(this.getClass().getSimpleName())
                // 分隔符
                .append("$")
                // 数据源类型
                .append(dataSourceType)
                // 后缀
                .append(DATABASE_METADATA_TEMPLATE_SUFFIX);
        String dataSourceMetaDataTemplateName = dataSourceMetaDataTemplateNameBuilder.toString();
        log.info("数据源模板类名: {}", dataSourceMetaDataTemplateName);
        // 从Spring容器中获取数据源模板
        return applicationContext.getBean(dataSourceMetaDataTemplateName, DataSourceMetaDataTemplate.class);
    }

    /**
     * @param id 数据源ID
     * @return
     */
    @Override
    public DataSourceMetaData getDataSourceMetaDataById(Integer id) {
        // 根据ID获取数据源信息
        DataSourceDO dataSourceDO = dataSourceMapper.selectDataSourceById(id);
        if (dataSourceDO == null) {
            throw new BusinessException(ResponseStatusEnum.NOT_FOUND, "数据源不存在或已删除，ID: " + id);
        }

        // 拼接数据源模板类名
        DataSourceMetaDataTemplate dataSourceMetaDataTemplate = getDataSourceMetaDataTemplate(dataSourceDO.getType());

        DataSourceConnectionDTO dataSourceConnectionDTO = dataSourceConverter.toDataSourceConnectionDTO(dataSourceDO);
        return dataSourceMetaDataTemplate.generateDataSourceMetaData(dataSourceConnectionDTO);
    }

    private abstract class DataSourceMetaDataTemplate {

        /**
         * 生成完整的数据源元数据
         * @param dataSourceConnectionDTO
         * @return
         */
        public final DataSourceMetaData generateDataSourceMetaData(DataSourceConnectionDTO dataSourceConnectionDTO) {
            try (Connection connection = createConnection(dataSourceConnectionDTO)) {
                // 获取表元信息
                List<TableMetaData> tableMetaDataList = extractTableMetaData(connection);

                // 数据源元信息
                DataSourceMetaData dataSourceMetaData = new DataSourceMetaData();
                dataSourceMetaData.setDatabase(dataSourceConnectionDTO.getDatabase());
                dataSourceMetaData.setType(dataSourceConnectionDTO.getType());
                dataSourceMetaData.setVersion(connection.getMetaData().getDatabaseProductName() + " " + connection.getMetaData().getDatabaseProductVersion());
                dataSourceMetaData.setTableMetaDataList(tableMetaDataList);

                return dataSourceMetaData;
            } catch (Exception e) {
                log.error("获取数据源元信息失败", e);
                throw new RuntimeException("获取数据源元信息失败: " + e.getMessage());
            }
        }

        /**
         * 创建数据源连接
         * @param dataSourceConnectionDTO
         * @return
         * @throws ClassNotFoundException
         * @throws SQLException
         */
        public abstract Connection createConnection(DataSourceConnectionDTO dataSourceConnectionDTO) throws ClassNotFoundException, SQLException;

        /**
         * 提取表元信息
         * @param connection
         * @return
         * @throws SQLException
         */
        private List<TableMetaData> extractTableMetaData(Connection connection) throws SQLException {
            List<String> params = prepareResultSetParams(connection).get(MetaDataConstant.TABLE_KEY);
            List<TableMetaData> tableMetaDataList = new ArrayList<>();
            // 数据库元信息
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            try (ResultSet tableAndViewRs = databaseMetaData.getTables(params.get(0), params.get(1), params.get(2), new String[]{"TABLE", "VIEW"})) {
                while (tableAndViewRs.next()) {
                    String tableName = tableAndViewRs.getString("TABLE_NAME");
                    String tableType = tableAndViewRs.getString("TABLE_TYPE");
                    String tableComment = tableAndViewRs.getString("REMARKS");
                    tableComment = tableComment.equals("") ? tableName : tableComment;
                    List<ColumnMetaData> columnMetaDataList = extractColumnsMetaData(connection, tableName);
                    TableMetaData tableMetaData = new TableMetaData();
                    tableMetaData.setTableName(tableName);
                    tableMetaData.setTableComment(tableComment);
                    tableMetaData.setColumns(columnMetaDataList);
                    log.info("表名: {}, 类型: {}, 备注: {}, 列数: {}, 列数据: {}", tableName, tableType, tableComment, columnMetaDataList.size(), columnMetaDataList);
                    tableMetaDataList.add(tableMetaData);
                }
            }
            return tableMetaDataList;
        }

        /**
         * 提取列元信息
         * @param connection
         * @param tableName
         * @return
         * @throws SQLException
         */
        private List<ColumnMetaData> extractColumnsMetaData(Connection connection, String tableName) throws SQLException {
            List<String> params = prepareResultSetParams(connection).get(MetaDataConstant.COLUMN_KEY);
            List<String> primaryKeyList = extractPrimaryKeys(connection, tableName);
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            List<ColumnMetaData> columnMetaDataList = new ArrayList<>();
            try (ResultSet columnsRs = databaseMetaData.getColumns(params.get(0), params.get(1), tableName, params.get(2))) {
                Map<String, String> columnCommentList = new HashMap<>();
                while (columnsRs.next()) {
                    ColumnMetaData columnMetaData = new ColumnMetaData();
                    String columnName = columnsRs.getString("COLUMN_NAME");
                    columnMetaData.setColumnName(columnName);
                    columnMetaData.setColumnType(columnsRs.getString("TYPE_NAME"));
                    columnMetaData.setColumnSize(columnsRs.getInt("COLUMN_SIZE"));
                    columnMetaData.setDecimalDigits(columnsRs.getInt("DECIMAL_DIGITS"));
                    //columnMetaData.setNullable(columnsRs.getInt("NULLABLE"));
                    columnMetaData.setColumnComment(columnsRs.getString("REMARKS"));
                    columnMetaData.setDefaultValue(columnsRs.getString("COLUMN_DEF"));
                    columnMetaData.setPrimaryKey(primaryKeyList.contains(columnName));
                    columnMetaData.setColumnComment(columnCommentList.getOrDefault(columnName, ""));
                    columnMetaDataList.add(columnMetaData);
                }
            }
            return columnMetaDataList;
        }

        /**
         * 获取主键信息(包括常规主键和联合主键)
         * @param connection
         * @return
         * @throws SQLException
         */
        private List<String> extractPrimaryKeys(Connection connection, String tableName) throws SQLException {
            List<String> params = prepareResultSetParams(connection).get(MetaDataConstant.PRIMARY_KEY_KEY);
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            List<String> primaryKeyList = new ArrayList<>();
            // 获取指定表的主键信息
            try (ResultSet primaryKeyRs = databaseMetaData.getPrimaryKeys(params.get(0), params.get(1), tableName)) {
                while (primaryKeyRs.next()) {
                    String columnName = primaryKeyRs.getString("COLUMN_NAME");
                    primaryKeyList.add(columnName);
                }
            }
            return primaryKeyList;
        }

        /**
         * 准备获取结果集参数
         * @return
         */
        public abstract Map<String, List<String>> prepareResultSetParams(Connection connection) throws SQLException;

    }

    /**
     * MySQL数据库元数据构建器
     */
    @Component
    private class MySQLDataSourceMetaDataTemplate extends DataSourceMetaDataTemplate {

        @Override
        public Connection createConnection(DataSourceConnectionDTO dataSourceConnectionDTO) throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%%2B8&allowPublicKeyRetrieval=true",
                    dataSourceConnectionDTO.getHost(), dataSourceConnectionDTO.getPort(), dataSourceConnectionDTO.getDatabase());
            return DriverManager.getConnection(url, dataSourceConnectionDTO.getUsername(), dataSourceConnectionDTO.getPassword());
        }

        @Override
        public Map<String, List<String>> prepareResultSetParams(Connection connection) throws SQLException {
            // 获取数据库名称
            String databaseName = ((ConnectionImpl) connection).getDatabase();
            Map<String, List<String>> params = new HashMap<>();
            params.put(MetaDataConstant.TABLE_KEY, Arrays.asList(databaseName, null, "%"));
            params.put(MetaDataConstant.PRIMARY_KEY_KEY, Arrays.asList(databaseName, null));
            params.put(MetaDataConstant.COLUMN_KEY, Arrays.asList(databaseName, null, "%"));
            return params;
        }

    }

    /**
     * Oracle数据库元数据构建器
     */
    @Component
    private class OracleDataSourceMetaDataTemplate extends DataSourceMetaDataTemplate {

        @Override
        public Connection createConnection(DataSourceConnectionDTO dataSourceConnectionDTO) throws ClassNotFoundException, SQLException {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = String.format("jdbc:oracle:thin:@%s:%s:%s",
                    dataSourceConnectionDTO.getHost(), dataSourceConnectionDTO.getPort(), dataSourceConnectionDTO.getDatabase());
            return DriverManager.getConnection(url, dataSourceConnectionDTO.getUsername(), dataSourceConnectionDTO.getPassword());
        }

        @Override
        public Map<String, List<String>> prepareResultSetParams(Connection connection) {
            Map<String, List<String>> params = new HashMap<>();
            // Oracle使用用户名作为模式(schema)
            params.put(MetaDataConstant.TABLE_KEY, Arrays.asList(null, null, "%"));
            params.put(MetaDataConstant.PRIMARY_KEY_KEY, Arrays.asList(null, null));
            params.put(MetaDataConstant.COLUMN_KEY, Arrays.asList(null, null, "%"));
            return params;
        }

    }

    /**
     * SQL Server元数据构建器
     */
    @Component
    private class SqlServerDataSourceMetaDataTemplate extends DataSourceMetaDataTemplate {

        @Override
        public Connection createConnection(DataSourceConnectionDTO dataSourceConnectionDTO) throws ClassNotFoundException, SQLException {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = String.format("jdbc:sqlserver://%s:%s;databaseName=%s;encrypt=false",
                    dataSourceConnectionDTO.getHost(), dataSourceConnectionDTO.getPort(), dataSourceConnectionDTO.getDatabase());
            return DriverManager.getConnection(url, dataSourceConnectionDTO.getUsername(), dataSourceConnectionDTO.getPassword());
        }

        @Override
        public Map<String, List<String>> prepareResultSetParams(Connection connection) {
            Map<String, List<String>> params = new HashMap<>();
            // SQL Server使用dbo作为默认模式
            params.put(MetaDataConstant.TABLE_KEY, Arrays.asList(null, "dbo", "%"));
            params.put(MetaDataConstant.PRIMARY_KEY_KEY, Arrays.asList(null, "dbo"));
            params.put(MetaDataConstant.COLUMN_KEY, Arrays.asList(null, "dbo", "%"));
            return params;
        }

    }

    /**
     * PostgreSQL数据库元数据构建器
     */
    @Component
    private class PostgreSQLDataSourceMetaDataTemplate extends DataSourceMetaDataTemplate {

        @Override
        public Connection createConnection(DataSourceConnectionDTO dataSourceConnectionDTO) throws ClassNotFoundException, SQLException {
            Class.forName("org.postgresql.Driver");
            String url = String.format("jdbc:postgresql://%s:%s/%s",
                    dataSourceConnectionDTO.getHost(), dataSourceConnectionDTO.getPort(), dataSourceConnectionDTO.getDatabase());
            return DriverManager.getConnection(url, dataSourceConnectionDTO.getUsername(), dataSourceConnectionDTO.getPassword());
        }

        @Override
        public Map<String, List<String>> prepareResultSetParams(Connection connection) {
            Map<String, List<String>> params = new HashMap<>();
            // PostgreSQL使用public作为默认模式
            params.put(MetaDataConstant.TABLE_KEY, Arrays.asList(null, "public", "%"));
            params.put(MetaDataConstant.PRIMARY_KEY_KEY, Arrays.asList(null, "public"));
            params.put(MetaDataConstant.COLUMN_KEY, Arrays.asList(null, "public", "%"));
            return params;
        }

    }

}
