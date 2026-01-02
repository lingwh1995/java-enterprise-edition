package org.bluebridge.system.converter;

import org.bluebridge.system.domain.entity.DictDO;
import org.bluebridge.system.domain.vo.DictVO;
import org.mapstruct.Mapper;

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

}