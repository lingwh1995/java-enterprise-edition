package org.bluebridge.cache.service.impl;

import com.alicp.jetcache.anno.Cached;
import org.bluebridge.cache.constant.CacheKeyConstants;
import org.bluebridge.cache.converter.DictConverter;
import org.bluebridge.cache.domain.dto.DictQueryDTO;
import org.bluebridge.cache.domain.entity.DictDO;
import org.bluebridge.cache.domain.vo.DictVO;
import org.bluebridge.cache.mapper.DictMapper;
import org.bluebridge.cache.service.DictService;
import org.bluebridge.common.domain.query.Query;
import org.bluebridge.common.util.SpringUtils;
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
        List<DictVO> dictVOList = SpringUtils.getBean(DictService.class).listDictWithJoin(null);
        return dictVOList.stream().filter(dictVO -> dictVO.getDictCode().equals(dictCode))
                .findFirst()
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
    public List<DictVO> listDictWithJoin(Query<DictQueryDTO> query) {
        List<DictDO> dictDOList = dictMapper.selectDictListWithJoin(query);
        return dictConverter.toDictVOList(dictDOList);
    }

    @Override
    public List<DictVO> listDict(Query<DictQueryDTO> query) {
        List<DictDO> dictDOList = dictMapper.selectDictList(query);
        return dictConverter.toDictVOList(dictDOList);
    }

}
