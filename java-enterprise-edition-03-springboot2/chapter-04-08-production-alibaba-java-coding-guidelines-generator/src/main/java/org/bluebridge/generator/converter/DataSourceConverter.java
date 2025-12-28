package org.bluebridge.generator.converter;

import com.github.pagehelper.PageInfo;
import org.bluebridge.generator.domain.dto.DataSourceConnectionDTO;
import org.bluebridge.generator.domain.dto.DataSourceCreateDTO;
import org.bluebridge.generator.domain.dto.DataSourceUpdateDTO;
import org.bluebridge.generator.domain.entity.DataSourceDO;
import org.bluebridge.generator.domain.vo.DataSourceVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author lingwh
 * @desc 商品对象映射器
 * @date 2025/12/13 11:20
 */
// 组件模型设置为Spring，使MapStruct生成的实现类可以被Spring管理
@Mapper(componentModel = "spring")
public interface DataSourceConverter {

    /**
     * 将 CreateProductDTO 转换为 DataSourceDO 实体
     * @param createProductDTO
     * @return
     */
    DataSourceDO toDataSourceDO(DataSourceCreateDTO createProductDTO);

    /**
     * 将 UpdateProductDTO 转换为 DataSourceDO 实体
     * @param dataSourceUpdateDTO
     * @return
     */
    DataSourceDO toDataSourceDO(DataSourceUpdateDTO dataSourceUpdateDTO);

    /**
     * 把 DataSourceCreateDTO 转换为 DataSourceConnectionDTO
     * @param dataSourceCreateDTO
     * @return
     */
    DataSourceConnectionDTO toDataSourceConnectionDTO(DataSourceCreateDTO dataSourceCreateDTO);

    /**
     * 把 DataSourceUpdateDTO 转换为 DataSourceConnectionDTO
     * @param dataSourceUpdateDTO
     * @return
     */
    DataSourceConnectionDTO toDataSourceConnectionDTO(DataSourceUpdateDTO dataSourceUpdateDTO);

    /**
     * 把 DataSourceDO 转换为 DataSourceConnectionDTO
     * @param dataSourceDO
     * @return
     */
    DataSourceConnectionDTO toDataSourceConnectionDTO(DataSourceDO dataSourceDO);

    /**
     * 将 DataSourceDO 转换为 DataSourceVO
     * @param dataSourceDO
     * @return
     */
    DataSourceVO toDataSourceVO(DataSourceDO dataSourceDO);

    /**
     * 将 DataSourceDO 列表转换为 DataSourceVO 列表
     * @param dataSourceDOList
     * @return
     */
    List<DataSourceVO> toDataSourceVOList(List<DataSourceDO> dataSourceDOList);

    /**
     * 将 DataSourceDO分页对象 列表转换为 DataSourceVO分页对象
     * @param DataSourceDOPageInfo
     * @return
     */
    PageInfo<DataSourceVO> toDataSourceVOPageInfo(PageInfo<DataSourceDO> DataSourceDOPageInfo);

}