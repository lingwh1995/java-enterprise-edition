package org.bluebridge.common.config;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.common.component.CacheHolder;
import org.bluebridge.domain.entity.DictDO;
import org.bluebridge.service.DictService;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.TimeZone;

/**
 * @author lingwh
 * @desc 启动初始化配置（所有的初始化配置工作都在这里面完成）
 * @date 2025/12/9 10:53
 */
@Slf4j
@Configuration
public class SystemStartupConfig {

    @Resource
    private DictService dictService;

    @Resource
    private CacheHolder<DictDO> cacheHolder;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private RedisTemplate redisTemplate;


    @PostConstruct
    public void init() {
        log.info(" ===>   系统启动预热开始......");

        // 检查运行时环境
        checkRuntimeEnvironment();

        // 打印系统元数据信息
        printSystemMetadataInfo();

        // 加载字典数据
        loadDict();

        // 探测组件的连通性
        pingComponentConnect();

        log.info("===>   系统启动预热完成......");
    }

    /**
     * 检查运行时环境
     */
    private void checkRuntimeEnvironment() {
        String timezone = TimeZone.getDefault().getID();
        log.info(" ===>   运行时环境检查: 时区 [{}], 文件编码 [{}]", timezone, System.getProperty("file.encoding"));
        if (!"Asia/Shanghai".equals(timezone) && !"GMT+8".equals(timezone)) {
            log.warn(" ===>   注意：当前时区不是 Asia/Shanghai，请确认业务是否允许。");
        }
    }

    /**
     * 打印系统元数据信息
     */
    private void printSystemMetadataInfo() {
        log.info(" ===>   Java Version: {}", System.getProperty("java.version"));
        log.info(" ===>   OS Architecture: {}", System.getProperty("os.arch"));
    }

    /**
     * 加载字典数据到缓存中
     */
    private void loadDict() {
        // 缓存字典到缓存中
        List<DictDO> dictDOList = dictService.searchProduct(null);
        dictDOList.forEach(dictDO -> cacheHolder.put(dictDO.getDictCode(), dictDO));
    }

    /**
     * 探测组件的连通性
     */
    private void pingComponentConnect() {
        // 探测数据库
        jdbcTemplate.execute("SELECT 1");
        log.info("===>   [OK] Database 连通性正常");

        // 探测 Redis
        redisTemplate.opsForValue().get("ping");
        log.info("===>   [OK] Redis 连通性正常");
    }

}
