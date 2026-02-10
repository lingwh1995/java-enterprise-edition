package org.bluebridge.service.impl;

import com.alicp.jetcache.anno.Cached;
import org.bluebridge.common.constant.CacheKeyConstants;
import org.bluebridge.common.domain.query.Query;
import org.bluebridge.common.util.SpringUtils;
import org.bluebridge.converter.DictConverter;
import org.bluebridge.domain.entity.DictDO;
import org.bluebridge.domain.vo.DictVO;
import org.bluebridge.mapper.DictMapper;
import org.bluebridge.service.DictService;
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
    private DictConverter dictConverter;

    @Override
    public DictVO getDictByDictCode(String dictCode) {
        // 1. 查询dictDOList，如果有缓存则从缓存中获取dictDOList
        // 2. 在 dictDOList 中找到 dictCode 对应的 dictDO
        // 3. 把 dictDO 转换为 DictVO并返回
        // 为了使代理生效，这里必须通过 SpringUtils 获取 bean
        List<DictDO> dictDOList = SpringUtils.getBean(DictService.class).searchDict(null);
        return dictDOList.stream().filter(dictDO -> dictDO.getDictCode().equals(dictCode))
                .findFirst().map(dictDO -> dictConverter.toDictVO(dictDO))
                .get();
    }

    /*
    // 指定使用本地缓存
    @Cached(name = ":dict:search_dict", key = CacheKeyConstants.CACHE_KEY, cacheType = CacheType.LOCAL, expire = 24, timeUnit = TimeUnit.HOURS)
    // 指定使用远程缓存
    @Cached(name = ":dict:search_dict", key = CacheKeyConstants.CACHE_KEY, cacheType = CacheType.REMOTE, expire = 24, timeUnit = TimeUnit.HOURS)
    // 指定使用二级缓存
    @Cached(name = ":dict:search_dict", key = CacheKeyConstants.CACHE_KEY, cacheType = CacheType.BOTH, expire = 24, timeUnit = TimeUnit.HOURS)
    */
    // 使用配置文件中默认配置
    @Cached(name = ":dict:search_dict", key = CacheKeyConstants.CACHE_KEY, expire = 24, timeUnit = TimeUnit.HOURS)
    @Override
    public List<DictDO> searchDict(Query<DictDO> query) {
        return dictMapper.selectDictListWithJoin(query);
    }

}
