package org.bluebridge.cache.converter;
import org.bluebridge.cache.domain.entity.DictDO;
import org.bluebridge.cache.domain.vo.DictVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 字典对象映射器
 *
 * @author lingwh
 * @date 2025/12/13 11:20
 */
// 组件模型设置为 Spring，使 MapStruct 生成的实现类可以被 Spring 管理
@Mapper(componentModel = "spring")
public interface DictConverter {

    /**
     * 将 DictDO 转换为 DictVO
     *
     * @param dictDO
     * @return
     */
    DictVO toDictVO(DictDO dictDO);

    /**
     * 将 DictDOList 转换为 DictVOList
     *
     * @param dictDOList
     * @return
     */
    List<DictVO> toDictVOList(List<DictDO> dictDOList);
}