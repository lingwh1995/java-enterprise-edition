package com.xa8bit.system.service;


import com.xa8bit.common.domain.query.Query;
import com.xa8bit.system.domain.entity.DictDO;
import com.xa8bit.system.domain.vo.DictVO;

import java.util.List;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/9 19:08
 */
public interface DictService {

    /**
     * 根据字典编码查询字典信息
     * @param dictCode 字典编码
     * @return 字典信息
     */
    DictVO getDictByDictCode(String dictCode);

    /**
     * 查询字典列表
     * @param query 查询条件
     * @return 字典列表
     */
    List<DictDO> searchProduct(Query<DictDO> query);

}
