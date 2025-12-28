package org.bluebridge.system.service.impl;

import org.bluebridge.common.domain.query.Query;
import org.bluebridge.system.component.CacheHolder;
import org.bluebridge.system.converter.DictConverter;
import org.bluebridge.system.domain.entity.DictDO;
import org.bluebridge.system.domain.vo.DictVO;
import org.bluebridge.system.mapper.DictMapper;
import org.bluebridge.system.service.DictService;
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
    public List<DictDO> searchProduct(Query<DictDO> query) {
        return dictMapper.selectListWithJoin(query);
    }

}
