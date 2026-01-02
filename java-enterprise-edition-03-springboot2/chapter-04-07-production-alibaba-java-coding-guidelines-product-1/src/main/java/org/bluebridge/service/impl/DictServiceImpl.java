package org.bluebridge.service.impl;

import org.bluebridge.common.component.CacheHolder;
import org.bluebridge.common.domain.query.Query;
import org.bluebridge.converter.DictConverter;
import org.bluebridge.domain.entity.DictDO;
import org.bluebridge.domain.vo.DictVO;
import org.bluebridge.mapper.DictMapper;
import org.bluebridge.service.DictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/9 19:09
 */
@Service
public class DictServiceImpl implements DictService {

    @Resource
    private DictMapper dictMapper;

    @Resource
    private CacheHolder<DictDO> cacheHolder;

    @Resource
    private DictConverter dictConverter;

    @Override
    public DictVO getDictByDictCode(String dictCode) {
        DictDO dictDO = cacheHolder.get(dictCode).orElse(null);
        return dictConverter.toDictVO(dictDO);
    }

    @Override
    public List<DictDO> searchDict(Query<DictDO> query) {
        return dictMapper.selectDictListWithJoin(query);
    }

}
