package org.bluebridge.converter;

import org.bluebridge.domain.entity.DictDO;
import org.bluebridge.domain.vo.DictVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author lingwh
 * @desc 字典对象映射器
 * @date 2025/12/13 11:20
 */
// 组件模型设置为Spring，使MapStruct生成的实现类可以被Spring管理
@Mapper(componentModel = "spring")
public interface DictConverter {

    /**
     * 将 DictDO 转换为 DictVO
     * @param dictDO
     * @return
     */
    DictVO toDictVO(DictDO dictDO);

    /**
     * 将 DictDOList 转换为 DictVOList
     * @param dictDOList
     * @return
     */
    List<DictVO> toDictVOList(List<DictDO> dictDOList);

}