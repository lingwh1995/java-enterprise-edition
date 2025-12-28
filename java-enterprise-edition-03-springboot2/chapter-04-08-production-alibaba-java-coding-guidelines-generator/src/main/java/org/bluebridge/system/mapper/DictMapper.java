package org.bluebridge.system.mapper;

import org.bluebridge.common.domain.query.Query;
import org.bluebridge.system.domain.entity.DictDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/9 10:57
 */
@Mapper
public interface DictMapper {

    List<DictDO> selectListWithJoin(Query<DictDO> query);

}
