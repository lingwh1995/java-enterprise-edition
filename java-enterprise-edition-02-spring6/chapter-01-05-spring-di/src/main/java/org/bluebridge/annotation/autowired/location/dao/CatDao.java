package org.bluebridge.annotation.autowired.location.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class CatDao {

    private static final Logger logger = LogManager.getLogger(CatDao.class);

    public void deleteById(String id) {
        logger.info("正在执行根据id删除操作...[使用注解完成DI-测试autowired可以出现的位置]");
    }
}
