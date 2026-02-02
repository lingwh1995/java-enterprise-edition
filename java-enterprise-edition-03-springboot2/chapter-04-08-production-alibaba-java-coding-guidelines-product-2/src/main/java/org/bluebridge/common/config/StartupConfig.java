package org.bluebridge.common.config;

import lombok.extern.slf4j.Slf4j;
import org.bluebridge.monitor.constant.SqlConstants;
import org.bluebridge.cache.domain.entity.DictDO;
import org.bluebridge.monitor.enums.SlowSqlThresholdTypeEnum;
import org.bluebridge.cache.service.DictService;
import org.springframework.beans.factory.annotation.Value;
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
public class StartupConfig {

    @Resource
    private DictService dictService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    @Value("${boost.dict.load-on-startup:false}")
    private boolean dictLoadOnStartup;

    @PostConstruct
    public void startUp() throws Exception {
        log.info(" ===>   系统启动预热开始......");

        // 检查运行时环境
        checkRuntimeEnvironment();

        // 打印系统信息
        printSystemMetadataInfo();

        // 加载慢查询参数
        loadLongQueryTime();

        // 加载字典数据
        loadDict();

        // 探测组件的连通性
        pingComponentConnect();

        log.info(" ===>   系统启动预热完成......");
    }

    /**
     * 检查运行时环境
     */
    private void checkRuntimeEnvironment() {
        log.info(" ===>   正在检查运行环境......");
        String timezone = TimeZone.getDefault().getID();
        log.info(" ===>   检查运行环境: 时区 [{}], 文件编码 [{}]", timezone, System.getProperty("file.encoding"));
        if (!"Asia/Shanghai".equals(timezone) && !"GMT+8".equals(timezone)) {
            log.warn(" ===>   注意：当前时区不是 Asia/Shanghai，请确认业务是否允许。");
        }
    }

    /**
     * 打印系统元数据信息
     */
    private void printSystemMetadataInfo() {
        log.info(" ===>   正在打印系统信息......");
        log.info(" ===>   Java Version: {}", System.getProperty("java.version"));
        log.info(" ===>   OS Architecture: {}", System.getProperty("os.arch"));
    }

    /**
     * 查询慢查询参数
     */
    private void loadLongQueryTime() {
        try {
            String databaseType = jdbcTemplate.getDataSource().getConnection()
                    .getMetaData().getDatabaseProductName();
            if ("MySQL".equalsIgnoreCase(databaseType)) {
                // 1. 执行 SQL 查询 MySQL 自身的慢查询阈值
                // 如果你想查自定义的表，只需更换 SQL 即可
                String mysqlValue = jdbcTemplate.queryForObject(
                        "SHOW VARIABLES LIKE 'long_query_time'",
                        (rs, rowNum) -> rs.getString("Value")
                );

                if (mysqlValue != null) {
                    // MySQL 默认单位是秒，转为毫秒
                    int threshold = (int) (Double.parseDouble(mysqlValue) * 1000);

                    // 2. 更新静态常量
                    SqlConstants.SLOW_SQL_THRESHOLD = threshold;
                    SqlConstants.SLOW_SQL_THRESHOLD_TYPE = SlowSqlThresholdTypeEnum.DB;
                    log.info(" ===>   SqlConstants.SLOW_SQL_THRESHOLD 同步成功！当前慢查询阈值已设为: {} ms", threshold);
                }
            }
        } catch (Exception e) {
            // 3. 异常处理：查不到则保持现状（即使用 SqlConstants 中的默认值或 yml 中的值）
            log.warn(" ===>   SqlConstants.LONG_QUERY_TIME 未能从数据库获取参数（原因: {}），将使用常量默认值: {} ms",
                    e.getMessage(), SqlConstants.SLOW_SQL_THRESHOLD);
        }
    }

    /**
     * 加载字典数据到缓存中
     */
    private void loadDict() {
        if(dictLoadOnStartup) {
            log.info(" ===>   正在加载字典数据......");
            // 缓存字典到缓存中
            List<DictDO> dictDOList = dictService.searchDict(null);
        }
    }

    /**
     * 探测组件的连通性
     */
    private void pingComponentConnect() {
        log.info(" ===>   正在探测组件连通......");
        // 探测数据库
        try {
            jdbcTemplate.execute("SELECT 1");
            log.info(" ===>   [OK] Database 连通性正常......");
        } catch (Exception e) {
            // 红色字体打印异常信息
            log.error(" ===>   [NO] Database 连通性异常......{}", e.getMessage());
        }

        // 探测 Redis
        try {
            redisTemplate.opsForValue().get("ping");
            log.info(" ===>   [OK] Redis 连通性正常......");
        } catch (Exception e) {
            log.error(" ===>   [NO] Redis 连通性异常......{}", e.getMessage());
        }
    }

}
