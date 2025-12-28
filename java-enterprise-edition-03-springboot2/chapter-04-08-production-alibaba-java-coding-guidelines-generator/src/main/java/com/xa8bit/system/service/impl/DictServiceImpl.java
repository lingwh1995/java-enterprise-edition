package com.xa8bit.system.service.impl;

import com.xa8bit.common.domain.query.Query;
import com.xa8bit.system.component.CacheHolder;
import com.xa8bit.system.converter.DictConverter;
import com.xa8bit.system.domain.entity.DictDO;
import com.xa8bit.system.domain.vo.DictVO;
import com.xa8bit.system.mapper.DictMapper;
import com.xa8bit.system.service.DictService;
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
