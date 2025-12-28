package com.xa8bit.system.mapper;

import com.xa8bit.common.domain.query.Query;
import com.xa8bit.system.domain.entity.DictDO;
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
