package org.bluebridge.cache.service;

import org.bluebridge.cache.domain.dto.DictQueryDTO;
import org.bluebridge.cache.domain.vo.DictVO;
import org.bluebridge.common.domain.query.Query;

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
     * 关联查询字典列表
     * @param query 查询条件
     * @return 字典列表
     */
    List<DictVO> listDictWithJoin(Query<DictQueryDTO> query);

    /**
     * 查询字典列表
     * @param query 查询条件
     * @return 字典列表
     */
    List<DictVO> listDict(Query<DictQueryDTO> query);

}
