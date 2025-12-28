package org.bluebridge.generator.service;

import com.github.pagehelper.PageInfo;
import org.bluebridge.common.domain.query.PageQuery;
import org.bluebridge.common.domain.query.Query;
import org.bluebridge.generator.domain.dto.DataSourceQueryDTO;
import org.bluebridge.generator.domain.dto.DataSourceUpdateDTO;
import org.bluebridge.generator.domain.bo.DataSourceMetaData;
import org.bluebridge.generator.domain.dto.DataSourceCreateDTO;
import org.bluebridge.generator.domain.vo.DataSourceVO;

import java.sql.SQLException;
import java.util.List;

/**
 * @author lingwh
 * @desc 
 * @date 2025/11/23 12:18
 */
public interface DataSourceService {

    /**
     * 创建数据源
     * @param dataSourceCreateDTO
     * @return
     */
    int createDataSource(DataSourceCreateDTO dataSourceCreateDTO);

    /**
     * 根据ID删除数据源
     * @param id
     * @return
     */
    int deleteDataSourceById(Integer id);

    /**
     * 根据ID更新数据源
     * @param id
     * @param dataSourceUpdateDTO
     * @return
     */
    int updateDataSource(Integer id, DataSourceUpdateDTO dataSourceUpdateDTO);

    /**
     * 根据ID查询数据源
     * @param id
     * @return
     */
    DataSourceVO getDataSourceById(Integer id);

    /**
     * 获取所有数据源
     * @return
     */
    List<DataSourceVO> listDataSource();

    /**
     * 查询所有数据源
     * @return
     */
    List<DataSourceVO> searchDataSource(Query<DataSourceQueryDTO> query);

    /**
     * 根据条件分页查询数据源列表
     * @param pageQuery
     * @return
     */
    PageInfo<DataSourceVO> page(PageQuery<DataSourceQueryDTO> pageQuery);

    /**
     * 根据数据源ID获取数据源元信息
     *
     * @param id 数据源ID
     * @return 数据库元信息
     */
    DataSourceMetaData getDataSourceMetaDataById(Integer id) throws SQLException, ClassNotFoundException;

}
