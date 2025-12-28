package com.xa8bit.generator.mapper;

import com.xa8bit.common.domain.query.Query;
import com.xa8bit.generator.domain.dto.DataSourceQueryDTO;
import com.xa8bit.generator.domain.entity.DataSourceDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * @author lingwh
 * @desc
 * @date 2025/11/23 12:10
 */
@Mapper
public interface DataSourceMapper {

    /**
     * 新增数据源
     * @param dataSourceDO
     * @return
     */
    int insertDataSource(DataSourceDO dataSourceDO);

    /**
     * 根据ID删除数据源
     * @param id
     * @return
     */
    int deleteDataSourceById(Integer id);

    /**
     * 更新数据源
     * @param dataSourceDO
     * @return
     */
    int updateDataSource(DataSourceDO dataSourceDO);

    /**
     * 根据ID查询数据源
     * @param id
     * @return
     */
    DataSourceDO selectDataSourceById(Integer id);

    /**
     * 根据条件分页查询数据源列表
     * @param query
     * @return
     */
    List<DataSourceDO> selectDataSourceList(Query<DataSourceQueryDTO> query);

}
