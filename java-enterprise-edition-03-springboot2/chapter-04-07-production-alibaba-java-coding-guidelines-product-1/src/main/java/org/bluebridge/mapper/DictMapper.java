package org.bluebridge.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.bluebridge.common.domain.query.Query;
import org.bluebridge.domain.dto.DictQueryDTO;
import org.bluebridge.domain.entity.DictDO;

import java.util.List;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/9 10:57
 */
@Mapper
public interface DictMapper {

    List<DictDO> selectDictListWithJoin(Query<DictQueryDTO> query);

    List<DictDO> selectDictList(Query<DictQueryDTO> query);

}
