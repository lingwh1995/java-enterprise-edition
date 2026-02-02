package org.bluebridge.cache.service.impl;

import com.alicp.jetcache.anno.Cached;
import org.bluebridge.cache.constant.CacheKeyConstants;
import org.bluebridge.common.domain.query.Query;
import org.bluebridge.cache.component.CacheHolder;
import org.bluebridge.cache.converter.DictConverter;
import org.bluebridge.cache.domain.entity.DictDO;
import org.bluebridge.cache.domain.vo.DictVO;
import org.bluebridge.cache.mapper.DictMapper;
import org.bluebridge.cache.service.DictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    /*
    // 指定使用本地缓存
    @Cached(name = ":dict:search_dict", key = CacheKeyConstants.CACHE_KEY, cacheType = CacheType.LOCAL, expire = 1, timeUnit = TimeUnit.DAYS)
    // 指定使用远程缓存
    @Cached(name = ":dict:search_dict", key = CacheKeyConstants.CACHE_KEY, cacheType = CacheType.REMOTE, expire = 1, timeUnit = TimeUnit.DAYS)
    // 指定使用二级缓存
    @Cached(name = ":dict:search_dict", key = CacheKeyConstants.CACHE_KEY, cacheType = CacheType.BOTH, expire = 1, timeUnit = TimeUnit.DAYS)
    */
    // 使用配置文件中默认配置
    @Cached(name = ":dict:search_dict", key = CacheKeyConstants.CACHE_KEY, expire = 1, timeUnit = TimeUnit.DAYS)
    @Override
    public List<DictDO> searchDict(Query<DictDO> query) {
        return dictMapper.selectDictListWithJoin(query);
    }

}
